/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.LoanDetailsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanDetailsController {

    /*
        --> This is to control the loanDetails Table in the database.
     */

    public ArrayList<LoanDetailsModel> getLoanDetails() throws SQLException, ClassNotFoundException {
        ArrayList<LoanDetailsModel> models = new ArrayList<>();
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM LoanDetails");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            models.add(new LoanDetailsModel(
                    resultSet.getString("loanName"),
                    resultSet.getString("loanCode"),
                    resultSet.getString("forWhom"),
                    resultSet.getDouble("minimumAccountBalance"),
                    resultSet.getInt("minimumAccountMaturity"),
                    resultSet.getDouble("maximumLoanAmount"),
                    resultSet.getString("interestType"),
                    resultSet.getDouble("interest"),
                    resultSet.getString("interestCalPeriod"),
                    resultSet.getInt("maximumNoOfInstallments"),
                    resultSet.getString("notPaidOption1"),
                    resultSet.getString("notPaidOption2")
            ));
        }
        return models;
    }

    public LoanDetailsModel getOneLoanDetail(String loanCode) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM LoanDetails WHERE loanCode=?");
        statement.setObject(1,loanCode);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new LoanDetailsModel(
                    resultSet.getString("loanName"),
                    resultSet.getString("loanCode"),
                    resultSet.getString("forWhom"),
                    resultSet.getDouble("minimumAccountBalance"),
                    resultSet.getInt("minimumAccountMaturity"),
                    resultSet.getDouble("maximumLoanAmount"),
                    resultSet.getString("interestType"),
                    resultSet.getDouble("interest"),
                    resultSet.getString("interestCalPeriod"),
                    resultSet.getInt("maximumNoOfInstallments"),
                    resultSet.getString("notPaidOption1"),
                    resultSet.getString("notPaidOption2")
            );
        }
        return null;
    }

    public double getMinimumAccBalance(String loanCode) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT minimumAccountBalance FROM LoanDetails WHERE LoanCode=?");
        statement.setObject(1,loanCode);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public LoanDetailsModel getLoanModelByType(String loanType) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM LoanDetails WHERE loanName=?");
        statement.setObject(1,loanType);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return new LoanDetailsModel(
                    resultSet.getString("loanName"),
                    resultSet.getString("loanCode"),
                    resultSet.getString("forWhom"),
                    resultSet.getDouble("minimumAccountBalance"),
                    resultSet.getInt("minimumAccountMaturity"),
                    resultSet.getDouble("maximumLoanAmount"),
                    resultSet.getString("interestType"),
                    resultSet.getDouble("interest"),
                    resultSet.getString("interestCalPeriod"),
                    resultSet.getInt("maximumNoOfInstallments"),
                    resultSet.getString("notPaidOption1"),
                    resultSet.getString("notPaidOption2")
            );
        }
        return null;
    }
}
