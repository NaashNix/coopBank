package model;

public class BankDetailsModel {
    private String bankName;
    private String branchName;
    private String branch;
    private String regNumber;
    private String depDate;

    @Override
    public String toString() {
        return "BankDetailsModel{" +
                "bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branch='" + branch + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", depDate='" + depDate + '\'' +
                '}';
    }



    public BankDetailsModel(String bankName, String branchName,
                            String branch, String regNumber, String depDate) {
        this.bankName = bankName;
        this.branchName = branchName;
        this.branch = branch;
        this.regNumber = regNumber;
        this.depDate = depDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }
}
