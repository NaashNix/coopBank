/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class DepositObjectModel {
    private String accountNumber;
    private String date;
    private String time;
    private double amount;
    private String description;
    private String depTransactionID;

    public DepositObjectModel() { }

    @Override
    public String toString() {
        return "DepositObjectModel{" +
                "accountNumber='" + accountNumber + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", depTransactionID='" + depTransactionID + '\'' +
                '}';
    }

    public DepositObjectModel(String accountNumber, String date, String time,
                              double amount, String description, String depTransactionID) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.description = description;
        this.depTransactionID = depTransactionID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepTransactionID() {
        return depTransactionID;
    }

    public void setDepTransactionID(String depTransactionID) {
        this.depTransactionID = depTransactionID;
    }
}
