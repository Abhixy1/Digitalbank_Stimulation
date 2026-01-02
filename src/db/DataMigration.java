package db;

import model.Account;
import model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataMigration {
    private static final Gson gson = new Gson();

    public static void migrate() {
        try (Connection conn = DBConnection.getConnection()) {
            if (isDatabaseEmpty(conn)) {
                System.out.println("Database is empty. Starting migration from JSON...");
                migrateAccounts(conn);
                migrateTransactions(conn);
                System.out.println("Migration completed successfully.");
                backupJsonFiles();
            } else {
                System.out.println("Database already contains data. Skipping migration.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDatabaseEmpty(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT count(*) FROM accounts")) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private static void migrateAccounts(Connection conn) {
        File file = new File(DBConnection.getAccountsFile());
        if (!file.exists())
            return;

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Account>>() {
            }.getType();
            List<Account> accounts = gson.fromJson(reader, listType);
            if (accounts == null)
                return;

            String sql = "INSERT INTO accounts (account_number, user_id, pin_hash, balance, type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Account acc : accounts) {
                    pstmt.setString(1, acc.getAccountNumber());
                    pstmt.setInt(2, acc.getUserId());
                    pstmt.setString(3, acc.getPinHash());
                    pstmt.setDouble(4, acc.getBalance());
                    pstmt.setString(5, acc.getType());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                System.out.println("Migrated " + accounts.size() + " accounts.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void migrateTransactions(Connection conn) {
        File file = new File(DBConnection.getTransactionsFile());
        if (!file.exists())
            return;

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Transaction>>() {
            }.getType();
            List<Transaction> transactions = gson.fromJson(reader, listType);
            if (transactions == null)
                return;

            String sql = "INSERT INTO transactions (account_number, type, amount, timestamp) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Transaction t : transactions) {
                    pstmt.setString(1, t.getAccountNumber());
                    pstmt.setString(2, t.getType());
                    pstmt.setDouble(3, t.getAmount());
                    pstmt.setString(4, t.getTimestamp());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                System.out.println("Migrated " + transactions.size() + " transactions.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void backupJsonFiles() {
        backupFile(DBConnection.getAccountsFile());
        backupFile(DBConnection.getTransactionsFile());
    }

    private static void backupFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                Files.move(file.toPath(), new File(path + ".bak").toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Backed up " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
