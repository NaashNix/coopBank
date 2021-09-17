package controller;

import controller.components.ModifiedAlertBox;
import controller.dbControllers.BankDetailsController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.BankDetailsModel;

import java.sql.SQLException;

public class BankDetailsFormController {
    public TextField txtBankName;
    public TextField txtBranch;
    public TextField txtRegNumber;
    public TextField txtDepDate;
    public TextField txtBranchName;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadBankDetails(); // * Load all the data to the fields.
    }

    private void loadBankDetails() throws SQLException, ClassNotFoundException {

        /*
            --> This method called in initialize() method.
            --> This method will set the fields of bank details.
         */

        BankDetailsModel bankDetailsModel = new BankDetailsController().getBankDetails();
        if (bankDetailsModel == null){

        }else{
            txtBankName.setText(bankDetailsModel.getBankName());
            txtBranchName.setText(bankDetailsModel.getBranchName());
            txtBranch.setText(bankDetailsModel.getBranch());
            txtRegNumber.setText(bankDetailsModel.getRegNumber());
            txtDepDate.setText(bankDetailsModel.getDepDate());
        }
    }


    public void saveBranchDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        /*
            --> save the updated bank details when 'Done' button clicked.
            --> All fields must filled to complete the process.
         */

        // * Creating Bank Details Model from the updated Details.

        if (validateFields()){

                if (new BankDetailsController().bankDetailsSave(
                        new BankDetailsModel(
                                txtBankName.getText(),
                                txtBranchName.getText(),
                                txtBranch.getText(),
                                txtRegNumber.getText(),
                                txtDepDate.getText()
                        ))){

                    // * Make Alert Box (Successful)
                    ModifiedAlertBox alert = new ModifiedAlertBox(
                            "Successfully Updated!",
                            Alert.AlertType.INFORMATION,
                            "Done!",
                            "Bank Details have been updated!"
                    );
                    alert.showAlert();

                }else{
                    // * Make Alert box (Failed!)
                    System.out.println("Failed!");

                }
        }else{
            System.out.println("Validation Failed!");
        }
    }

    private boolean validateFields() {

        /*
            --> Ensuring that the all the fields are filled.
            --> This method is called in the saveBranchDetails() method.
            --> Return (boolean), if true * then saving details process is starts.
         */

        // * If all fields are filled, returns true;
        return !txtBranchName.getText().isEmpty() && !txtBranch.getText().isEmpty()
                && !txtBankName.getText().isEmpty() && !txtRegNumber.getText().isEmpty()
                && !txtDepDate.getText().isEmpty();
        // * If any of the above is empty, then it returns false;
    }
}
