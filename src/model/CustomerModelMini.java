/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package model;

public class CustomerModelMini {
    private String name;
    private String accountNumber;
    private String telephone;
    private String rationLoan;
    private String loanByDeposit;
    private String instantLoan;

    public CustomerModelMini(String name, String accountNumber,
                             String telephone, String rationLoan,
                             String loanByDeposit, String instantLoan) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.telephone = telephone;
        this.rationLoan = rationLoan;
        this.loanByDeposit = loanByDeposit;
        this.instantLoan = instantLoan;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRationLoan() {
        return rationLoan;
    }

    public void setRationLoan(String rationLoan) {
        this.rationLoan = rationLoan;
    }

    public String getLoanByDeposit() {
        return loanByDeposit;
    }

    public void setLoanByDeposit(String loanByDeposit) {
        this.loanByDeposit = loanByDeposit;
    }

    public String getInstantLoan() {
        return instantLoan;
    }

    public void setInstantLoan(String instantLoan) {
        this.instantLoan = instantLoan;
    }
}
