/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import controller.dbControllers.*;
import model.CustomerModel;
import model.CustomerModelMini;
import model.LoanDetailsModel;

import java.sql.SQLException;
import java.time.LocalDate;


public class LoanAlgorithm {

    public boolean doTheAlgorithmForInstantLoan(CustomerModelMini customer) throws SQLException, ClassNotFoundException {
        LoanDetailsModel instantLoanModel = new LoanDetailsController().getOneLoanDetail("L001");
        boolean rationLoanArrears = false; // * This one should be false.
        boolean loanByDepositArrears = false; // * This one should be false.
        boolean accountBalanceOkay = false; // * This one should be true.
        boolean accountMaturity = false; // * This should be true;


        if (customer.getRationLoan()!=null){
            rationLoanArrears = new RationLoanController().getArrearsStatus(customer.getRationLoan());
        }

        if (customer.getLoanByDeposit()!=null){
            loanByDepositArrears = new LoanByDepositController().getArrearsStatus(customer.getRationLoan());
        }

        if (new SavingsAccountController().getAccountBalance(customer.getAccountNumber()) >=
                new LoanDetailsController().getMinimumAccBalance(instantLoanModel.getLoanCode())){
            accountBalanceOkay = true;
        }


        if (!rationLoanArrears && !loanByDepositArrears && accountBalanceOkay){
            LocalDate date = new CustomerDetailsController().getJoinedDate(customer.getAccountNumber());
//            LocalDate maturedDate = date.plusMonths(instantLoanModel.getMinAccountMaturity());
//            accountMaturity = maturedDate.isBefore(LocalDate.now());
            accountMaturity = true;
        }

        return accountMaturity;
    }

    public boolean doTheAlgorithmForRationLoan(CustomerModelMini customer) throws SQLException, ClassNotFoundException {
        LoanDetailsModel rationLoanModel = new LoanDetailsController().getOneLoanDetail("L002");
        boolean instantLoanArrears = false; // * This one should be false.
        boolean loanByDepositArrears = false; // * This one should be false.
        boolean accountBalanceOkay = false; // * This one should be true.
        boolean accountMaturity = false; // * This should be true;


        if (customer.getInstantLoan()!=null){
            instantLoanArrears = new InstantLoanController().getArrearsStatus(customer.getRationLoan());
        }

        if (customer.getLoanByDeposit()!=null){
            loanByDepositArrears = new LoanByDepositController().getArrearsStatus(customer.getRationLoan());
        }

        if (new SavingsAccountController().getAccountBalance(customer.getAccountNumber()) >=
                new LoanDetailsController().getMinimumAccBalance(rationLoanModel.getLoanCode())){
            accountBalanceOkay = true;
        }


        if (!instantLoanArrears && !loanByDepositArrears && accountBalanceOkay){
            LocalDate date = new CustomerDetailsController().getJoinedDate(customer.getAccountNumber());
//            LocalDate maturedDate = date.plusMonths(rationLoanModel.getMinAccountMaturity());
//            accountMaturity = maturedDate.isBefore(LocalDate.now());
            accountMaturity = true;
        }

        return accountMaturity;
    }

    public boolean doTheAlgorithmForLoanByDeposit(CustomerModelMini customer) throws SQLException, ClassNotFoundException {
        LoanDetailsModel loanByDeposit = new LoanDetailsController().getOneLoanDetail("L003");
        boolean rationLoanArrears = false; // * This one should be false.
        boolean instantLoanArrears = false; // * This one should be false.
        boolean accountBalanceOkay = false; // * This one should be true.
        boolean accountMaturity = false; // * This should be true;


        if (customer.getRationLoan()!=null){
            rationLoanArrears = new RationLoanController().getArrearsStatus(customer.getRationLoan());
        }

        if (customer.getInstantLoan()!=null){
            instantLoanArrears = new LoanByDepositController().getArrearsStatus(customer.getRationLoan());
        }

        if (new SavingsAccountController().getAccountBalance(customer.getAccountNumber()) >=
                new LoanDetailsController().getMinimumAccBalance(loanByDeposit.getLoanCode())){
            accountBalanceOkay = true;
        }


        if (!rationLoanArrears && !instantLoanArrears && accountBalanceOkay){
            LocalDate date = new CustomerDetailsController().getJoinedDate(customer.getAccountNumber());
            //LocalDate maturedDate = date.plusMonths(loanByDeposit.getMinAccountMaturity());
            //accountMaturity = maturedDate.isBefore(LocalDate.now());
            accountMaturity = true;
        }

        return accountMaturity;
    }

    public boolean checkThatTheLoanIsIn(String accountNumber) throws SQLException, ClassNotFoundException {
        CustomerModel model = new CustomerDetailsController().getCustomerDetails(accountNumber);
        if (model.getInstantLoan().equals("") || model.getInstantLoan() == null){

        }
        return false
                ;
    }

}
