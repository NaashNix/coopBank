/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class CommonTransactionModel {
    private String accountNumber;
    private String transactionID;
    private String transactionType;
    private String time;
    private String date;
    private String description;
    private double amount;

    public CommonTransactionModel(String accountNumber, String transactionID,
                                  String transactionType, String time, String date,
                                  String description, double amount) {
        this.accountNumber = accountNumber;
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.time = time;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public CommonTransactionModel(){}

    @Override
    public String toString() {
        return "CommonTransactionModel{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
