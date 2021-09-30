/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import controller.components.NumberGenerator;
import db.DbConnection;
import model.IncomeTransactionModel;
import model.LoanByDeposit;
import model.LoanByDepositPayModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanByDepositController  {
    public boolean getArrearsStatus(String dLoanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT loanStatus FROM LoanByDeposit WHERE dLoanNumber=?");
        statement.setObject(1,dLoanNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            if (resultSet.getString(1).equals("arrears")){
                return true;
            }else return resultSet.getString(1).equals("Stopped");
        }
        return false;
    }

    public boolean saveLoan(LoanByDeposit model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO LoanByDeposit VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        connection.setAutoCommit(false);
        statement.setObject(1,model.getLoanNumber());
        statement.setObject(2,model.getAccountNumber());
        statement.setObject(3,model.getLoanAmount());
        statement.setObject(4,model.getIssuedDate());
        statement.setObject(5,model.getMonthlyInstallment());
        statement.setObject(6,model.getNumberOfInstallments());
        statement.setObject(7,model.getInstallmentsToBePaid());
        statement.setObject(8,model.getLoanPaidAmount());
        statement.setObject(9,model.getLoanStatus());
        statement.setObject(10,model.getNextInstallmentDate());
        statement.setObject(11,model.getInterest());

        if (statement.executeUpdate()>0){
            PreparedStatement customerStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET loanByDeposit=? WHERE accountNumber=?");
            customerStatement.setObject(1,model.getLoanNumber());
            customerStatement.setObject(2,model.getAccountNumber());

            if (customerStatement.executeUpdate()>0) {
                if (new SavingsAccountController().updateSavingsAccountForWithdraw(model.getAccountNumber(), model.getLoanAmount())) {
                    if (new OnHoldDetailController().updateOnHoldDetailPlus(model.getAccountNumber(), model.getLoanAmount())) {
                        if (new MoneyJournalController().makeMinusRecord("Main Balance", model.getLoanAmount())) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                            return false;
                        }

                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
        }
        return false;
    }

    public ArrayList<LoanByDeposit> getIssuedLoans() throws SQLException, ClassNotFoundException {

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM LoanByDeposit");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<LoanByDeposit> loanByDepositSet = new ArrayList<>();
        while(resultSet.next()){
            loanByDepositSet.add(new LoanByDeposit(
                    resultSet.getString("dLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("dLoanAmount"),
                    resultSet.getString("dIssuedDate"),
                    resultSet.getDouble("MonthlyInstallment"),
                    resultSet.getInt("dNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("dLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            ));
        }
        return loanByDepositSet;
    }

    public LoanByDeposit getLoanByNumber(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM LoanByDeposit WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return new LoanByDeposit(
                    resultSet.getString("dLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("dLoanAmount"),
                    resultSet.getString("dIssuedDate"),
                    resultSet.getDouble("MonthlyInstallment"),
                    resultSet.getInt("dNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("dLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            );
        }
        return null;
    }

    public boolean updateLoanInfo(LoanByDepositPayModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE LoanByDeposit SET installmentsToBePaid=?," +
                        "dLoanPaidAmount=?,nextInstallmentDate=?  WHERE accountNumber=?");
        statement.setObject(1,model.getNumOfInstallmentsTobePaid());
        statement.setObject(2,model.getLoanPaidAmount());
        statement.setObject(3,model.getNextInstallmentDate());
        statement.setObject(4,model.getAccountNumber());

        if (statement.executeUpdate()>0){
            // * Make the interest income
            // * single installment without interest
            double singleInstallment = model.getLoanAmount()/model.getNumberOfInstallments();
            double interestIncome = model.getInstallmentValue()-singleInstallment;

            if (new MoneyJournalController().makePlusRecord("Main Balance",model.getInstallmentValue())){
                IncomeTransactionModel incomeModel = new IncomeTransactionModel(
                        new NumberGenerator().getIncomeTransactionID(),
                        "loanByDepPay("+model.getLoanNumber()+")",interestIncome
                );

                if (new IncomeController().saveIncomeRecord(incomeModel)){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }

        }

        return false;
    }

    public boolean setLoanStatusOver(String loanNumber, String accountNumber, double loanAmount) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE LoanByDeposit SET loanStatus=? WHERE dLoanNumber=?");
        String loanStatus = "Complete";
        statement.setObject(1,loanStatus);
        statement.setObject(2,loanNumber);

        if(statement.executeUpdate()>0){
            PreparedStatement statement1 = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET loanByDeposit=? WHERE accountNumber=?");
            statement1.setObject(1,"NULL");
            statement1.setObject(2,accountNumber);
            return statement1.executeUpdate()>0;
        }
        return false;
    }

}
