/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;


import controller.components.NumberGenerator;
import db.DbConnection;
import model.IncomeTransactionModel;
import model.InstantLoanModel;
import model.RationLoanModel;
import model.RationLoanPayModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RationLoanController {
    public boolean getArrearsStatus(String rLoanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT loanStatus FROM RationLoan WHERE rLoanNumber=?");
        statement.setObject(1,rLoanNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            if (resultSet.getString(1).equals("arrears")){
                return true;
            }else return resultSet.getString(1).equals("Stopped");
        }
        return false;
    }

    public RationLoanModel getRationLoanModelByID(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM RationLoan WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new RationLoanModel(
                    resultSet.getString("rLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("rLoanAmount"),
                    resultSet.getString("rIssuedDate"),
                    resultSet.getDouble("rMonthlyInstallment"),
                    resultSet.getInt("rNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("rLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            );
        }
    return null;
    }


    public boolean saveLoan(RationLoanModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO RationLoan VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        connection.setAutoCommit(false);
        statement.setObject(1,model.getLoanNumber());
        statement.setObject(2,model.getAccountNumber());
        statement.setObject(3,model.getLoanAmount());
        statement.setObject(4,model.getIssuedDate());
        statement.setObject(5,model.getMonthlyInstallment());
        statement.setObject(6,model.getNumberOfInstallments());
        statement.setObject(7,model.getInstallmentsToBePaid());
        statement.setObject(8,model.getLoanPaidAmount());
        statement.setObject(9,model.getLoanStatus());
        statement.setObject(10,model.getNextInstallmentDate());
        statement.setObject(11,model.getInterest());

        if (statement.executeUpdate()>0){
            PreparedStatement customerStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET rationLoan=? WHERE accountNumber=?");
            customerStatement.setObject(1,model.getLoanNumber());
            customerStatement.setObject(2,model.getAccountNumber());
            if (customerStatement.executeUpdate()>0){
                if (new MoneyJournalController().makeMinusRecord("Main Balance", model.getLoanAmount())){
                    connection.commit();
                 return true;
                }
                return false;
            }else{
                connection.rollback();
                return false;
            }
        }
        return false;
    }

    public ArrayList<RationLoanModel> getIssuedLoans() throws SQLException, ClassNotFoundException {
        ArrayList<RationLoanModel> rationLoanSet = new ArrayList<>();
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM RationLoan");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            rationLoanSet.add(new RationLoanModel(
                    resultSet.getString("rLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("rLoanAmount"),
                    resultSet.getString("rIssuedDate"),
                    resultSet.getDouble("rMonthlyInstallment"),
                    resultSet.getInt("rNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("rLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            ));
        }
        return rationLoanSet;
    }

    public boolean updateLoanInfo(RationLoanPayModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE RationLoan SET installmentsToBePaid=?," +
                        "rLoanPaidAmount=?,nextInstallmentDate=?  WHERE accountNumber=?");
        statement.setObject(1,model.getNumOfInstallmentsTobePaid());
        statement.setObject(2,model.getLoanPaidAmount());
        statement.setObject(3,model.getNextInstallmentDate());
        statement.setObject(4,model.getAccountNumber());

        if (statement.executeUpdate()>0){
            // * Make the interest income
            // * single installment without interest
            double singleInstallment = model.getLoanAmount()/model.getNumberOfInstallments();
            double interestIncome = model.getInstallmentValue()-singleInstallment;

            if (new MoneyJournalController().makePlusRecord("Main Balance",model.getInstallmentValue())){
                IncomeTransactionModel incomeModel = new IncomeTransactionModel(
                        new NumberGenerator().getIncomeTransactionID(),
                        "rationLPay("+model.getLoanNumber()+")",interestIncome
                );

                if (new IncomeController().saveIncomeRecord(incomeModel)){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }

        }
        return false;
    }

    public boolean setLoanStatusOver(String accountNumber, String loanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE Ration Loan SET loanStatus=? WHERE rLoanNumber=?");
        statement.setObject(1,"Completed");
        statement.setObject(2,loanNumber);

        if(statement.executeUpdate()>0){
            PreparedStatement statement1 = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET rationLoan=? WHERE accountNumber=?");
            statement1.setObject(1,"NULL");
            statement1.setObject(2,accountNumber);
            if (statement1.executeUpdate()>0){
                return setLoanStatus(loanNumber,"Complete");
            }
        }
        return false;
    }

    public boolean setLoanStatus(String loanNumber, String loanStatus) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE RationLoan SET loanStatus=? WHERE dLoanNumber=?");
        statement.setObject(1,loanStatus);
        statement.setObject(2,loanNumber);
        return statement.executeUpdate()>0;
    }


    public ArrayList<RationLoanModel> getArreasedLoan() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM RationLoan WHERE nextInstallmentDate <= ? AND loanStatus=?");
        LocalDate today = LocalDate.from(LocalDateTime.now());

        //statement.setObject(1,"2021-11-04");
        statement.setObject(1,today);
        statement.setObject(2,"Active");
        ArrayList<RationLoanModel> models = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            models.add(new RationLoanModel(
                    resultSet.getString("rLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("rLoanAmount"),
                    resultSet.getString("rIssuedDate"),
                    resultSet.getDouble("rMonthlyInstallment"),
                    resultSet.getInt("rNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("rLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate"),
                    resultSet.getDouble("interest")
            ));
        }

        return models;
    }


}
