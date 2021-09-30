/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class LoanByDeposit {
    private String LoanNumber;
    private String accountNumber;
    private double LoanAmount;
    private String IssuedDate;
    private double MonthlyInstallment;
    private int NumberOfInstallments;
    private int installmentsToBePaid;
    private double LoanPaidAmount;
    private String loanStatus;
    private Date nextInstallmentDate;
    private double interest;

    public LoanByDeposit(){}

    public LoanByDeposit(String loanNumber, String accountNumber, double loanAmount, String issuedDate, double monthlyInstallment, int numberOfInstallments, int installmentsToBePaid, double loanPaidAmount, String loanStatus, Date nextInstallmentDate, double interest) {
        LoanNumber = loanNumber;
        this.accountNumber = accountNumber;
        LoanAmount = loanAmount;
        IssuedDate = issuedDate;
        MonthlyInstallment = monthlyInstallment;
        NumberOfInstallments = numberOfInstallments;
        this.installmentsToBePaid = installmentsToBePaid;
        LoanPaidAmount = loanPaidAmount;
        this.loanStatus = loanStatus;
        this.nextInstallmentDate = nextInstallmentDate;
        this.interest = interest;
    }

    public String getLoanNumber() {
        return LoanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        LoanNumber = loanNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        LoanAmount = loanAmount;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public double getMonthlyInstallment() {
        return MonthlyInstallment;
    }

    public void setMonthlyInstallment(double monthlyInstallment) {
        MonthlyInstallment = monthlyInstallment;
    }

    public int getNumberOfInstallments() {
        return NumberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        NumberOfInstallments = numberOfInstallments;
    }

    public double getLoanPaidAmount() {
        return LoanPaidAmount;
    }

    public void setLoanPaidAmount(double loanPaidAmount) {
        LoanPaidAmount = loanPaidAmount;
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

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getInstallmentsToBePaid() {
        return installmentsToBePaid;
    }

    public void setInstallmentsToBePaid(int installmentsToBePaid) {
        this.installmentsToBePaid = installmentsToBePaid;
    }
}
