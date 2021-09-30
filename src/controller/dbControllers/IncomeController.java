/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.IncomeTransactionModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IncomeController {
    public boolean saveIncomeRecord(IncomeTransactionModel model) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("INSERT INTO Incomes VALUES(?,?,?)");
        statement.setObject(1,model.getTransactionID());
        statement.setObject(2,model.getDescription());
        statement.setObject(3,model.getAmount());

        return statement.executeUpdate()>0;
    }
}
