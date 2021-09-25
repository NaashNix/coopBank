/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsAccountController {

    public boolean updateSavingsForDepositTransaction(String accountNumber, double amount) throws SQLException, ClassNotFoundException {
        /*
            --> This method will set the personal's savings account balance.
         */

        if (checkRecordIsExist(accountNumber)){
            // * If the savings account has already a record in the savings accoun table.
         Connection connection = DbConnection.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement
                 ("UPDATE SavingsAccount SET personalBalance=(personalBalance+"+amount+") WHERE accountNumber=?");
         statement.setObject(1,accountNumber);
         return statement.executeUpdate()>0;

        }else {
            // * If the savings account haven't a record in the savings accoun table.
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO SavingsAccount VALUES(?,?)");
            statement.setObject(1, accountNumber);
            statement.setObject(2, amount);

            return statement.executeUpdate() > 0;
        }

    }

    public boolean updateSavingsAccountForWithdraw(String accountNumber, double amount) throws SQLException, ClassNotFoundException {
        if (checkRecordIsExist(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE SavingsAccount SET personalBalance=(personalBalance-"+amount+") WHERE accountNumber=?");
            statement.setObject(1,accountNumber);
            return statement.executeUpdate()>0;
        }
        return false;
    }

    public double getAccountBalance(String accountNumber) throws SQLException, ClassNotFoundException {
        if (checkRecordIsExist(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("SELECT personalBalance FROM SavingsAccount WHERE accountNumber=?");
            statement.setObject(1,accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getDouble(1);
            }
        }
        return 0.0;
    }

    public boolean checkRecordIsExist(String accountNumber) throws SQLException, ClassNotFoundException {
        /*
            --> This method will returns the true or false.
                This will check that the requested account number is already in the deposiTransaction table and
                if it is there then it will return the true else it will returns false.
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT EXISTS(SELECT*FROM SavingsAccount WHERE accountNumber=?)");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }else{
            return false;
        }
    }

}
