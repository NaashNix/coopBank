package controller.dbControllers;


import db.DbConnection;
import model.UserDetailsModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDetailsController {

    public ArrayList<String> getUserDetails() throws SQLException, ClassNotFoundException {
        /*
            --> This method return ALL the 'userNames' to the * loginFormController
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM UserDetails");

        // * Creating String list for the return.
        ArrayList<String> userNameList = new ArrayList<>();

        // * Executing the query.
        ResultSet resultSet = statement.executeQuery();

        // * Adding all user names to list.
        while (resultSet.next()){
            userNameList.add(resultSet.getString("userName"));
        }

        return userNameList;
    }

    public String getPassword(String userName) throws SQLException, ClassNotFoundException {
        /*
            --> This will returns the password for user entered 'User Name'
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
            .prepareStatement("SELECT*FROM UserDetails WHERE userName=?");
        statement.setObject(1,userName);

        // * Executing and returning
        ResultSet resultSet = statement.executeQuery();

        return resultSet.getString("password");

    }


    public ArrayList<UserDetailsModel> getAllUsersDetails() throws SQLException, ClassNotFoundException {
        /*
            --> This will return ALL the UserDetails records from the
                'UserDetails' table.
            --> Called at the 'initialize()' in the * UserDetailsFormController
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT*FROM UserDetails");

        ResultSet resultSet = statement.executeQuery();

        // * Create a List for returns.
        ArrayList<UserDetailsModel> userDetailsModels = new ArrayList<>();

        while (resultSet.next()){

            // * Creating userDetailsModel from the result set and added to ArrayList.
            userDetailsModels.add(
                    new UserDetailsModel(
                        resultSet.getString("name"),
                        resultSet.getString("telephone"),
                        resultSet.getString("bankPosition"),
                        resultSet.getString("userName"),
                        resultSet.getString("password")
                    ));
        }

        return userDetailsModels;
    }


    public boolean updateUserDetails(String userName,UserDetailsModel model) throws SQLException, ClassNotFoundException {
        /*
            --> This method will update the user detail record according to received user name.
            --> If this updated successfully then it is returns true. else false.
         */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE UserDetails SET name=? , telephone=? , bankPosition=? ," +
                        "userName=?, password=? WHERE userName=?" );
        statement.setObject(1,model.getName());
        statement.setObject(2,model.getTelephone());
        statement.setObject(3,model.getBankPosition());
        statement.setObject(4,model.getUserName());
        statement.setObject(5,model.getPassword());
        statement.setObject(6,userName);

        return statement.executeUpdate()>0;
    }

}
