package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private HistoryPanel historyPanel;

    public MainFrame() {
        setTitle("Digital ATM");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Modern look, no window borders

        // Main layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Theme.BG_COLOR);

        // Add standard border/title bar since we are undecorated
        add(createTitleBar(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Initialize Screens
        mainPanel.add(new LoginPanel(this), "LOGIN");
        // Lazy loading of main app panel could be done, but we'll init it here
        // However, MainAppPanel needs MainFrame, and it creates its own children.
        // We'll init it when needed or just here.

        // Note: We need a way to reference the MainAppPanel to call showScreen on IT
        // when reusing the old panels (Dashboard, ActionPanel) that call
        // mainFrame.showScreen()

        cardLayout.show(mainPanel, "LOGIN"); // Default
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Theme.PANEL_COLOR);
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel title = new JLabel("  DIGITAL BANK ATM");
        title.setForeground(Theme.TEXT_SECONDARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JButton closeBtn = new JButton("X");
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(Theme.ERROR_COLOR);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setPreferredSize(new Dimension(40, 30));
        closeBtn.addActionListener(e -> System.exit(0));

        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(closeBtn, BorderLayout.EAST);

        return titleBar;
    }

    private MainAppPanel appPanel;

    public void showScreen(String name) {
        if (name.equals("LOGIN")) {
            cardLayout.show(mainPanel, "LOGIN");
            appPanel = null; // Reset app state
        } else if (name.equals("DASHBOARD")) {
            // This corresponds to successful login
            appPanel = new MainAppPanel(this);
            mainPanel.add(appPanel, "APP");
            cardLayout.show(mainPanel, "APP");
            appPanel.showScreen("HOME");
        } else {
            // If we are in APP mode, delegate
            if (appPanel != null) {
                appPanel.showScreen(name);
            } else {
                // Fallback or error
                System.err.println("Attempted to show " + name + " but appPanel is null");
            }
        }
    }

    public static void main(String[] args) {
        // Initialize database and migrate data if necessary
        db.SchemaInit.init();
        db.DataMigration.migrate();

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
