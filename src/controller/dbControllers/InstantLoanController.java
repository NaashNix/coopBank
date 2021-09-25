/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.InstantLoanModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InstantLoanController{

    public boolean getArrearsStatus(String iLoanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT loanStatus FROM InstantLoan WHERE iLoanNumber=?");
        statement.setObject(1,iLoanNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            if (resultSet.getString(1).equals("arrears")){
                return true;
            }else return resultSet.getString(1).equals("Stopped");
        }
        return false;
    }

    public boolean saveLoan(InstantLoanModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO InstantLoan VALUES(?,?,?,?,?,?,?,?,?,?)");
        statement.setObject(1,model.getiLoanNumber());
        statement.setObject(2,model.getAccountNumber());
        statement.setObject(3,model.getiLoanAmount());
        statement.setObject(4,model.getiIssuedDate());
        statement.setObject(5,model.getiMonthlyInstallment());
        statement.setObject(6,model.getiNumberOfInstallments());
        statement.setObject(7,model.getiLoanPaidAmount());
        statement.setObject(8,model.getLoanStatus());
        statement.setObject(9,model.getNextInstallmentDate());
        statement.setObject(10,model.getInterest());

        if (statement.executeUpdate()>0){
            PreparedStatement customerStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET instantLoan=? WHERE accountNumber=?");
            customerStatement.setObject(1,model.getiLoanNumber());
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

    public ArrayList<InstantLoanModel> getIssuedLoans() throws SQLException, ClassNotFoundException {
        /*
            --> This method returns the latest withdrawal transactions of the requested account owner.
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM InstantLoan");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<InstantLoanModel> instantLoanModel = new ArrayList<>();
        while(resultSet.next()){
            instantLoanModel.add(new InstantLoanModel(
                    resultSet.getString("iLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("iLoanAmount"),
                    resultSet.getString("iIssuedDate"),
                    resultSet.getDouble("iMonthlyInstallment"),
                    resultSet.getDouble("interest"),
                    resultSet.getInt("iNumberOfInstallments"),
                    resultSet.getDouble("iLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate")
            ));
        }
        return instantLoanModel;
    }

}
