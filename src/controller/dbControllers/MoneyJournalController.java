/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MoneyJournalController {
    /*
        --> This controller is to handle the money journal table in the database.
     */

    public boolean makePlusRecord(String balanceType,double amount) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE MoneyJournal SET " +
                        "transactionDate=?,transactionTime=?," +
                        "balance=(balance+"+amount+") WHERE balanceType=?");
        Date date = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Time time = Time.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));

            statement.setObject(1, date);
            statement.setObject(2, time);
            statement.setObject(3, balanceType);

            if (statement.executeUpdate()>0){
                return true;
            }else{
                return false;
            }
    }

    public double getBalances(String balanceType) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT balance FROM MoneyJournal WHERE balanceType=?");
        statement.setObject(1,balanceType);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }else{
            return 0.0;
        }
    }

    public boolean makeMinusRecord(String balanceType,double amount) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE MoneyJournal SET " +
                        "transactionDate=?,transactionTime=?," +
                        "balance=(balance-"+amount+") WHERE balanceType=?");
        Date date = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Time time = Time.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        statement.setObject(1, date);
        statement.setObject(2, time);
        statement.setObject(3, balanceType);

        if (statement.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }


    public boolean setMainBalance (String balanceType, double amount) throws SQLException, ClassNotFoundException {
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("INSERT INTO MoneyJournal VALUES(?,?,?,?)");
            Date date = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Time time = Time.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));

            statement.setObject(1, balanceType);
            statement.setObject(2, date);
            statement.setObject(3, time);
            statement.setObject(3, amount);
        return statement.executeUpdate() > 0;
    }
}
