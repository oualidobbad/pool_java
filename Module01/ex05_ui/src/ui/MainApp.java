package ui;

import logic.*;
import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the Transactions Manager GUI application.
 */
public class MainApp {

    public static void main(String[] args) {
        boolean devMode = false;
        for (String arg : args) {
            if ("--profile=dev".equals(arg)) {
                devMode = true;
            }
        }

        final boolean isDev = devMode;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(isDev);
            frame.setVisible(true);
        });
    }
}
