/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class InstantLoanModel {
    private String iLoanNumber;
    private String accountNumber;
    private double iLoanAmount;
    private String iIssuedDate;
    private double iMonthlyInstallment;
    private double interest;
    private int iNumberOfInstallments;
    private int installmentsToBePaid;
    private double iLoanPaidAmount;
    private String loanStatus;
    private Date nextInstallmentDate;

    @Override
    public String toString() {
        return "InstantLoanModel{" +
                "iLoanNumber='" + iLoanNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", iLoanAmount=" + iLoanAmount +
                ", iIssuedDate='" + iIssuedDate + '\'' +
                ", iMonthlyInstallment=" + iMonthlyInstallment +
                ", interest=" + interest +
                ", iNumberOfInstallments=" + iNumberOfInstallments +
                ", iLoanPaidAmount=" + iLoanPaidAmount +
                ", loanStatus='" + loanStatus + '\'' +
                ", nextInstallmentDate=" + nextInstallmentDate +
                '}';
    }

    public InstantLoanModel(String iLoanNumber, String accountNumber, double iLoanAmount, String iIssuedDate, double iMonthlyInstallment, double interest, int iNumberOfInstallments, int installmentsToBePaid, double iLoanPaidAmount, String loanStatus, Date nextInstallmentDate) {
        this.iLoanNumber = iLoanNumber;
        this.accountNumber = accountNumber;
        this.iLoanAmount = iLoanAmount;
        this.iIssuedDate = iIssuedDate;
        this.iMonthlyInstallment = iMonthlyInstallment;
        this.interest = interest;
        this.iNumberOfInstallments = iNumberOfInstallments;
        this.installmentsToBePaid = installmentsToBePaid;
        this.iLoanPaidAmount = iLoanPaidAmount;
        this.loanStatus = loanStatus;
        this.nextInstallmentDate = nextInstallmentDate;
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

    public double getiLoanAmount() {
        return iLoanAmount;
    }

    public void setiLoanAmount(double iLoanAmount) {
        this.iLoanAmount = iLoanAmount;
    }

    public String getiIssuedDate() {
        return iIssuedDate;
    }

    public void setiIssuedDate(String iIssuedDate) {
        this.iIssuedDate = iIssuedDate;
    }

    public double getiMonthlyInstallment() {
        return iMonthlyInstallment;
    }

    public void setiMonthlyInstallment(double iMonthlyInstallment) {
        this.iMonthlyInstallment = iMonthlyInstallment;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
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

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Date getNextInstallmentDate() {
        return nextInstallmentDate;
    }

    public void setNextInstallmentDate(Date nextInstallmentDate) {
        this.nextInstallmentDate = nextInstallmentDate;
    }

    public int getInstallmentsToBePaid() {
        return installmentsToBePaid;
    }

    public void setInstallmentsToBePaid(int installmentsToBePaid) {
        this.installmentsToBePaid = installmentsToBePaid;
    }
}
