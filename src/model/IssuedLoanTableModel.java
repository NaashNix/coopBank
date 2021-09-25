/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class IssuedLoanTableModel {
    private String name;
    private String loanNumber;
    private double loanAmount;
    private double nextInstallment;
    private String paymentDate;
    private double interest;
    private int numberOfInstallments;
    private String loanStatus;

    public IssuedLoanTableModel(){}
    public IssuedLoanTableModel(String name, String loanNumber, double loanAmount, double nextInstallment, String paymentDate, double interest, int numberOfInstallments, String loanStatus) {
        this.name = name;
        this.loanNumber = loanNumber;
        this.loanAmount = loanAmount;
        this.nextInstallment = nextInstallment;
        this.paymentDate = paymentDate;
        this.interest = interest;
        this.numberOfInstallments = numberOfInstallments;
        this.loanStatus = loanStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getNextInstallment() {
        return nextInstallment;
    }

    public void setNextInstallment(double nextInstallment) {
        this.nextInstallment = nextInstallment;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}
