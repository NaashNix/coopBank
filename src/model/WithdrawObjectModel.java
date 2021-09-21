/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class WithdrawObjectModel {
    private String accountNumber;
    private String date;
    private String time;
    private double amount;
    private String description;
    private String withdrawTransactionID;

    public WithdrawObjectModel(){}

    public WithdrawObjectModel(String accountNumber, String date,
                               String time, double amount, String description, String withdrawTransactionID) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.description = description;
        this.withdrawTransactionID = withdrawTransactionID;
    }

    @Override
    public String toString() {
        return "WithdrawObjectModel{" +
                "accountNumber='" + getAccountNumber() + '\'' +
                ", date='" + getDate() + '\'' +
                ", time='" + getTime() + '\'' +
                ", amount=" + getAmount() +
                ", description='" + getDescription() + '\'' +
                ", withdrawTransactionID='" + getWithdrawTransactionID() + '\'' +
                '}';
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

    public String getWithdrawTransactionID() {
        return withdrawTransactionID;
    }

    public void setWithdrawTransactionID(String withdrawTransactionID) {
        this.withdrawTransactionID = withdrawTransactionID;
    }
}
