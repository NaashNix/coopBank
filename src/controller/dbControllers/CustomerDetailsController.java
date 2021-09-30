package controller.dbControllers;

import db.DbConnection;
import javafx.util.Pair;
import model.CustomerDetailModelForView;
import model.CustomerModel;
import model.CustomerModelMini;
import model.OpenAccDepMoneyModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDetailsController {
    /*
        --> This class is for getting/updating/saving the 'Customer' Db.table
     */

    public boolean savingDetails(CustomerModel customer, OpenAccDepMoneyModel deposit) throws SQLException, ClassNotFoundException {

        /*
            --> This method will save the customer model details.
         */

        // * Creating the connection and query.
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        connection.setAutoCommit(false);

        statement.setObject(1, customer.getAccountNumber());
        statement.setObject(2, customer.getName());
        statement.setObject(3, customer.getAge());
        statement.setObject(4, customer.getSex());
        statement.setObject(5, customer.getAccountType());
        statement.setObject(6, customer.getCustomerAddress());
        statement.setObject(7, customer.getTelephoneNumber());
        statement.setObject(8, customer.getCustomerBirthday());
        statement.setObject(9, customer.getJoinedDate());
        statement.setObject(10, customer.getCustomerNIC());
        statement.setObject(11, customer.getCustomerEmail());
        statement.setObject(12, customer.getRationLoan());
        statement.setObject(13, customer.getLoanByDeposit());
        statement.setObject(14, customer.getInstantLoan());

        // * Executing the query.
        statement.executeUpdate();
        if (deposit!=null){
            if (new DepositMoneyController().recordDepositTransaction(deposit)) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }else{
            connection.commit();
            return true;
        }
    }

    public boolean checkAccountNumberIsExist(String accountNumber) throws SQLException, ClassNotFoundException {

        /*
            --> If the requested account number is in the database this returns true.
                else it returns the false.
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT EXISTS(SELECT*FROM Customer WHERE accountNumber=?)");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }else{
            return false;
        }

    }

    public CustomerModel getCustomerDetails(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM Customer WHERE accountNumber=?");
        statement.setObject(1,accountNumber);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
        return new CustomerModel(
                resultSet.getString("accountNumber"),
                resultSet.getString("customerName"),
                resultSet.getInt("customerAge"),
                resultSet.getString("sex"),
                resultSet.getString("accountType"),
                resultSet.getString("customerAddress"),
                resultSet.getString("telephoneNumber"),
                resultSet.getString("customerBirthday"),
                resultSet.getString("joinedDate"),
                resultSet.getString("customerNIC"),
                resultSet.getString("customerEmail"),
                resultSet.getString("rationLoan"),
                resultSet.getString("loanByDeposit"),
                resultSet.getString("instantLoan")
            );
        }else{
            return null;
        }

    }

    public CustomerModelMini getMiniCustomerModel(String accountNumber) throws SQLException, ClassNotFoundException {
        if (checkAccountNumberIsExist(accountNumber)){
            PreparedStatement statement = DbConnection.getInstance().getConnection()
                    .prepareStatement("SELECT*FROM Customer WHERE accountNumber=?");
            statement.setObject(1,accountNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new CustomerModelMini(
                        resultSet.getString("customerName"),
                        resultSet.getString("accountNumber"),
                        resultSet.getString("telephoneNumber"),
                        resultSet.getString("rationLoan"),
                        resultSet.getString("loanByDeposit"),
                        resultSet.getString("instantLoan")
                );
            }
        }else{
            return null;
        }
        return null;
    }

    public boolean updateDetails(CustomerModel model) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE Customer SET customerName=?, customerAge=?," +
                        "customerAddress=?,telephoneNumber=?,customerBirthday=?," +
                        "customerNIC=? WHERE accountNumber=?");
        statement.setObject(1,model.getName());
        statement.setObject(2,model.getAge());
        statement.setObject(3,model.getCustomerAddress());
        statement.setObject(4,model.getTelephoneNumber());
        statement.setObject(5,model.getCustomerBirthday());
        statement.setObject(6,model.getCustomerNIC());
        statement.setObject(7,model.getAccountNumber());

        return statement.executeUpdate()>0;
    }

    public String getAccountHolderName(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT customerName FROM Customer WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }else{
            return null;
        }
    }

    public LocalDate getJoinedDate(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT joinedDate FROM Customer WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            LocalDate date = resultSet.getDate(1).toLocalDate();
            return date;
        }
        return null;
    }

    public double getAccountBalance(String accountNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT personalBalance FROM SavingsAccount WHERE accountNumber=?");
        statement.setObject(1,accountNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0.0;
    }

    public ArrayList<CustomerDetailModelForView> getCustomersForView() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM Customer");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Pair<CustomerModelMini,String>> customerAndJoinedDate = new ArrayList<>();
        ArrayList<CustomerDetailModelForView> realCustomerSet = new ArrayList<>();
        while (resultSet.next()){
            customerAndJoinedDate.add(
                    new Pair<>(new CustomerModelMini(
                            resultSet.getString("customerName"),
                            resultSet.getString("accountNumber"),
                            resultSet.getString("telephoneNumber"),
                            resultSet.getString("rationLoan"),
                            resultSet.getString("loanByDeposit"),
                            resultSet.getString("instantLoan")
                    ),resultSet.getString("joinedDate"))
            );
        }
        for (Pair<CustomerModelMini, String> model :customerAndJoinedDate
             ) {
            // * here goes the code line to get the savings account balance.
            PreparedStatement statement1 = DbConnection.getInstance().getConnection()
                    .prepareStatement("SELECT personalBalance FROM SavingsAccount WHERE accountNumber=?");
            statement1.setObject(1,model.getKey().getAccountNumber());
            ResultSet resultSet1 = statement1.executeQuery();
            double accountBalance = 0;
            double holdBalance = 0;
            if (resultSet1.next()){
                accountBalance = resultSet1.getDouble(1);
            }

            // * here goes the code line to get the holed account balance.
            holdBalance = new OnHoldDetailController().getTheHoldedAmount(model.getKey().getAccountNumber());

            // * Here goes the code line that create the view customer model.
            realCustomerSet.add(new CustomerDetailModelForView(
                    model.getKey().getName(),
                    model.getKey().getAccountNumber(),
                    accountBalance,
                    holdBalance,
                    model.getKey().getInstantLoan(),
                    model.getKey().getLoanByDeposit(),
                    model.getKey().getRationLoan(),
                    model.getValue()
            ));
        }

        return realCustomerSet;
    }

}


