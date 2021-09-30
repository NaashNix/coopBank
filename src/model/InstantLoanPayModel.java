/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class InstantLoanPayModel {
    private String iLoanNumber;
    private String accountNumber;
    private int iNumberOfInstallments;
    private double iLoanPaidAmount;
    private Date nextInstallmentDate;
    private double loanAmount;
    private double installmentValue;
    private int numOfInstallmentsTobePaid;

    public InstantLoanPayModel(String iLoanNumber, String accountNumber, int iNumberOfInstallments, double iLoanPaidAmount, Date nextInstallmentDate, double loanAmount, double installmentValue, int numOfInstallmentsTobePaid) {
        this.iLoanNumber = iLoanNumber;
        this.accountNumber = accountNumber;
        this.iNumberOfInstallments = iNumberOfInstallments;
        this.iLoanPaidAmount = iLoanPaidAmount;
        this.nextInstallmentDate = nextInstallmentDate;
        this.loanAmount = loanAmount;
        this.installmentValue = installmentValue;
        this.numOfInstallmentsTobePaid = numOfInstallmentsTobePaid;
    }

    public String getiLoanNumber() {
        return iLoanNumber;
    }

    public void setiLoanNumber(String iLoanNumber) {
        this.iLoanNumber = iLoanNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getiNumberOfInstallments() {
        return iNumberOfInstallments;
    }

    public void setiNumberOfInstallments(int iNumberOfInstallments) {
        this.iNumberOfInstallments = iNumberOfInstallments;
    }

    public double getiLoanPaidAmount() {
        return iLoanPaidAmount;
    }

    public void setiLoanPaidAmount(double iLoanPaidAmount) {
        this.iLoanPaidAmount = iLoanPaidAmount;
    }

    public Date getNextInstallmentDate() {
        return nextInstallmentDate;
    }

    public void setNextInstallmentDate(Date nextInstallmentDate) {
        this.nextInstallmentDate = nextInstallmentDate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public int getNumOfInstallmentsTobePaid() {
        return numOfInstallmentsTobePaid;
    }

    public void setNumOfInstallmentsTobePaid(int numOfInstallmentsTobePaid) {
        this.numOfInstallmentsTobePaid = numOfInstallmentsTobePaid;
    }
}
