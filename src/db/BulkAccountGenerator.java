package db;

import java.util.Random;

public class BulkAccountGenerator {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        Random random = new Random();

        System.out.println("Generating 20 new accounts...");

        for (int i = 1; i <= 20; i++) {
            String accountNumber = String.format("A%04d", i + 100); // A0101, A0102, etc.
            int userId = 1000 + i;
            String pin = String.format("%04d", random.nextInt(10000)); // Random 4-digit PIN
            double initialBalance = 1000.0 + (random.nextDouble() * 9000.0); // Random balance 1k-10k

            try {
                // Check if account already exists to avoid unique constraint failure
                if (accountDAO.getAccount(accountNumber) == null) {
                    accountDAO.createAccount(accountNumber, userId, pin, initialBalance);
                    System.out.printf("Created Account: %s | UserID: %d | PIN: %s | Balance: $%.2f%n",
                            accountNumber, userId, pin, initialBalance);
                } else {
                    System.out.printf("Account %s already exists. Skipping.%n", accountNumber);
                }
            } catch (Exception e) {
                System.err.println("Error creating account " + accountNumber + ": " + e.getMessage());
            }
        }

        System.out.println("Bulk account generation completed.");
    }
}
