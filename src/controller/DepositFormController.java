package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.DepositMoneyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DepositObjectModel;
import model.OpenAccDepMoneyModel;
import model.SampleTM;

import java.sql.SQLException;

public class DepositFormController {

    public TableColumn colDate;
    public TableColumn colAccount;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public TableView<SampleTM> latestTransactions;
    public ComboBox<String> cmbBalanceShower;
    public TextField txtAccNumber;
    public TextField txtName;
    public TextField txtAccType;
    public TextField txtDescription;
    public TextField txtAmount;
    public Label lblTransactionID;
    DepositObjectModel model;

    public void initialize() throws SQLException, ClassNotFoundException {
        // * Set values to the balance shower account type selector.
        cmbBalanceShower.setValue("Savings Account");

        // * Get the account number from the passer and setting passer to the default.
        model = ObjectPasser.getDepositObjectModel();
        ObjectPasser.setDepositObjectModel(null);

        // * Get required details and set data to fields.
        setDataToFields();
    }

    private void setDataToFields() throws SQLException, ClassNotFoundException {
        txtAccNumber.setText(model.getAccountNumber());
        txtName.setText(new CustomerDetailsController().getAccountHolderName(model.getAccountNumber()));
        txtAccType.setText("Savings Account");
        txtDescription.setText(model.getDescription());
        txtAmount.setText(String.valueOf(model.getAmount()));
        lblTransactionID.setText(model.getDepTransactionID());
    }


    public void confirmButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FormFieldValidator validator = new FormFieldValidator(
                txtAmount,txtDescription
        );
        if (validator.checkEmptyFields()){
            validator.emptyFieldProperties("-fx-border-color:red");
        }else{
            validator.filledFieldProperties("-fx-border-color:#b5b5b5");
            DepositObjectModel deposit = new DepositObjectModel(
                    model.getAccountNumber(),
                    model.getDate(),
                    model.getTime(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDescription.getText(),
                    model.getDepTransactionID()
            );
            proceedToMakeDeposit(deposit);
        }
    }

    private void proceedToMakeDeposit(DepositObjectModel deposit) throws SQLException, ClassNotFoundException {
        if(new DepositMoneyController().updateDepositInfo(deposit)){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION,
                    "Succeed!","Transaction succeed!");
            alertBox.showAlert();
        }else{
            System.out.println("ERROR->[@Code:01]");
        }
    }
}
