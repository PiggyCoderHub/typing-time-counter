/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

/** 休息倒计时圆环：渐变弧 + 发光端点 + 中心“下次休息”字样。 */
public final class CountdownRing extends JComponent {
    private long remaining = 0;
    private long total = 1;
    private int tick = 0;

    public CountdownRing() {
        setBackground(new Color(24, 26, 34));
        Timer timer = new Timer(40, e -> { tick++; repaint(); });
        timer.start();
    }

    public void setRemaining(long rem, long tot) {
        this.remaining = rem;
        this.total = Math.max(1, tot);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, w, h);

        int cx = w / 2, cy = h / 2;
        int r = Math.min(w, h) / 2 - 16;

        // 轨道
        g2.setStroke(new BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(48, 52, 66));
        g2.drawOval(cx - r, cy - r, 2 * r, 2 * r);

        // 进度弧（越接近休息越满）
        float frac = Math.max(0f, Math.min(1f, 1f - (float) remaining / (float) total));
        Color c1 = new Color(77, 208, 225), c2 = new Color(255, 117, 151);
        GradientPaint gp = new GradientPaint(cx - r, cy - r, c1, cx + r, cy + r, c2);
        g2.setPaint(gp);
        double start = -90;
        double extent = 360 * frac;
        g2.draw(new Arc2D.Double(cx - r, cy - r, 2 * r, 2 * r, start, extent, Arc2D.OPEN));

        // 发光端点
        double ang = Math.toRadians(start + extent);
        int hx = (int) (cx + r * Math.cos(ang));
        int hy = (int) (cy + r * Math.sin(ang));
        g2.setColor(Color.WHITE);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.6f + 0.4f * (float) Math.abs(Math.sin(tick / 10.0))));
        g2.fillOval(hx - 5, hy - 5, 10, 10);
        g2.setComposite(AlphaComposite.SrcOver);

        // 中心文字
        g2.setColor(new Color(180, 190, 210));
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        FontMetrics fm = g2.getFontMetrics();
        String s = "下次休息";
        g2.drawString(s, cx - fm.stringWidth(s) / 2, cy - 6);
    }
}
