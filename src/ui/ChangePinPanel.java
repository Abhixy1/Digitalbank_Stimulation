package ui;

import javax.swing.*;
import java.awt.*;
import db.AccountDAO;

public class ChangePinPanel extends JPanel {
    private String currentAccount = "12345";
    private JPasswordField oldPinField;
    private JPasswordField newPinField;
    private JPasswordField confirmPinField;
    private AccountDAO accountDAO;

    public ChangePinPanel() {
        this.accountDAO = new AccountDAO();
        setLayout(new GridBagLayout());
        setBackground(Theme.BG_COLOR);
        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("CHANGE SECURITY PIN");
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.TEXT_PRIMARY);
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy++;
        add(createLabel("Old PIN:"), gbc);
        gbc.gridx = 1;
        oldPinField = createPasswordField();
        add(oldPinField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("New PIN:"), gbc);
        gbc.gridx = 1;
        newPinField = createPasswordField();
        add(newPinField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("Confirm PIN:"), gbc);
        gbc.gridx = 1;
        confirmPinField = createPasswordField();
        add(confirmPinField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton changeBtn = new JButton("Update PIN");
        changeBtn.setFont(Theme.BUTTON_FONT);
        changeBtn.setBackground(Theme.ACCENT_COLOR);
        changeBtn.addActionListener(e -> updatePin());
        add(changeBtn, gbc);
    }

    private void updatePin() {
        String oldPin = new String(oldPinField.getPassword());
        String newPin = new String(newPinField.getPassword());
        String confirm = new String(confirmPinField.getPassword());

        if (newPin.length() < 4) {
            JOptionPane.showMessageDialog(this, "PIN must be at least 4 digits.");
            return;
        }

        if (!newPin.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "New PINs do not match.");
            return;
        }

        // Verify old PIN
        if (accountDAO.validatePin(currentAccount, oldPin)) {
            boolean success = accountDAO.updatePin(currentAccount, newPin);
            if (success) {
                JOptionPane.showMessageDialog(this, "PIN Changed Successfully!");
                oldPinField.setText("");
                newPinField.setText("");
                confirmPinField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "System Error updating PIN.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Old PIN.");
        }
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.STANDARD_FONT);
        l.setForeground(Theme.TEXT_SECONDARY);
        return l;
    }

    private JPasswordField createPasswordField() {
        JPasswordField f = new JPasswordField(10);
        f.setFont(Theme.SUBHEADER_FONT);
        return f;
    }
}
