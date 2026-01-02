package ui;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {

    public GradientPanel() {
        setOpaque(false); // Make sure the gradient is visible (though paintComponent handles it)
        // We handle painting ourselves
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // High quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Diagonal gradient from Top-Left to Bottom-Right
        GradientPaint gp = new GradientPaint(
                0, 0, Theme.GRADIENT_START,
                w, h, Theme.GRADIENT_END);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
