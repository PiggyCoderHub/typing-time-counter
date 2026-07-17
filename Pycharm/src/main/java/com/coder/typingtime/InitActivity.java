/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

/** 项目打开后尽早把统计服务拉起来，这样不打开工具窗也能默默计数。 */
public final class InitActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        TypingCounterService.getInstance();
    }
}
