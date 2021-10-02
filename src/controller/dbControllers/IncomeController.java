/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;
import model.IncomeTransactionModel;
import model.IncomeViewerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IncomeController {
    public boolean saveIncomeRecord(IncomeTransactionModel model) throws SQLException, ClassNotFoundException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("INSERT INTO Incomes VALUES(?,?,?,?,?)");
        statement.setObject(1,model.getTransactionID());
        statement.setObject(2,model.getDescription());
        statement.setObject(3,model.getAmount());
        statement.setObject(4,date);
        statement.setObject(5,time);

        return statement.executeUpdate()>0;
    }

    public boolean saveIncomeRecordWithMoneyJournalUpdate(IncomeTransactionModel model) throws SQLException, ClassNotFoundException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Incomes VALUES(?,?,?,?,?)");

        statement.setObject(1,model.getTransactionID());
        statement.setObject(2,model.getDescription());
        statement.setObject(3,model.getAmount());
        statement.setObject(4,date);
        statement.setObject(5,time);

        if (statement.executeUpdate()>0){
            if (new MoneyJournalController().makePlusRecord("Main Balance",model.getAmount())){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }else{
            return false;
        }
    }

    public ArrayList<IncomeViewerModel> getAllTheTimeIncomes() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM Incomes");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<IncomeViewerModel> models = new ArrayList<>();
        while(resultSet.next()){
            models.add(new IncomeViewerModel(
                    resultSet.getString("transactionID"),
                    resultSet.getString("description"),
                    resultSet.getDate("transactionDate"),
                    resultSet.getString("transactionTime"),
                    resultSet.getDouble("amount")
            ));
        }

        return models;
    }

    public double calculateAllTheTimeIncomes() throws SQLException, ClassNotFoundException {
        ArrayList<IncomeViewerModel> models = getAllTheTimeIncomes();
        double totalInAllTheTime = 0.0;
        for (IncomeViewerModel temp : models
             ) {
            totalInAllTheTime = totalInAllTheTime+temp.getAmount();
        }

        return totalInAllTheTime;
    }

    public ArrayList<IncomeViewerModel> getLastMonthIncomes() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Incomes WHERE transactionDate BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE()");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<IncomeViewerModel> models = new ArrayList<>();
        while(resultSet.next()){
            models.add(new IncomeViewerModel(
                    resultSet.getString("transactionID"),
                    resultSet.getString("description"),
                    resultSet.getDate("transactionDate"),
                    resultSet.getString("transactionTime"),
                    resultSet.getDouble("amount")
            ));
        }

        return models;
    }

    public double calculateLastMonthIncomes() throws SQLException, ClassNotFoundException {
        ArrayList<IncomeViewerModel> models = getLastMonthIncomes();
        double totalLastMonth = 0.0;
        for (IncomeViewerModel temp : models
        ) {
            totalLastMonth = totalLastMonth+temp.getAmount();
        }

        return totalLastMonth;
    }

}
