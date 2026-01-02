package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import db.TransactionDAO;
import model.Transaction;

public class HistoryPanel extends JPanel {
    private MainFrame mainFrame;
    private TransactionDAO transactionDAO;
    private String currentAccount = "12345";
    private JPanel listPanel;

    public HistoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.transactionDAO = new TransactionDAO();

        setLayout(new BorderLayout());
        setBackground(Theme.BG_COLOR);

        initUI();
    }

    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.BG_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("TRANSACTION HISTORY");
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.TEXT_PRIMARY);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Theme.ERROR_COLOR);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> mainFrame.showScreen("DASHBOARD"));

        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // List
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Theme.PANEL_COLOR);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        listPanel.removeAll();
        List<Transaction> transactions = transactionDAO.getTransactions(currentAccount);

        if (transactions.isEmpty()) {
            JLabel empty = new JLabel("No transactions found.");
            empty.setForeground(Theme.TEXT_SECONDARY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalStrut(20));
            listPanel.add(empty);
        } else {
            for (Transaction t : transactions) {
                listPanel.add(createTransactionItem(t));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createTransactionItem(Transaction t) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.BG_COLOR);
        p.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        p.setMaximumSize(new Dimension(800, 60));

        JLabel typeLbl = new JLabel(t.getType());
        typeLbl.setFont(Theme.SUBHEADER_FONT);
        typeLbl.setForeground(Theme.TEXT_PRIMARY);

        JLabel amountLbl = new JLabel((t.getType().equals("Withdraw") ? "- " : "+ ") + "$" + t.getAmount());
        amountLbl.setFont(Theme.SUBHEADER_FONT);
        amountLbl.setForeground(t.getType().equals("Withdraw") ? Theme.ERROR_COLOR : Theme.SUCCESS_COLOR);

        JLabel timeLbl = new JLabel(t.getTimestamp());
        timeLbl.setFont(Theme.STANDARD_FONT);
        timeLbl.setForeground(Theme.TEXT_SECONDARY);

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(typeLbl);
        left.add(timeLbl);

        p.add(left, BorderLayout.WEST);
        p.add(amountLbl, BorderLayout.EAST);

        return p;
    }
}
