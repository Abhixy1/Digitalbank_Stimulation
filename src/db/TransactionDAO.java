package db;

import model.Transaction;
import com.google.gson.Gson;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final Gson gson = new Gson();

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_number, type, amount, timestamp) VALUES (?, ?, ?, ?)";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getAccountNumber());
            pstmt.setString(2, transaction.getType());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactions(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                            rs.getInt("id"),
                            rs.getString("account_number"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getString("timestamp")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public String toJsonArray(String accountNumber) {
        List<Transaction> transactions = getTransactions(accountNumber);
        return gson.toJson(transactions);
    }
}
