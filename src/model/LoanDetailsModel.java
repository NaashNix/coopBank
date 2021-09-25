/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class LoanDetailsModel {
    private String loanName;
    private String loanCode;
    private String forWhom;
    private double minimumAccountBalance;
    private int minAccountMaturity;
    private double maximumLoanAmount;
    private String interestType;
    private double interest;
    private String interestCalPeriod;
    private int maximumNoOfInstallments;
    private String nonPayOption1;
    private String nonPayOption2;

    public LoanDetailsModel(String loanName, String loanCode, String forWhom, double minimumAccountBalance, int minAccountMaturity, double maximumLoanAmount, String interestType, double interest, String interestCalPeriod, int maximumNoOfInstallments, String nonPayOption1,String nonPayOption2) {
        this.loanName = loanName;
        this.loanCode = loanCode;
        this.forWhom = forWhom;
        this.minimumAccountBalance = minimumAccountBalance;
        this.minAccountMaturity = minAccountMaturity;
        this.maximumLoanAmount = maximumLoanAmount;
        this.interestType = interestType;
        this.interest = interest;
        this.interestCalPeriod = interestCalPeriod;
        this.maximumNoOfInstallments = maximumNoOfInstallments;
        this.nonPayOption1 = nonPayOption1;
        this.nonPayOption2 = nonPayOption2;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public void setForWhom(String forWhom) {
        this.forWhom = forWhom;
    }

    public void setMinimumAccountBalance(double minimumAccountBalance) {
        this.minimumAccountBalance = minimumAccountBalance;
    }

    public void setMinAccountMaturity(int minAccountMaturity) {
        this.minAccountMaturity = minAccountMaturity;
    }

    public void setMaximumLoanAmount(double maximumLoanAmount) {
        this.maximumLoanAmount = maximumLoanAmount;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void setInterestCalPeriod(String interestCalPeriod) {
        this.interestCalPeriod = interestCalPeriod;
    }

    public void setMaximumNoOfInstallments(int maximumNoOfInstallments) {
        this.maximumNoOfInstallments = maximumNoOfInstallments;
    }

    public void setNonPayOption1(String nonPayOption1) {
        this.nonPayOption1 = nonPayOption1;
    }

    public void setNonPayOption2(String nonPayOption2) {
        this.nonPayOption2 = nonPayOption2;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public String getForWhom() {
        return forWhom;
    }

    public double getMinimumAccountBalance() {
        return minimumAccountBalance;
    }

    public int getMinAccountMaturity() {
        return minAccountMaturity;
    }

    public double getMaximumLoanAmount() {
        return maximumLoanAmount;
    }

    public String getInterestType() {
        return interestType;
    }

    public double getInterest() {
        return interest;
    }

    public String getInterestCalPeriod() {
        return interestCalPeriod;
    }

    public int getMaximumNoOfInstallments() {
        return maximumNoOfInstallments;
    }

    public String getNonPayOption1() {
        return nonPayOption1;
    }

    public String getNonPayOption2() {
        return nonPayOption2;
    }
}
