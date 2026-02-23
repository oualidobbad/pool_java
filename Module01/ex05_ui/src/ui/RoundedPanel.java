package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Rounded panel with optional fade-in animation.
 */
public class RoundedPanel extends JPanel {

    private Color bgColor;
    private int radius;
    private float opacity = 1f;

    public RoundedPanel(Color bg, int radius) {
        this.bgColor = bg;
        this.radius = radius;
        setOpaque(false);
    }

    public RoundedPanel(Color bg) {
        this(bg, Theme.CORNER_RADIUS);
    }

    public void setBgColor(Color bg) {
        this.bgColor = bg;
        repaint();
    }

    /** Trigger a fade-in animation from transparent to full opacity */
    public void fadeIn() {
        opacity = 0f;
        repaint();
        Timer timer = new Timer(16, null);
        timer.addActionListener(e -> {
            opacity += 0.08f;
            if (opacity >= 1f) {
                opacity = 1f;
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
