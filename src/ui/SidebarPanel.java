package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarPanel extends JPanel {
    private MainAppPanel appPanel;

    public SidebarPanel(MainAppPanel appPanel) {
        this.appPanel = appPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Theme.PANEL_COLOR);
        setPreferredSize(new Dimension(200, 0));

        // Logo / Title area
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(Theme.PANEL_COLOR);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel logo = new JLabel("<html>DIGITAL<br>BANK</html>");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logoPanel.add(logo, BorderLayout.CENTER);
        logoPanel.setMaximumSize(new Dimension(200, 100));
        add(logoPanel);

        add(Box.createVerticalStrut(20));

        // Options
        addButton("Home", "HOME");
        addButton("Deposit", "DEPOSIT");
        addButton("Withdraw", "WITHDRAW");
        addButton("History", "HISTORY");
        addButton("Change PIN", "CHANGE_PIN");

        add(Box.createVerticalGlue()); // Push logout to bottom
        addButton("Logout", "LOGOUT");
    }

    private void addButton(String text, String command) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.SUBHEADER_FONT);
        btn.setForeground(Theme.TEXT_SECONDARY);
        btn.setBackground(Theme.PANEL_COLOR);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Theme.BG_COLOR);
                btn.setForeground(Theme.ACCENT_COLOR);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Theme.PANEL_COLOR);
                btn.setForeground(Theme.TEXT_SECONDARY);
            }
        });

        btn.addActionListener(e -> {
            if (command.equals("LOGOUT")) {
                appPanel.logout();
            } else {
                appPanel.showScreen(command);
            }
        });

        add(btn);
    }
}
