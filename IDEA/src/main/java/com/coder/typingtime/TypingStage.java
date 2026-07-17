/**
 * 码字计时器 · 摸鱼休息提醒
 * @author 琼台师范学院万象大模型研究社 Piggy
 * @data 2026-07-17
 * @verson v0.5
 */

package com.coder.typingtime;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * “码字舞台”——强动效核心。
 * 你每敲一个字，就会有一颗彩色字符从底部蹦出、带光晕地往上飘并淡出；
 * 底部还有一条会流动的最近输入“弹幕带”；每满 100 字或达成目标，中央会炸出一句祝贺。
 */
public final class TypingStage extends JComponent {
    private final List<Particle> particles = new ArrayList<>();
    private final Deque<CharCell> ribbon = new ArrayDeque<>();
    private final List<Pulse> pulses = new ArrayList<>();
    private final Random rnd = new Random();
    private final Color[] palette = {
            new Color(0x4dd0e1), new Color(0xff7597), new Color(0xaed581),
            new Color(0xffd54f), new Color(0xff8a65), new Color(0xb388ff)
    };
    private int colorIdx = 0;
    private int tick = 0;

    private static final int MAX_PARTICLES = 160;
    private static final int RIBBON_MAX = 46;

    public TypingStage() {
        setBackground(new Color(18, 20, 28));
        Timer timer = new Timer(33, e -> { step(); repaint(); });
        timer.start();
    }

    /** 把一个字符送进舞台蹦迪。 */
    public void pushChar(char c) {
        if (particles.size() > MAX_PARTICLES) particles.remove(0);
        Color col = palette[colorIdx++ % palette.length];
        int w = getWidth() > 0 ? getWidth() : 400;
        float cx = w * 0.5f + (rnd.nextFloat() * 2 - 1) * w * 0.20f;
        float cy = getHeight() - 26;

        Particle p = new Particle();
        p.ch = c;
        p.x = cx;
        p.y = cy;
        p.vx = (rnd.nextFloat() * 2 - 1) * 1.6f;
        p.vy = -(2.4f + rnd.nextFloat() * 2.4f);
        p.color = col;
        p.age = 0;
        p.life = 70 + rnd.nextInt(45);
        p.scale = 0f;
        particles.add(p);

        ribbon.addLast(new CharCell(c, col));
        if (ribbon.size() > RIBBON_MAX) ribbon.removeFirst();
    }

    /** 中央炸出一句大字（里程碑 / 目标达成）。 */
    public void pulse(String text) {
        pulses.add(new Pulse(text));
    }

    private void step() {
        tick++;
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.age++;
            p.x += p.vx;
            p.y += p.vy;
            p.vy += 0.025f;   // 轻微重力，飘上去再缓缓落下
            p.vx *= 0.99f;
            if (p.age < 12) p.scale = Math.min(1.35f, p.scale + 0.20f);
            else p.scale = Math.max(1f, p.scale - 0.012f);
            if (p.age > p.life) it.remove();
        }
        pulses.removeIf(p -> p.age++ > 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        long t = System.currentTimeMillis();

        // 深色渐变背景
        GradientPaint bg = new GradientPaint(0, 0, new Color(24, 26, 38), 0, h, new Color(10, 12, 18));
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        // 漂浮的小光点
        g2.setColor(new Color(255, 255, 255, 16));
        for (int i = 0; i < 20; i++) {
            double a = (t / 1000.0) + i;
            int x = (int) ((w * 0.5) + Math.cos(a * 0.6 + i) * (w * 0.44));
            int y = (int) ((h * 0.5) + Math.sin(a * 0.5 + i * 1.3) * (h * 0.44));
            g2.fillOval(x, y, 3, 3);
        }

        // 底部流动“弹幕带”
        drawRibbon(g2, w, h, t);

        // 蹦迪字符
        for (Particle p : particles) {
            float alpha = Math.max(0f, Math.min(1f, 1f - (float) p.age / p.life));
            drawGlowChar(g2, p.ch, p.x, p.y, p.scale, p.color, alpha);
        }

        // 中央炸裂大字
        for (Pulse pl : pulses) {
            float a = Math.max(0f, 1f - (float) pl.age / 60f);
            int y = h / 2 - (int) ((float) pl.age / 60f * 56);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(pl.text);
            g2.setColor(new Color(255, 221, 87));
            g2.drawString(pl.text, (w - tw) / 2, y);
        }
        g2.setComposite(AlphaComposite.SrcOver);

        // 角落提示
        g2.setColor(new Color(255, 255, 255, 45));
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        g2.drawString("✨ 码字舞台 · 你敲的每一个字都在蹦迪", 12, 20);
    }

    private void drawRibbon(Graphics2D g2, int w, int h, long now) {
        int baseY = h - 14;
        int i = 0;
        for (CharCell c : ribbon) {
            int fromEnd = i;
            float yy = baseY + (float) Math.sin((now / 300.0) + fromEnd) * 3f;
            float alpha = 0.25f + 0.6f * (fromEnd == ribbon.size() - 1 ? 1f : 0.5f);
            int x = w - 20 - (ribbon.size() - 1 - fromEnd) * 16;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setFont(new Font("Consolas", Font.BOLD, 18));
            g2.setColor(c.color);
            g2.drawString(String.valueOf(c.ch), x, yy);
            i++;
        }
        g2.setComposite(AlphaComposite.SrcOver);
    }

    private void drawGlowChar(Graphics2D g2, char ch, float x, float y, float scale, Color color, float alpha) {
        AffineTransform old = g2.getTransform();
        g2.translate(x, y);
        g2.scale(scale, scale);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.35f));
        g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        g2.setColor(color);
        g2.drawString(String.valueOf(ch), -10, 8);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        g2.setColor(color.brighter());
        g2.drawString(String.valueOf(ch), -8, 6);
        g2.setTransform(old);
    }

    static final class Particle {
        char ch; float x, y, vx, vy; Color color; int age, life; float scale;
    }
    static final class CharCell {
        char ch; Color color;
        CharCell(char c, Color col) { ch = c; color = col; }
    }
    static final class Pulse {
        String text; int age = 0;
        Pulse(String t) { text = t; }
    }
}
