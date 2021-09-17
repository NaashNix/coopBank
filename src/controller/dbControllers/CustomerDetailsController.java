package controller.dbControllers;

import db.DbConnection;
import model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDetailsController {
    /*
        --> This class is for getting/updating/saving the 'Customer' Db.table
     */

    public boolean savingDetails(CustomerModel customer) throws SQLException, ClassNotFoundException {

        /*
            --> This method will save the customer model details.
         */

        // * Creating the connection and query.
        PreparedStatement statement = DbConnection.getInstance().getConnection()
        .prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.setObject(1,customer.getAccountNumber());
        statement.setObject(2,customer.getName());
        statement.setObject(3,customer.getAge());
        statement.setObject(4,customer.getSex());
        statement.setObject(5,customer.getAccountType());
        statement.setObject(6,customer.getCustomerAddress());
        statement.setObject(7,customer.getTelephoneNumber());
        statement.setObject(8,customer.getCustomerBirthday());
        statement.setObject(9,customer.getJoinedDate());
        statement.setObject(10,customer.getCustomerNIC());
        statement.setObject(11,customer.getCustomerEmail());
        statement.setObject(12,customer.getRationLoan());
        statement.setObject(13,customer.getLoanByDeposit());
        statement.setObject(14,customer.getInstantLoan());

        // * Executing the query.
        if (statement.executeUpdate()>0){
            return true;
        }else{
            System.out.println("Failed! [@savingCustomerDetailsSQL]");
            return false;
        }
    }


    public boolean setAccountBalance(String accountNumber,double amount) throws SQLException, ClassNotFoundException {
        /*
            --> This method will set the personal's savings account balance.
         */

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.
                prepareStatement("")

    }
}
