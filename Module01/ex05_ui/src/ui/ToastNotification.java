package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Animated notification toast that slides in and fades out.
 */
public class ToastNotification {

    public enum Type { SUCCESS, ERROR, INFO, WARNING }

    public static void show(JFrame parent, String message, Type type) {
        Color bg;
        Color fg = Theme.BG_DARK;
        switch (type) {
            case SUCCESS: bg = Theme.ACCENT_GREEN; break;
            case ERROR:   bg = Theme.ACCENT_RED;   break;
            case WARNING: bg = Theme.ACCENT_YELLOW; break;
            default:      bg = Theme.ACCENT_BLUE;  break;
        }

        JWindow toast = new JWindow(parent);
        toast.setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        String icon;
        switch (type) {
            case SUCCESS: icon = "\u2713 "; break;
            case ERROR:   icon = "\u2717 "; break;
            case WARNING: icon = "\u26A0 "; break;
            default:      icon = "\u2139 "; break;
        }

        JLabel label = new JLabel(icon + message);
        label.setFont(Theme.FONT_BODY);
        label.setForeground(fg);
        panel.add(label);
        toast.getContentPane().add(panel);
        toast.pack();

        // Position at top-center of parent
        Point loc = parent.getLocationOnScreen();
        int x = loc.x + (parent.getWidth() - toast.getWidth()) / 2;
        int startY = loc.y - toast.getHeight();
        int targetY = loc.y + 20;
        toast.setLocation(x, startY);
        toast.setOpacity(0f);
        toast.setVisible(true);

        // Slide-in animation
        Timer slideIn = new Timer(12, null);
        final int[] currentY = {startY};
        slideIn.addActionListener(e -> {
            int diff = (targetY - currentY[0]) / 4;
            if (diff == 0) diff = targetY > currentY[0] ? 1 : -1;
            currentY[0] += diff;
            float progress = 1f - (float)(targetY - currentY[0]) / (float)(targetY - startY);
            progress = Math.min(1f, Math.max(0f, progress));
            try {
                toast.setOpacity(progress);
            } catch (Exception ignored) {}
            toast.setLocation(x, currentY[0]);
            if (Math.abs(currentY[0] - targetY) <= 1) {
                currentY[0] = targetY;
                toast.setLocation(x, targetY);
                try { toast.setOpacity(1f); } catch (Exception ignored) {}
                slideIn.stop();

                // After 2.5s fade out
                Timer delay = new Timer(2500, ev -> {
                    Timer fadeOut = new Timer(30, null);
                    final float[] opacity = {1f};
                    fadeOut.addActionListener(fe -> {
                        opacity[0] -= 0.08f;
                        if (opacity[0] <= 0) {
                            toast.dispose();
                            ((Timer) fe.getSource()).stop();
                        } else {
                            try { toast.setOpacity(opacity[0]); } catch (Exception ignored) {}
                        }
                    });
                    fadeOut.start();
                });
                delay.setRepeats(false);
                delay.start();
            }
        });
        slideIn.start();
    }
}
