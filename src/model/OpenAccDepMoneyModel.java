/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;


public class OpenAccDepMoneyModel {
    private String transactionID;
    private String accountNumber;
    private String description;
    private double amount;
    private String date;
    private String time;


    public OpenAccDepMoneyModel(){}

    public OpenAccDepMoneyModel(String transactionID, String accountNumber,
                                String description, double amount, String date,
                                String time) {
        this.setTransactionID(transactionID);
        this.setAccountNumber(accountNumber);
        this.setDescription(description);
        this.setAmount(amount);
        this.setDate(date);
        this.setTime(time);
    }


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
}
