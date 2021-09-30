/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import controller.dbControllers.InstantLoanController;
import controller.dbControllers.LoanDetailsController;
import javafx.util.Pair;
import model.InstantLoanModel;
import model.LoanDetailsModel;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanInstallmentCalculator {

    public double calculateInstantLoan(InstantLoanModel instantLoanModel){
        double balance = instantLoanModel.getiLoanAmount() - instantLoanModel.getiLoanPaidAmount();
        if (balance!=0.0){
            double interest = balance*instantLoanModel.getInterest()/100;
            return instantLoanModel.getiMonthlyInstallment()+interest;
        }
        return 0.0;
    }

    public Double calculateInstallments(String loanType, double amount, int installments) throws SQLException, ClassNotFoundException {
        LoanDetailsModel loanModel = new LoanDetailsController().getLoanModelByType(loanType);

        if (loanModel!=null) {
            if (loanModel.getInterestType().equals("Flat Rate")) {
                if (loanModel.getInterestCalPeriod().equals("Yearly")) {
                    double totalInterest = (amount * ((double) installments / 12) * loanModel.getInterest()) / installments;
                    double monthlyInstallment = (amount+totalInterest)/installments;
                    return monthlyInstallment;

                }
            } else if (loanModel.getInterestType().equals("Reducing BS")) {
                if (loanModel.getInterestCalPeriod().equals("Monthly")) {
                    double monthlyInstallment = amount/installments;
                    double interest = loanModel.getInterest();
                    System.out.println("Interest : "+interest);
                    double monthUnits = ((double)installments/2)*(installments+1);
                    System.out.println("Month Units : "+monthUnits);
                    double totalInterest = (((monthlyInstallment*interest)/100)*monthUnits);
                    System.out.println("totalInterest : "+totalInterest);
                    return (amount+totalInterest)/installments;
                }
            }
        }else{
            System.out.println("Loan Model is null @[LoanInstallmentCalculator]");
        }
        return null;
    }

    public double getOnlyInterestForLoanByDeposit(double amount, String loanType, int numOfInstallments) throws SQLException, ClassNotFoundException {
        LoanDetailsModel loanModel = new LoanDetailsController().getLoanModelByType(loanType);

        double interest = loanModel.getInterest();
        double monthUnits = ((double)numOfInstallments/2)*(numOfInstallments+1);
        return (((amount*interest)/100)*monthUnits);
    }

    public Date getNextInstallmentDate(){
        LocalDate today = LocalDate.from(LocalDateTime.now());
        LocalDate nextInstallmentDay = today.plusMonths(1);
        return Date.valueOf(nextInstallmentDay);
    }
}
