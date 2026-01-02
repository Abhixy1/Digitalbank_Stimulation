package ui;

import javax.swing.*;
import java.awt.*;
import db.AccountDAO;
import model.Account;

public class DashboardPanel extends JPanel {
    private MainFrame mainFrame;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_COLOR);

        // Menu grid
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        menuPanel.add(createMenuButton("Check Balance", () -> showBalance()));
        menuPanel.add(createMenuButton("Deposit", () -> mainFrame.showScreen("DEPOSIT")));
        menuPanel.add(createMenuButton("Withdraw", () -> mainFrame.showScreen("WITHDRAW")));
        menuPanel.add(createMenuButton("History", () -> {
            mainFrame.showScreen("HISTORY");
            // Ideally we should refresh history here, but we can do it in MainFrame or
            // HistoryPanel via show hook
        }));
        menuPanel.add(createMenuButton("Logout", () -> mainFrame.showScreen("LOGIN")));

        add(menuPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("DASHBOARD", SwingConstants.CENTER);
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(title, BorderLayout.NORTH);
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.SUBHEADER_FONT);
        btn.setBackground(Theme.PANEL_COLOR);
        btn.setForeground(Theme.ACCENT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Theme.ACCENT_COLOR));
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void showBalance() {
        // Retrieve fresh balance
        AccountDAO dao = new AccountDAO();
        Account acc = dao.getAccount("12345"); // Hardcoded current user
        if (acc != null) {
            JOptionPane.showMessageDialog(this, "Current Balance: $" + acc.getBalance());
        } else {
            JOptionPane.showMessageDialog(this, "Error retrieving balance");
        }
    }
}
