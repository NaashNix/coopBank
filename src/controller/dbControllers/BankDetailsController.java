package controller.dbControllers;

import db.DbConnection;
import model.BankDetailsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDetailsController {

    public BankDetailsModel getBankDetails() throws SQLException, ClassNotFoundException {

        /* --> Getting the bank details for initialize method in bank details form.
           --> Details are transmitted to bank details controller as #BankDetailsModel#
           --> Return only one model, as BankDetails table should contain only one record.
        */

        PreparedStatement statement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM BankDetails");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new BankDetailsModel(
                    resultSet.getString("bankName"),
                    resultSet.getString("branchName"),
                    resultSet.getString("branch"),
                    resultSet.getString("bankRegNumber"),
                    resultSet.getString("systemDepDate")
            );

        }else{
            return null;
        }
    }

    public boolean bankDetailsSave(BankDetailsModel bankDetailsModel) throws SQLException, ClassNotFoundException {

        /* --> Saving the bank details which are getting from edited bank details form.
           --> this return 'true' if previous record is deleted and new one is saved.
        */

        PreparedStatement deleteStatement = DbConnection.getInstance().getConnection()
                .prepareStatement("DELETE FROM BankDetails");

        if (deleteStatement.executeUpdate() > 0) {

            PreparedStatement saveStatement = DbConnection.getInstance().getConnection()
                    .prepareStatement("INSERT INTO BankDetails VALUES(?,?,?,?,?)");
            saveStatement.setObject(1,bankDetailsModel.getBankName());
            saveStatement.setObject(2,bankDetailsModel.getBranchName());
            saveStatement.setObject(3,bankDetailsModel.getBranch());
            saveStatement.setObject(4,bankDetailsModel.getRegNumber());
            saveStatement.setObject(5,bankDetailsModel.getDepDate());

            return saveStatement.executeUpdate() > 0;

        }else{
            return false;
        }
    }


}
