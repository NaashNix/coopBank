/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.DepositObjectModel;
import model.WithdrawObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        if (new SavingsAccountController().setAccountBalance(model.getAccountNumber(),model.getAmount())){
            connection.commit();
            return true;
        }else
        {
            connection.rollback();
            return false;
        }

    }
}
