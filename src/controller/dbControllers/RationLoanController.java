/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;


import db.DbConnection;
import model.InstantLoanModel;
import model.RationLoanModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RationLoanController {
    public boolean getArrearsStatus(String rLoanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT loanStatus FROM RationLoan WHERE rLoanNumber=?");
        statement.setObject(1,rLoanNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            if (resultSet.getString(1).equals("arrears")){
                return true;
            }else return resultSet.getString(1).equals("Stopped");
        }
        return false;
    }

    public boolean saveLoan(RationLoanModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO RationLoan VALUES(?,?,?,?,?,?,?,?,?,?)");
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
                    .prepareStatement("UPDATE Customer SET rationLoan=? WHERE accountNumber=?");
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

}
