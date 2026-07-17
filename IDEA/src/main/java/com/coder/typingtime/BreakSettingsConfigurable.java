/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public final class BreakSettingsConfigurable implements Configurable {
    private JPanel panel;
    private JCheckBox enabledBox;
    private JSpinner intervalSpinner;
    private JSpinner snoozeSpinner;
    private JSpinner goalSpinner;
    private final TypingCounterService svc = TypingCounterService.getInstance();

    @Override
    public @NotNull String getDisplayName() { return "码字计时 & 休息提醒"; }

    @Override
    public @Nullable JComponent createComponent() {
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(JBUI.Borders.empty(12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = JBUI.insets(6);
        c.anchor = GridBagConstraints.WEST;

        enabledBox = new JCheckBox("开启休息提醒", svc.getState().breakEnabled);
        intervalSpinner = new JSpinner(new SpinnerNumberModel(svc.getState().intervalMinutes, 5, 180, 5));
        snoozeSpinner = new JSpinner(new SpinnerNumberModel(svc.getState().snoozeMinutes, 1, 60, 1));
        goalSpinner = new JSpinner(new SpinnerNumberModel((int) svc.getState().dailyGoal, 100, 5000, 100));

        c.gridx = 0; c.gridy = 0; panel.add(enabledBox, c);
        c.gridy = 1; panel.add(label("休息间隔（分钟）："), c);
        c.gridx = 1; panel.add(intervalSpinner, c);
        c.gridx = 0; c.gridy = 2; panel.add(label("贪睡时长（分钟）："), c);
        c.gridx = 1; panel.add(snoozeSpinner, c);
        c.gridx = 0; c.gridy = 3; panel.add(label("每日码字目标："), c);
        c.gridx = 1; panel.add(goalSpinner, c);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        panel.add(new JBLabel("💡 码字会实时蹦迪展示，码满设定时长就蹦出一句提醒你去走走。"), c);
        return panel;
    }

    private JBLabel label(String t) { return new JBLabel(t); }

    @Override
    public boolean isModified() {
        var st = svc.getState();
        return st.breakEnabled != enabledBox.isSelected()
                || st.intervalMinutes != (Integer) intervalSpinner.getValue()
                || st.snoozeMinutes != (Integer) snoozeSpinner.getValue()
                || st.dailyGoal != (Integer) goalSpinner.getValue();
    }

    @Override
    public void apply() {
        var st = svc.getState();
        st.breakEnabled = enabledBox.isSelected();
        st.intervalMinutes = (Integer) intervalSpinner.getValue();
        st.snoozeMinutes = (Integer) snoozeSpinner.getValue();
        st.dailyGoal = (Integer) goalSpinner.getValue();
    }

    @Override
    public void reset() {
        var st = svc.getState();
        enabledBox.setSelected(st.breakEnabled);
        intervalSpinner.setValue(st.intervalMinutes);
        snoozeSpinner.setValue(st.snoozeMinutes);
        goalSpinner.setValue((int) st.dailyGoal);
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() { return enabledBox; }
}
