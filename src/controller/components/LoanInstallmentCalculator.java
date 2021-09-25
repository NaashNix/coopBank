/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import controller.dbControllers.InstantLoanController;
import controller.dbControllers.LoanDetailsController;
import javafx.util.Pair;
import model.InstantLoanModel;
import model.LoanDetailsModel;

import java.sql.SQLException;

public class LoanInstallmentCalculator {

    public double calculateInstantLoan(InstantLoanModel instantLoanModel){
        double balance = instantLoanModel.getiLoanAmount() - instantLoanModel.getiLoanPaidAmount();
        if (balance!=0.0){
            double interest = balance*instantLoanModel.getInterest()/100;
            return instantLoanModel.getiMonthlyInstallment()+interest;
        }
        return 0.0;
    }

    public Pair<Double, Double> calculateInstallments(String loanType, double amount, int installments) throws SQLException, ClassNotFoundException {
        LoanDetailsModel loanModel = new LoanDetailsController().getLoanModelByType(loanType);

        if (loanModel!=null) {
            if (loanModel.getInterestType().equals("Flat Rate")) {
                if (loanModel.getInterestCalPeriod().equals("Yearly")) {
                    double totalInterest = (amount * ((double) installments / 12) * loanModel.getInterest()) / installments;
                    double singleInterest = totalInterest/installments;
                    double installmentWithoutInterest = amount / installments;
                    return new Pair<Double, Double>(installmentWithoutInterest + singleInterest, installmentWithoutInterest + singleInterest);

                }
            } else if (loanModel.getInterestType().equals("Reducing BS")) {
                if (loanModel.getInterestCalPeriod().equals("Monthly")) {
                    double installment = amount / installments;
                    double firstInstallment = (amount * loanModel.getInterest() / 100) + installment;
                    return new Pair<Double, Double>(installment, firstInstallment);
                }
            }
        }else{
            System.out.println("Loan Model is null @[LoanInstallmentCalculator]");
        }
        return null;
    }
}
