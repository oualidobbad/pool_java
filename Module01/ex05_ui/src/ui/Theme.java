package ui;

import java.awt.*;

/**
 * Central color and font theme for the application.
 */
public final class Theme {

    private Theme() {}

    // ── Color Palette (Catppuccin Mocha inspired) ──
    public static final Color BG_DARK       = new Color(30, 30, 46);
    public static final Color BG_SURFACE    = new Color(49, 50, 68);
    public static final Color BG_OVERLAY    = new Color(69, 71, 90);
    public static final Color BG_CARD       = new Color(55, 56, 76);

    public static final Color TEXT_PRIMARY  = new Color(205, 214, 244);
    public static final Color TEXT_SECONDARY= new Color(166, 173, 200);
    public static final Color TEXT_DIM      = new Color(127, 132, 156);

    public static final Color ACCENT_BLUE   = new Color(137, 180, 250);
    public static final Color ACCENT_GREEN  = new Color(166, 227, 161);
    public static final Color ACCENT_RED    = new Color(243, 139, 168);
    public static final Color ACCENT_YELLOW = new Color(249, 226, 175);
    public static final Color ACCENT_MAUVE  = new Color(203, 166, 247);
    public static final Color ACCENT_TEAL   = new Color(148, 226, 213);
    public static final Color ACCENT_PEACH  = new Color(250, 179, 135);

    // ── Button colors ──
    public static final Color BTN_DEFAULT   = BG_OVERLAY;
    public static final Color BTN_HOVER     = new Color(88, 91, 112);
    public static final Color BTN_ACTIVE    = ACCENT_BLUE;

    // ── Fonts ──
    public static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADING   = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY      = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_MONO      = new Font("JetBrains Mono", Font.PLAIN, 13);
    public static final Font FONT_BTN       = new Font("Segoe UI", Font.BOLD, 13);

    // ── Dimensions ──
    public static final int SIDEBAR_WIDTH   = 220;
    public static final int CORNER_RADIUS   = 12;
}
