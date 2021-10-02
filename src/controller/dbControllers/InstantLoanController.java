/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import controller.components.NumberGenerator;
import db.DbConnection;
import model.IncomeTransactionModel;
import model.InstantLoanModel;
import model.InstantLoanPayModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class InstantLoanController{

    public boolean getArrearsStatus(String iLoanNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT loanStatus FROM InstantLoan WHERE iLoanNumber=?");
        statement.setObject(1,iLoanNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            if (resultSet.getString(1).equals("arrears")){
                return true;
            }else return resultSet.getString(1).equals("Stopped");
        }
        return false;
    }

    public boolean saveLoan(InstantLoanModel model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO InstantLoan VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        statement.setObject(1,model.getiLoanNumber());
        statement.setObject(2,model.getAccountNumber());
        statement.setObject(3,model.getiLoanAmount());
        statement.setObject(4,model.getiIssuedDate());
        statement.setObject(5,model.getiMonthlyInstallment());
        statement.setObject(6,model.getiNumberOfInstallments());
        statement.setObject(7,model.getInstallmentsToBePaid());
        statement.setObject(8,model.getiLoanPaidAmount());
        statement.setObject(9,model.getLoanStatus());
        statement.setObject(10,model.getNextInstallmentDate());
        statement.setObject(11,model.getInterest());

        if (statement.executeUpdate()>0){
            PreparedStatement customerStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET instantLoan=? WHERE accountNumber=?");
            customerStatement.setObject(1,model.getiLoanNumber());
            customerStatement.setObject(2,model.getAccountNumber());

            if (customerStatement.executeUpdate()>0){
                if (new MoneyJournalController().makeMinusRecord("Main Balance",model.getiLoanAmount())){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
        }
        return false;
    }

    public ArrayList<InstantLoanModel> getIssuedLoans() throws SQLException, ClassNotFoundException {
        /*
            --> This method returns the latest withdrawal transactions of the requested account owner.
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM InstantLoan");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<InstantLoanModel> instantLoanModel = new ArrayList<>();
        while(resultSet.next()){
            instantLoanModel.add(new InstantLoanModel(
                    resultSet.getString("iLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("iLoanAmount"),
                    resultSet.getString("iIssuedDate"),
                    resultSet.getDouble("iMonthlyInstallment"),
                    resultSet.getDouble("interest"),
                    resultSet.getInt("iNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("iLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate")
            ));
        }
        return instantLoanModel;
    }

    public InstantLoanModel getLoanByNumber(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM InstantLoan WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return new InstantLoanModel(
                    resultSet.getString("iLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("iLoanAmount"),
                    resultSet.getString("iIssuedDate"),
                    resultSet.getDouble("iMonthlyInstallment"),
                    resultSet.getDouble("interest"),
                    resultSet.getInt("iNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("iLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate")
            );
        }
        return null;
    }

    public boolean updateLoanInfo(InstantLoanPayModel model) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE InstantLoan SET installmentsToBePaid=?," +
                        "iLoanPaidAmount=?,nextInstallmentDate=?  WHERE accountNumber=?");
        statement.setObject(1,model.getNumOfInstallmentsTobePaid());
        statement.setObject(2,model.getiLoanPaidAmount());
        statement.setObject(3,model.getNextInstallmentDate());
        statement.setObject(4,model.getAccountNumber());

        if (statement.executeUpdate()>0){
            // * Make the interest income
            // * single installment without interest
            double singleInstallment = model.getLoanAmount()/model.getiNumberOfInstallments();
            double interestIncome = model.getInstallmentValue()-singleInstallment;


            if (new MoneyJournalController().makePlusRecord("Main Balance",model.getInstallmentValue())){
                IncomeTransactionModel incomeModel = new IncomeTransactionModel(
                        new NumberGenerator().getIncomeTransactionID(),
                        "instantLoanPay("+model.getiLoanNumber()+")",interestIncome
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
                .prepareStatement("UPDATE InstantLoan SET loanStatus=? WHERE iLoanNumber=?");
        statement.setObject(1,"Completed");
        statement.setObject(2,loanNumber);

        if(statement.executeUpdate()>0){
            PreparedStatement statement1 = DbConnection.getInstance().getConnection()
                    .prepareStatement("UPDATE Customer SET instantLoan=? WHERE accountNumber=?");
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
                .prepareStatement("UPDATE InstantLoan SET loanStatus=? WHERE dLoanNumber=?");
        statement.setObject(1,loanStatus);
        statement.setObject(2,loanNumber);
        return statement.executeUpdate()>0;
    }

    public ArrayList<InstantLoanModel> getArreasedLoan() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM InstantLoan WHERE nextInstallmentDate <= ? AND loanStatus=?");
        LocalDate today = LocalDate.from(LocalDateTime.now());

        statement.setObject(1,"2021-11-04");
        //statement.setObject(1,today);
        statement.setObject(2,"Active");
        ArrayList<InstantLoanModel> models = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            models.add(new InstantLoanModel(
                    resultSet.getString("iLoanNumber"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("iLoanAmount"),
                    resultSet.getString("iIssuedDate"),
                    resultSet.getDouble("iMonthlyInstallment"),
                    resultSet.getDouble("interest"),
                    resultSet.getInt("iNumberOfInstallments"),
                    resultSet.getInt("installmentsToBePaid"),
                    resultSet.getDouble("iLoanPaidAmount"),
                    resultSet.getString("loanStatus"),
                    resultSet.getDate("nextInstallmentDate")
            ));
        }

        return models;
    }
}
