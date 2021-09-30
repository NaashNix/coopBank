/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class CustomerDetailModelForView {
    private String name;
    private String accountNumber;
    private double mainBalance;
    private double holedBalance;
    private String instantLoan;
    private String loanByDeposit;
    private String rationLoan;
    private String opened;

    @Override
    public String toString() {
        return "CustomerDetailModelForView{" +
                "name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", mainBalance=" + mainBalance +
                ", holedBalance=" + holedBalance +
                ", instantLoan='" + instantLoan + '\'' +
                ", loanByDeposit='" + loanByDeposit + '\'' +
                ", rationLoan='" + rationLoan + '\'' +
                ", opened='" + opened + '\'' +
                '}';
    }

    public CustomerDetailModelForView(String name, String accountNumber, double mainBalance, double holedBalance, String instantLoan, String loanByDeposit, String rationLoan, String opened) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.mainBalance = mainBalance;
        this.holedBalance = holedBalance;
        this.instantLoan = instantLoan;
        this.loanByDeposit = loanByDeposit;
        this.rationLoan = rationLoan;
        this.opened = opened;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(double mainBalance) {
        this.mainBalance = mainBalance;
    }

    public double getHoledBalance() {
        return holedBalance;
    }

    public void setHoledBalance(double holedBalance) {
        this.holedBalance = holedBalance;
    }

    public String getInstantLoan() {
        return instantLoan;
    }

    public void setInstantLoan(String instantLoan) {
        this.instantLoan = instantLoan;
    }

    public String getLoanByDeposit() {
        return loanByDeposit;
    }

    public void setLoanByDeposit(String loanByDeposit) {
        this.loanByDeposit = loanByDeposit;
    }

    public String getRationLoan() {
        return rationLoan;
    }

    public void setRationLoan(String rationLoan) {
        this.rationLoan = rationLoan;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }
}
