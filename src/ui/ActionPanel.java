package ui;

import javax.swing.*;
import java.awt.*;
import db.AccountDAO;
import db.TransactionDAO;
import model.Transaction;

public class ActionPanel extends JPanel {
    private MainFrame mainFrame;
    private String type; // "Deposit" or "Withdraw"
    private JTextField amountField;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private String currentAccount = "12345"; // Hardcoded for this phase until we pass user state

    public ActionPanel(MainFrame mainFrame, String type) {
        this.mainFrame = mainFrame;
        this.type = type;
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();

        setLayout(new GridBagLayout());
        setBackground(Theme.BG_COLOR);

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Title
        JLabel title = new JLabel(type.toUpperCase());
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        // Input
        amountField = new JTextField(10);
        amountField.setFont(Theme.SUBHEADER_FONT);
        gbc.gridy = 1;
        add(amountField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);

        JButton actionBtn = new JButton("Confirm");
        actionBtn.setFont(Theme.BUTTON_FONT);
        actionBtn.setBackground(Theme.SUCCESS_COLOR);
        actionBtn.addActionListener(e -> performTransaction());

        JButton backBtn = new JButton("Back");
        backBtn.setFont(Theme.BUTTON_FONT);
        backBtn.setBackground(Theme.ERROR_COLOR);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> mainFrame.showScreen("DASHBOARD"));

        btnPanel.add(actionBtn);
        btnPanel.add(backBtn);

        gbc.gridy = 2;
        add(btnPanel, gbc);
    }

    private void performTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive.");
                return;
            }

            // Logic
            model.Account acc = accountDAO.getAccount(currentAccount);
            if (acc == null) {
                JOptionPane.showMessageDialog(this, "Account error.");
                return;
            }

            double newBalance = acc.getBalance();

            if (type.equals("Withdraw")) {
                if (acc.getBalance() >= amount) {
                    newBalance -= amount;
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Funds.");
                    return;
                }
            } else {
                newBalance += amount;
            }

            // Update DB
            boolean success = accountDAO.updateBalance(currentAccount, newBalance);
            if (success) {
                transactionDAO.addTransaction(new Transaction(currentAccount, type, amount));
                JOptionPane.showMessageDialog(this, "Transaction Successful!\nNew Balance: $" + newBalance);
                mainFrame.showScreen("DASHBOARD");
                amountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "System Error.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
        }
    }
}
