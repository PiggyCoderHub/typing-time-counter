/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import java.util.Random;

/** 全部文案都走幽默路线，主打一个“码字也要开开心心”。 */
public final class FunMessages {
    private static final Random R = new Random();

    // —— 码字状态（每秒轮换）——
    private static final String[] STATUS = {
            "手指在键盘上跳踢踏舞 💃",
            "你正在和 Bug 极限拉扯",
            "距离诺贝尔奖，还差那么一丢丢",
            "这段代码，精神上值一个亿",
            "键盘：我承受了这个年纪不该承受的敲击",
            "今日份码字已送达，请查收",
            "你的手指正在燃烧 🔥 别停，趁热打铁",
            "字字珠玑，句句扎心",
            "摸鱼指数偏低，卷度在线 💯",
            "光标闪烁，灵感正在路上 🚀",
    };

    // —— 每满 100 字的里程碑 ——
    private static final String[] MILESTONE = {
            "🎉 又码了 100 字！手指值得加个鸡腿！",
            "💪 100 字达成，离退休又近了一步",
            "✨ 漂亮！这 100 字里至少有一半不是废话",
            "🏅 百字勋章到手，继续冲！",
    };

    // —— 完成每日目标 ——
    private static final String[] GOAL = {
            "🏆 今日目标达成！你就是天选码字人！",
            "👑 目标完成，今天你说了算！",
            "🌟 任务达成，建议立刻去炫耀一波",
    };

    // —— 休息提醒正文 ——
    private static final String[] REMIND = {
            "🦵 你的腿已经睡着了，快起来走走，叫醒它们！",
            "🪑 屁股和椅子已融为一体？是时候解除了。",
            "👀 屏幕看太久，眼睛快变成像素了，眨眨眼，站起来！",
            "☕ 多喝水，多走动，少掉头发。",
            "🧍 起来！站起来！你不是盆栽！",
            "🐟 摸鱼时间到，活动一下，回来继续当卷王。",
            "🦴 脊椎：求求了，让我伸个懒腰。",
            "🌿 出去吸口新鲜空气，回来继续 battle Bug。",
            "🕺 起来扭两下，拯救你那僵硬的全身。",
            "🚶 走两步，就两步，你的腰会感谢你的。",
    };

    private static final String[] SNOOZE = {"再躺 %d 分钟 😴", "让我再瘫 %d 分钟 🛋️", "稍后 %d 分钟提醒 💤"};
    private static final String[] DISMISS = {"已起立，继续战斗 💪", "起立成功，准备伸展 🧘", "收到，这就去溜达 🚶"};
    private static final String[] IDLE = {
            "距离下次休息还有一会儿，继续愉快地卷～",
            "你最近很安静，是在思考人生吗？",
            "摸鱼检测中……检测到 0 条鱼。",
    };

    public static String randomStatus() { return STATUS[R.nextInt(STATUS.length)]; }
    public static String randomMilestone() { return MILESTONE[R.nextInt(MILESTONE.length)]; }
    public static String randomGoal() { return GOAL[R.nextInt(GOAL.length)]; }
    public static String randomRemind() { return REMIND[R.nextInt(REMIND.length)]; }
    public static String randomSnooze(int m) { return String.format(SNOOZE[R.nextInt(SNOOZE.length)], m); }
    public static String randomDismiss() { return DISMISS[R.nextInt(DISMISS.length)]; }
    public static String randomIdle() { return IDLE[R.nextInt(IDLE.length)]; }
}
