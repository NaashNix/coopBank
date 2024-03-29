/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.LastTransacTableModel;
import model.WithdrawObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class WithdrawMoneyController {
    /*
        --> This class is for controlling WithdrawTransactions table in database.
     */

    public boolean updateWithdrawInfo(WithdrawObjectModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO WithdrawTransactions VALUES(?,?,?,?,?,?)");
        statement.setObject(1,model.getWithdrawTransactionID());
        statement.setObject(2,model.getDate());
        statement.setObject(3,model.getTime());
        statement.setObject(4,model.getAccountNumber());
        statement.setObject(5,model.getDescription());
        statement.setObject(6,model.getAmount());

        statement.executeUpdate();

        if (new SavingsAccountController().updateSavingsAccountForWithdraw(model.getAccountNumber(),model.getAmount())){
            connection.commit();
            return true;
        }else {
            connection.rollback();
            return false;
        }
    }

    public ArrayList<LastTransacTableModel> getLastWithdrawalTransactions(String accountNumber) throws SQLException, ClassNotFoundException {
        /*
            --> This method returns the latest withdrawal transactions of the requested account owner.
         */

        ArrayList<LastTransacTableModel> models = new ArrayList<>();
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM WithdrawTransactions WHERE accountNumber=? ORDER BY transactionDate DESC,transactionTime DESC LIMIT 10;");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            models.add(new LastTransacTableModel(
                    resultSet.getString("transactionDate"),
                    "Savings",
                    resultSet.getString("description"),
                    resultSet.getDouble("amount")
            ));
        }
        return models;
    }
}
