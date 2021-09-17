/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.OpenAccDepMoneyModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositMoneyController {
     /*
        --> This class is for saving deposit transactions to the db.
     */

    public boolean recordDepositTransaction(OpenAccDepMoneyModel deposit) throws SQLException, ClassNotFoundException {
        /*
            --> This method record the deposit transaction.
            --> Always use autoCommit here.
            --> As well as if the updation is done then user balance must be updated.
         */

        Connection connection = (Connection) DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO DepositTransactions VALUES(?,?,?,?,?,?)");
            statement.setObject(1,deposit.getTransactionID());
            statement.setObject(2,deposit.getDate());
            statement.setObject(3,deposit.getTime());
            statement.setObject(4,deposit.getAccountNumber());
            statement.setObject(5,deposit.getDescription());
            statement.setObject(6,deposit.getAmount());

            if (statement.executeUpdate()>0){

            }

    }

}
