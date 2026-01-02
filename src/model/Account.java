package model;

public class Account {
    private String accountNumber;
    private int userId;
    private String pinHash;
    private double balance;
    private String type;

    public Account(String accountNumber, int userId, double balance, String type) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }
}
