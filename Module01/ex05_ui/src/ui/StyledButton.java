package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A modern styled button with hover animation.
 */
public class StyledButton extends JButton {

    private Color bgColor;
    private Color hoverColor;
    private Color currentBg;
    private Color textColor;
    private boolean isSelected = false;
    private float animProgress = 0f;
    private Timer animTimer;

    public StyledButton(String text, Color bg, Color hover, Color textColor) {
        super(text);
        this.bgColor = bg;
        this.hoverColor = hover;
        this.currentBg = bg;
        this.textColor = textColor;

        setFont(Theme.FONT_BTN);
        setForeground(textColor);
        setBackground(bg);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(Theme.SIDEBAR_WIDTH - 30, 40));
        setMaximumSize(new Dimension(Theme.SIDEBAR_WIDTH - 30, 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) animateTo(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) animateTo(bgColor);
            }
        });
    }

    public StyledButton(String text) {
        this(text, Theme.BTN_DEFAULT, Theme.BTN_HOVER, Theme.TEXT_PRIMARY);
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            currentBg = Theme.BTN_ACTIVE;
            setForeground(Theme.BG_DARK);
        } else {
            currentBg = bgColor;
            setForeground(textColor);
        }
        repaint();
    }

    private void animateTo(Color target) {
        if (animTimer != null && animTimer.isRunning()) animTimer.stop();

        final Color start = currentBg;
        final int steps = 8;
        final int[] step = {0};

        animTimer = new Timer(16, e -> {
            step[0]++;
            float t = Math.min(1f, (float) step[0] / steps);
            t = t * t * (3 - 2 * t); // smoothstep
            currentBg = interpolate(start, target, t);
            repaint();
            if (step[0] >= steps) {
                ((Timer) e.getSource()).stop();
            }
        });
        animTimer.start();
    }

    private Color interpolate(Color a, Color b, float t) {
        return new Color(
                clamp((int) (a.getRed() + (b.getRed() - a.getRed()) * t)),
                clamp((int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t)),
                clamp((int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t))
        );
    }

    private int clamp(int v) {
        return Math.max(0, Math.min(255, v));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(currentBg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.CORNER_RADIUS, Theme.CORNER_RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}
