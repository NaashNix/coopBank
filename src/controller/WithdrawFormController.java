package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.WithdrawMoneyController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.SampleTM;
import model.WithdrawObjectModel;

import java.sql.SQLException;

public class WithdrawFormController {

    public TableView<SampleTM> latestTransactions;
    public TableColumn<SampleTM,String> colDate;
    public TableColumn<SampleTM,String> colAccount;
    public TableColumn<SampleTM,String> colDescription;
    public TableColumn<SampleTM,String> colAmount;
    public TextField txtAccNumber;
    public TextField txtName;
    public TextField txtAccType;
    public TextField txtDesc;
    public TextField txtAmount;
    public ComboBox<String> cmbAccountType;
    public WithdrawObjectModel model;


    public void initialize() throws SQLException, ClassNotFoundException {
        // * Setting values to the combo box.
        cmbAccountType.setValue("Savings Account");

        // * Get the account number from the passer and setting passer to the default.
        model = ObjectPasser.getWithdrawObjectModel();
        ObjectPasser.setWithdrawObjectModel(null);

        // * Set data to fields.
        setDataToFields();

    }

    private void setDataToFields() throws SQLException, ClassNotFoundException {
        txtAccNumber.setText(model.getAccountNumber());
        txtAccType.setText("Savings Account");
        txtAmount.setText(String.valueOf(model.getAmount()));
        txtName.setText(new CustomerDetailsController().getAccountHolderName(model.getAccountNumber()));
        txtDesc.setText(model.getDescription());
    }

    public void btnConfirmWithdrawOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FormFieldValidator validator = new FormFieldValidator(
                txtAmount,txtDesc
        );
        if (validator.checkEmptyFields()){
            validator.emptyFieldProperties("-fx-border-color:red");
        }else{
            validator.filledFieldProperties("-fx-border-color:#b5b5b5");
            WithdrawObjectModel withdraw = new WithdrawObjectModel(
                    model.getAccountNumber(),
                    model.getDate(),
                    model.getTime(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDesc.getText(),
                    model.getWithdrawTransactionID()
            );
            proceedToMakeDeposit(withdraw);
        }
    }

    private void proceedToMakeDeposit(WithdrawObjectModel withdraw) throws SQLException, ClassNotFoundException {
        if(new WithdrawMoneyController().updateWithdrawInfo(withdraw)){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION,
                    "Succeed!","Transaction succeed!");
            alertBox.showAlert();
        }else{
            System.out.println("ERROR->[@Code:01]");
        }

    }
}
