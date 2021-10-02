/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;
import java.sql.Time;

public class BankExpenditureModel {

    private String receiptNumber;
    private String description;
    private Date date;
    private String time;
    private double amount;

    public BankExpenditureModel(String receiptNumber, String description, Date date, String time, double amount) {
        this.receiptNumber = receiptNumber;
        this.description = description;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
