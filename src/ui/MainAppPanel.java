package ui;

import javax.swing.*;
import java.awt.*;

public class MainAppPanel extends JPanel {
    private MainFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private SidebarPanel sidebar;

    // Screens
    private DashboardPanel dashboardPanel;
    private ActionPanel depositPanel;
    private ActionPanel withdrawPanel;
    private HistoryPanel historyPanel;
    private ChangePinPanel changePinPanel;

    public MainAppPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);

        // Content Area
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BG_COLOR);
        add(contentPanel, BorderLayout.CENTER);

        // Init Screens
        // Note: passing 'this' instead of 'mainFrame' where appropriate would be better
        // structure,
        // but for now reusing existing panels that expect MainFrame might require
        // tweaks if they call showScreen on MainFrame directly.
        // Actually, we should intercept those calls or update the panels.
        // Let's pass mainFrame to them for now, but update DashboardPanel later.

        dashboardPanel = new DashboardPanel(mainFrame);
        depositPanel = new ActionPanel(mainFrame, "Deposit");
        withdrawPanel = new ActionPanel(mainFrame, "Withdraw");
        historyPanel = new HistoryPanel(mainFrame);
        changePinPanel = new ChangePinPanel();

        contentPanel.add(dashboardPanel, "HOME");
        contentPanel.add(depositPanel, "DEPOSIT");
        contentPanel.add(withdrawPanel, "WITHDRAW");
        contentPanel.add(historyPanel, "HISTORY");
        contentPanel.add(changePinPanel, "CHANGE_PIN");
    }

    public void showScreen(String name) {
        if (name.equals("HISTORY")) {
            historyPanel.refresh();
        }
        cardLayout.show(contentPanel, name);
    }

    public void logout() {
        mainFrame.showScreen("LOGIN");
    }
}
