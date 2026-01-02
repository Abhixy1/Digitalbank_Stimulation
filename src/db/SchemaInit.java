package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInit {
    public static void init() {
        String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_number TEXT PRIMARY KEY," +
                "user_id INTEGER NOT NULL," +
                "pin_hash TEXT NOT NULL," +
                "balance REAL NOT NULL," +
                "type TEXT NOT NULL" +
                ");";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_number TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "FOREIGN KEY (account_number) REFERENCES accounts(account_number)" +
                ");";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createAccountsTable);
            stmt.execute(createTransactionsTable);
            System.out.println("Database tables initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
