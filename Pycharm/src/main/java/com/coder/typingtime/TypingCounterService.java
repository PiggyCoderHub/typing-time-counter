/**
 * Code typing counter & break reminder plugin for IntelliJ IDEA
 * 插件核心服务：统计字符数、活动时间、定时休息提醒。
 * 监听全局文档变化，统计字符数、活动时间，累计“休息时间”。
 * 提醒休息时间：定时提醒、累计休息时间。
 * 配置界面：定时提醒、累计休息时间、目标字符数。
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @description 码字计时器 & 摸鱼休息提醒插件
 * @verson v0.5
 */

package com.coder.typingtime;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@State(name = "TypingTimeCounter", storages = @Storage("typing-time-counter.xml"))
public final class TypingCounterService
        implements PersistentStateComponent<TypingCounterService.State>, Disposable {

    public static class State {
        public long totalChars = 0L;
        public long todayChars = 0L;
        public String todayDate = "";
        public long todayActiveMs = 0L;
        public long totalActiveMs = 0L;
        public long lastTypeTs = 0L;
        public long dailyGoal = 800L;
        public boolean goalNotifiedToday = false;
        public boolean breakEnabled = true;
        public int intervalMinutes = 45;
        public int snoozeMinutes = 5;
        public long accumulatedMs = 0L;
        public long lastActivityTs = 0L;
    }

    private static final long IDLE_MS = 60_000L;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final State state = new State();
    private final CopyOnWriteArrayList<Consumer<String>> typingListeners = new CopyOnWriteArrayList<>();
    private final DocumentListener countingListener;

    public TypingCounterService() {
        this.countingListener = new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent e) {
                if (e.getNewLength() <= 0) return;
                handleType(e.getNewFragment().toString());
            }
        };
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(countingListener);
    }

    public static TypingCounterService getInstance() {
        return ApplicationManager.getApplication().getService(TypingCounterService.class);
    }

    public void addTypingListener(@NotNull Consumer<String> l) { typingListeners.add(l); }
    public void removeTypingListener(@NotNull Consumer<String> l) { typingListeners.remove(l); }

    private synchronized void handleType(@NotNull String frag) {
        long now = System.currentTimeMillis();
        resetDayIfNeeded();

        int n = frag.length();
        state.todayChars += n;
        state.totalChars += n;
        if (state.lastTypeTs > 0L) {
            long gap = now - state.lastTypeTs;
            if (gap > 0L && gap <= IDLE_MS) {
                state.todayActiveMs += gap;
                state.totalActiveMs += gap;
            }
        }
        state.lastTypeTs = now;

        if (state.lastActivityTs > 0L) {
            long gap = now - state.lastActivityTs;
            if (gap > 0L && gap <= IDLE_MS) state.accumulatedMs += gap;
        }
        state.lastActivityTs = now;

        maybeGoal();
        if (state.breakEnabled && state.accumulatedMs >= (long) state.intervalMinutes * 60_000L) {
            fireBreak();
            state.accumulatedMs = 0L;
        }

        for (Consumer<String> l : typingListeners) {
            try { l.accept(frag); } catch (Throwable ignored) {}
        }
    }

    private void resetDayIfNeeded() {
        String today = LocalDate.now().format(FMT);
        if (!today.equals(state.todayDate)) {
            state.todayDate = today;
            state.todayChars = 0L;
            state.todayActiveMs = 0L;
            state.goalNotifiedToday = false;
        }
    }

    private void maybeGoal() {
        if (!state.goalNotifiedToday && state.dailyGoal > 0L && state.todayChars >= state.dailyGoal) {
            state.goalNotifiedToday = true;
        }
    }

    public void fireBreak() {
        String msg = FunMessages.randomRemind();
        NotificationGroupManager manager = NotificationGroupManager.getInstance();
        Notification n = manager.getNotificationGroup("TypingTimeNotifications")
                .createNotification(msg, NotificationType.INFORMATION);
        int snooze = Math.max(1, state.snoozeMinutes);
        n.addAction(NotificationAction.createSimple(FunMessages.randomSnooze(snooze), () -> {
            state.accumulatedMs = Math.max(0L,
                    (long) state.intervalMinutes * 60_000L - (long) snooze * 60_000L);
            n.expire();
        }));
        n.addAction(NotificationAction.createSimple(FunMessages.randomDismiss(), () -> {
            state.accumulatedMs = 0L;
            n.expire();
        }));
        Notifications.Bus.notify(n);
    }

    public void snooze() {
        int s = Math.max(1, state.snoozeMinutes);
        state.accumulatedMs = Math.max(0L, (long) state.intervalMinutes * 60_000L - (long) s * 60_000L);
    }

    public void resetAccumulated() { state.accumulatedMs = 0L; }
    public void testBreak() { fireBreak(); }

    public long getTodayChars() { return state.todayChars; }
    public long getTotalChars() { return state.totalChars; }
    public long getTodayActiveMs() { return state.todayActiveMs; }
    public long getTotalActiveMs() { return state.totalActiveMs; }
    public long getDailyGoal() { return state.dailyGoal; }
    public boolean isGoalReached() { return state.goalNotifiedToday && state.todayChars >= state.dailyGoal; }
    public long getRemainingMs() {
        return Math.max(0L, (long) state.intervalMinutes * 60_000L - state.accumulatedMs);
    }
    public long getIntervalMs() { return (long) state.intervalMinutes * 60_000L; }

    @Override
    public State getState() { return state; }

    @Override
    public void loadState(@NotNull State s) { XmlSerializerUtil.copyBean(s, state); }

    @Override
    public void dispose() {}
}
