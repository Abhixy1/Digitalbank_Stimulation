package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import db.AccountDAO;

public class LoginPanel extends GradientPanel {
    private MainFrame mainFrame;
    private JTextField accountField;
    private JPasswordField pinField;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        // setBackground(Theme.BG_COLOR); // Removed, handled by GradientPanel

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Logo / Branding ---
        JLabel logoLabel = new JLabel("DIGITAL BANK");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(Theme.PRIMARY_COLOR); // Neon Cyan
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(logoLabel, gbc);

        // Subtitle
        JLabel subtitle = new JLabel("Secure ATM Interface");
        subtitle.setFont(Theme.SUBHEADER_FONT);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 40, 10); // More space below title
        add(subtitle, gbc);

        // --- Input Fields ---
        // Account Input
        accountField = createTextField("Account Number");
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(createLabel("Account Number"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 20, 10);
        add(accountField, gbc);

        // PIN Input
        pinField = createPasswordField();
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(createLabel("PIN"), gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 30, 10);
        add(pinField, gbc);

        // --- Login Button ---
        JButton loginBtn = createButton("LOGIN");
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // Don't stretch button too much
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginBtn, gbc);

        loginBtn.addActionListener(e -> handleLogin());
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.STANDARD_FONT);
        lbl.setForeground(Theme.TEXT_PRIMARY);
        return lbl;
    }

    private void handleLogin() {
        String accNum = accountField.getText();
        String pin = new String(pinField.getPassword());

        if (accNum.isEmpty() || pin.isEmpty()) {
            showError("Please enter both Account Number and PIN");
            return;
        }

        // Use a background thread for DB operation to avoid freezing UI
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                AccountDAO dao = new AccountDAO();
                return dao.validatePin(accNum, pin);
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        mainFrame.showScreen("DASHBOARD");
                    } else {
                        showError("Invalid Credentials");
                    }
                } catch (Exception ex) {
                    String msg = ex.getMessage();
                    if (msg != null && msg.contains("sqlite")) {
                        showError("Database driver missing!");
                    } else {
                        showError("Login Error: " + msg);
                        ex.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Access Denied", JOptionPane.ERROR_MESSAGE);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField(20); // Wider
        field.setBackground(Theme.PANEL_COLOR);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Theme.ACCENT_COLOR);
        // Modern matte border
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setFont(Theme.STANDARD_FONT);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setBackground(Theme.PANEL_COLOR);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Theme.ACCENT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setFont(Theme.STANDARD_FONT);
        return field;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.BUTTON_FONT);
        btn.setBackground(Theme.PRIMARY_COLOR);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Theme.SECONDARY_COLOR); // Violet on hover
                btn.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Theme.PRIMARY_COLOR);
                btn.setForeground(Color.BLACK);
            }
        });

        return btn;
    }
}
