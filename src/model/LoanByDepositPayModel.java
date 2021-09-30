/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class LoanByDepositPayModel {
    private String LoanNumber;
    private String accountNumber;
    private int NumberOfInstallments;
    private double LoanPaidAmount;
    private Date nextInstallmentDate;
    private double loanAmount;
    private double installmentValue;
    private int numOfInstallmentsTobePaid;

    public LoanByDepositPayModel(String LoanNumber, String accountNumber, int NumberOfInstallments, double LoanPaidAmount, Date nextInstallmentDate, double loanAmount, double installmentValue, int numOfInstallmentsTobePaid) {
        this.setLoanNumber(LoanNumber);
        this.setAccountNumber(accountNumber);
        this.setNumberOfInstallments(NumberOfInstallments);
        this.setLoanPaidAmount(LoanPaidAmount);
        this.setNextInstallmentDate(nextInstallmentDate);
        this.setLoanAmount(loanAmount);
        this.setInstallmentValue(installmentValue);
        this.setNumOfInstallmentsTobePaid(numOfInstallmentsTobePaid);
    }

    public String getLoanNumber() {
        return LoanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.LoanNumber = loanNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getNumberOfInstallments() {
        return NumberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.NumberOfInstallments = numberOfInstallments;
    }

    public double getLoanPaidAmount() {
        return LoanPaidAmount;
    }

    public void setLoanPaidAmount(double loanPaidAmount) {
        this.LoanPaidAmount = loanPaidAmount;
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
