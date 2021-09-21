package controller.dbControllers;

import db.DbConnection;
import model.CustomerModel;
import model.OpenAccDepMoneyModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        if (new DepositMoneyController().recordDepositTransaction(deposit)) {
            connection.commit();
            return true;
        } else {
            connection.rollback();
            return false;
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
}


