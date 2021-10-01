/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.dbControllers;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDetailsController {

    public boolean checkUserNameExists(String userName) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT EXISTS(SELECT*FROM UserDetails WHERE userName=?)");
        statement.setObject(1,userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }else{
            return false;
        }
    }

    public String getPassword(String userName) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT password FROM UserDetails WHERE userName=?");
        statement.setObject(1,userName);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);
        }else{
            return null;
        }
    }

}
