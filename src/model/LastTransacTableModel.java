/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class LastTransacTableModel {
    private String date;
    private String accountType;
    private String description;
    private double amount;

    @Override
    public String toString() {
        return "LastTransacTableModel{" +
                "date='" + date + '\'' +
                ", accountType='" + accountType + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    public LastTransacTableModel() {
    }

    public LastTransacTableModel(String date, String accountType,
                                 String description, double amount) {
        this.date = date;
        this.accountType = accountType;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
