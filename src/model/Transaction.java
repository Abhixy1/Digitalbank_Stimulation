package model;

public class Transaction {
    private int id;
    private String accountNumber;
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(int id, String accountNumber, String type, double amount, String timestamp) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(String accountNumber, String type, double amount) {
        this(0, accountNumber, type, amount, null);
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getTransactionId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " | " + type + " | $" + String.format("%.2f", amount);
    }
}
