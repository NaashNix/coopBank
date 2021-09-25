/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.InstantLoanModel;
import model.LoanByDeposit;

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
        PreparedStatement statement = connection.prepareStatement("INSERT INTO LoanByDeposit VALUES(?,?,?,?,?,?,?,?,?,?)");
        connection.setAutoCommit(false);
        statement.setObject(1,model.getLoanNumber());
        statement.setObject(2,model.getAccountNumber());
        statement.setObject(3,model.getLoanAmount());
        statement.setObject(4,model.getIssuedDate());
        statement.setObject(5,model.getMonthlyInstallment());
        statement.setObject(6,model.getNumberOfInstallments());
        statement.setObject(7,model.getLoanPaidAmount());
        statement.setObject(8,model.getLoanStatus());
        statement.setObject(9,model.getNextInstallmentDate());
        statement.setObject(10,model.getInterest());

        if (statement.executeUpdate()>0){
            PreparedStatement customerStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET loanByDeposit=? WHERE accountNumber=?");
            customerStatement.setObject(1,model.getLoanNumber());
            customerStatement.setObject(2,model.getAccountNumber());

            if (customerStatement.executeUpdate()>0){
                connection.commit();
                return true;
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
                    resultSet.getDouble("dLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            ));
        }
        return loanByDepositSet;
    }
}
