/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/** 工具窗主面板：上方统计卡片 + 中央码字舞台 + 底部休息倒计时与按钮。 */
public final class CounterPanel extends JPanel {
    private final TypingCounterService svc = TypingCounterService.getInstance();
    private final TypingStage stage = new TypingStage();
    private final CountdownRing ring = new CountdownRing();

    private final JBLabel todayCharsLabel = new JBLabel();
    private final JBLabel totalCharsLabel = new JBLabel();
    private final JBLabel timeLabel = new JBLabel();
    private final JBLabel speedLabel = new JBLabel();
    private final JBLabel goalLabel = new JBLabel();
    private final JBLabel statusLabel = new JBLabel();
    private final JBLabel lineLabel = new JBLabel();
    private final JCheckBox breakBox;

    private final Consumer<String> sink = this::onChars;
    private int lastMilestone = 0;
    private boolean goalShown = false;
    private int statusTick = 0;

    public CounterPanel() {
        super(new BorderLayout());
        setBackground(new Color(24, 26, 34));
        setBorder(JBUI.Borders.empty(10));

        // 统计卡片
        JPanel stats = new JPanel(new GridLayout(0, 2, 8, 8));
        stats.setBackground(getBackground());
        stats.add(card("今日码字（键盘已出汗）", todayCharsLabel));
        stats.add(card("累计码字（一生的痕迹）", totalCharsLabel));
        stats.add(card("今日码字时长（手指运动）", timeLabel));
        stats.add(card("当前手速（字/分）", speedLabel));
        stats.add(card("今日目标", goalLabel));
        stats.add(card("状态", statusLabel));
        add(stats, BorderLayout.NORTH);

        // 码字舞台
        stage.setPreferredSize(new Dimension(100, 240));
        stage.setBorder(BorderFactory.createLineBorder(new Color(60, 64, 80), 1, true));
        add(stage, BorderLayout.CENTER);

        // 底部：休息区
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setBackground(getBackground());
        ring.setPreferredSize(new Dimension(168, 168));
        bottom.add(ring, BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout(4, 4));
        right.setBackground(getBackground());
        lineLabel.setForeground(new Color(150, 200, 255));
        lineLabel.setFont(lineLabel.getFont().deriveFont(13f));
        right.add(lineLabel, BorderLayout.NORTH);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        controls.setBackground(getBackground());
        breakBox = new JCheckBox("开启摸鱼提醒", svc.getState().breakEnabled);
        breakBox.setForeground(Color.WHITE);
        breakBox.addActionListener(e -> svc.getState().breakEnabled = breakBox.isSelected());
        JButton nowBtn = new JButton("现在就去走走 🚶");
        nowBtn.addActionListener(e -> svc.testBreak());
        JButton laterBtn = new JButton("我再战一会儿 ⚔️");
        laterBtn.addActionListener(e -> svc.resetAccumulated());
        controls.add(breakBox);
        controls.add(nowBtn);
        controls.add(laterBtn);
        right.add(controls, BorderLayout.CENTER);
        bottom.add(right, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        svc.addTypingListener(sink);
        new javax.swing.Timer(1000, e -> refresh()).start();
        refresh();
    }

    private JPanel card(String title, JComponent valueComp) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(34, 37, 48));
        p.setBorder(JBUI.Borders.empty(8));
        JBLabel titleL = new JBLabel(title);
        titleL.setForeground(new Color(150, 160, 180));
        titleL.setFont(titleL.getFont().deriveFont(11f));
        JLabel v = (JLabel) valueComp;
        v.setForeground(Color.WHITE);
        v.setFont(v.getFont().deriveFont(Font.BOLD, 18f));
        p.add(titleL, BorderLayout.NORTH);
        p.add(valueComp, BorderLayout.CENTER);
        return p;
    }

    private void onChars(String frag) {
        if (frag.length() <= 40) {
            for (char c : frag.toCharArray()) stage.pushChar(c);
        } else {
            for (int i = 0; i < 30; i++) stage.pushChar(frag.charAt(i % frag.length()));
            stage.pulse("粘贴了一大片！+" + frag.length());
        }
    }

    private void refresh() {
        long today = svc.getTodayChars();
        long total = svc.getTotalChars();
        long activeMs = svc.getTodayActiveMs();
        long goal = svc.getDailyGoal();
        todayCharsLabel.setText(String.valueOf(today));
        totalCharsLabel.setText(String.valueOf(total));
        timeLabel.setText(formatMs(activeMs));
        double mins = activeMs / 60000.0;
        speedLabel.setText(String.valueOf(mins > 0 ? (int) (today / mins) : 0));
        goalLabel.setText(today + " / " + goal);

        int ms = (int) (today / 100);
        if (ms > lastMilestone) {
            lastMilestone = ms;
            stage.pulse(FunMessages.randomMilestone());
        }
        if (!goalShown && goal > 0 && today >= goal) {
            goalShown = true;
            stage.pulse(FunMessages.randomGoal());
        }
        if (today < goal) goalShown = false;

        ring.setRemaining(svc.getRemainingMs(), svc.getIntervalMs());

        statusTick++;
        if (statusTick % 6 == 0) {
            if (svc.getState().breakEnabled) lineLabel.setText(FunMessages.randomIdle());
            else lineLabel.setText("提醒已关闭，注意劳逸结合哦～");
            statusLabel.setText(FunMessages.randomStatus());
        }
    }

    private static String formatMs(long ms) {
        long s = ms / 1000, m = s / 60; s %= 60;
        long h = m / 60; m %= 60;
        return h > 0 ? String.format("%d:%02d:%02d", h, m, s) : String.format("%02d:%02d", m, s);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        svc.removeTypingListener(sink);
    }
}
