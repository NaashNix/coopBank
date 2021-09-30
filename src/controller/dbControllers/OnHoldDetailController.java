/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnHoldDetailController {

    public boolean updateOnHoldDetailPlus(String accountNumber, double amount) throws SQLException, ClassNotFoundException {
        if (getExistence(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE OnHoldDetails SET holdedAmount=(holdedAmount+"+amount+") WHERE accountNumber=?");
            statement.setObject(1,accountNumber);
            return statement.executeUpdate()>0;
        }else{
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("INSERT INTO OnHoldDetails VALUES(?,?)");
            statement.setObject(1,accountNumber);
            statement.setObject(2,amount);
            return statement.executeUpdate()>0;
        }
    }


    public boolean getExistence(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT EXISTS(SELECT*FROM OnHoldDetails WHERE accountNumber=?)");
        statement.setObject(1,accountNumber);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }else{
            return false;
        }

    }

    public boolean updateOnHoldDetailMinus(String accountNumber, double amount) throws SQLException, ClassNotFoundException {
        if (getExistence(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE OnHoldDetails SET holdedAmount=(holdedAmount-"+amount+") WHERE accountNumber=?");
            statement.setObject(1,accountNumber);
            return statement.executeUpdate()>0;
        }
        return false;
    }


    public double getTheHoldedAmount(String accountNumber) throws SQLException, ClassNotFoundException {
        if (getExistence(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("SELECT holdedAmount FROM OnHoldDetails WHERE accountNumber=?");
            statement.setObject(1,accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getDouble(1);
            }

        }
        return 0.0;
    }

}

