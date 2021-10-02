/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class IncomeViewerModel {

    private String transactionID;
    private String desc;
    private Date date;
    private String time;
    private double amount;

    public IncomeViewerModel(String transactionID, String desc, Date date, String time, double amount) {
        this.transactionID = transactionID;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
