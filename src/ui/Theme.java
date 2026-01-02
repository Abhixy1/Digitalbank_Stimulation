package ui;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    // Colors
    // Deep Midnight Blue Palette
    public static final Color BG_COLOR = new Color(3, 7, 18); // #030712
    public static final Color GRADIENT_START = new Color(3, 7, 18);
    public static final Color GRADIENT_END = new Color(17, 24, 39); // #111827
    
    public static final Color PANEL_COLOR = new Color(31, 41, 55, 200); // Semi-transparent
    
    public static final Color PRIMARY_COLOR = new Color(6, 182, 212); // #06b6d4 (Cyan)
    public static final Color SECONDARY_COLOR = new Color(139, 92, 246); // #8b5cf6 (Violet)
    public static final Color ACCENT_COLOR = PRIMARY_COLOR; 
    
    public static final Color TEXT_PRIMARY = new Color(249, 250, 251); // #f9fafb
    public static final Color TEXT_SECONDARY = new Color(156, 163, 175); // #9ca3af
    
    public static final Color ERROR_COLOR = new Color(239, 68, 68);
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);

    // Fonts
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font STANDARD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 15);
}
