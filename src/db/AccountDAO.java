package db;

import model.Account;
import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final Gson gson = new Gson();

    public Account getAccount(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account(
                            rs.getString("account_number"),
                            rs.getInt("user_id"),
                            rs.getDouble("balance"),
                            rs.getString("type"));
                    account.setPinHash(rs.getString("pin_hash"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validatePin(String accountNumber, String pinHash) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            return account.getPinHash().equals(pinHash);
        }
        return false;
    }

    public boolean updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createAccount(String accountNumber, int userId, String pin, double initialBalance) {
        String sql = "INSERT INTO accounts (account_number, user_id, pin_hash, balance, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setInt(2, userId);
            pstmt.setString(3, pin);
            pstmt.setDouble(4, initialBalance);
            pstmt.setString(5, "SAVINGS");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updatePin(String accountNumber, String newPin) {
        String sql = "UPDATE accounts SET pin_hash = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPin);
            pstmt.setString(2, accountNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toJson(String accountNumber) {
        Account account = getAccount(accountNumber);
        return gson.toJson(account);
    }
}
