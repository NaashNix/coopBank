/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

import java.sql.Date;

public class ArrearsLoanDisplayModel {
    private String loanNumber;
    private String debtorName;
    private String loanType;
    private double installment;
    private double loanAmount;
    private Date paymentDate;
    private int remainingInstallments;
    private String telephone;
    private String email;
    private String address;
    private String accountNumber;

    public ArrearsLoanDisplayModel(String loanNumber, String debtorName, String loanType, double installment, double loanAmount, Date paymentDate, int remainingInstallments, String telephone, String email, String address, String accountNumber) {
        this.loanNumber = loanNumber;
        this.debtorName = debtorName;
        this.loanType = loanType;
        this.installment = installment;
        this.loanAmount = loanAmount;
        this.paymentDate = paymentDate;
        this.remainingInstallments = remainingInstallments;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.accountNumber = accountNumber;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public double getInstallment() {
        return installment;
    }

    public void setInstallment(double installment) {
        this.installment = installment;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getRemainingInstallments() {
        return remainingInstallments;
    }

    public void setRemainingInstallments(int remainingInstallments) {
        this.remainingInstallments = remainingInstallments;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
