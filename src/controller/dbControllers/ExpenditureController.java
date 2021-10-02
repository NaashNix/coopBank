/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.BankExpenditureModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenditureController {

    public boolean saveExpenditure(BankExpenditureModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Expenditures VALUES(?,?,?,?,?)");
        connection.setAutoCommit(false);
        statement.setObject(1,model.getReceiptNumber());
        statement.setObject(2,model.getDescription());
        statement.setObject(3,model.getTime());
        statement.setObject(4,model.getDate());
        statement.setObject(5,model.getAmount());

        if (statement.executeUpdate()>0){
            if (new MoneyJournalController().makeMinusRecord("Main Balance", model.getAmount())) {
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }
        return false;

    }

    public BankExpenditureModel getExpenditure(String receiptNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM Expenditures WHERE receiptNumber=?");
        statement.setObject(1,receiptNumber);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return new BankExpenditureModel(
                    resultSet.getString("receiptNumber"),
                    resultSet.getString("description"),
                    resultSet.getDate("transactionDate"),
                    resultSet.getString("transactionTime"),
                    resultSet.getDouble("amount")
            );
        }

        return null;
    }


    public ArrayList<BankExpenditureModel> getAllExpenditures() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Expenditures");

        // * Making arraylist
        ArrayList<BankExpenditureModel> bankExpenditureModels = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            bankExpenditureModels.add(new BankExpenditureModel(
                    resultSet.getString("receiptNumber"),
                    resultSet.getString("description"),
                    resultSet.getDate("transactionDate"),
                    resultSet.getString("transactionTime"),
                    resultSet.getDouble("amount")
            ));
        }

        return bankExpenditureModels;
    }

    public ArrayList<BankExpenditureModel> getLastMonthExpenses() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Expenditures WHERE transactionDate BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE()");

        ResultSet resultSet = statement.executeQuery();
        ArrayList<BankExpenditureModel> bankExpenditureModels = new ArrayList<>();
        while (resultSet.next()){
            bankExpenditureModels.add(new BankExpenditureModel(
                    resultSet.getString("receiptNumber"),
                    resultSet.getString("description"),
                    resultSet.getDate("transactionDate"),
                    resultSet.getString("transactionTime"),
                    resultSet.getDouble("amount")
            ));
        }

        return bankExpenditureModels;
    }

    public double calculateTotalExpensesInMonth() throws SQLException, ClassNotFoundException {
        ArrayList<BankExpenditureModel> expModels = getLastMonthExpenses();
        double totalExpenditures = 0.0;
        if (expModels!=null){
            for (BankExpenditureModel model : expModels
                 ) {
                totalExpenditures=totalExpenditures+model.getAmount();
            }
        }

        return totalExpenditures;
    }

    public double calculateTotalExpensesInAllTheTime(){
        ArrayList<BankExpenditureModel> expModels = null;
        try {
            expModels = getAllExpenditures();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        double totalExpenditures = 0.0;
        if (expModels!=null){
            for (BankExpenditureModel model : expModels
            ) {
                totalExpenditures=totalExpenditures+model.getAmount();
            }
        }

        return totalExpenditures;
    }

}
