package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.DepositMoneyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.DepositObjectModel;
import model.OpenAccDepMoneyModel;
import model.SampleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

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
    public AnchorPane depositConfirmContext;
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

    private void proceedToMakeDeposit(DepositObjectModel deposit) throws SQLException, ClassNotFoundException{
        if(new DepositMoneyController().updateDepositInfo(deposit)){
            refresh();
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION,
                    "Succeed!","Transaction succeed!");
            alertBox.showAlert();

        }else{
            System.out.println("ERROR->[@Code:01]");
        }
    }

    public void depositCancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        depositConfirmContext.getChildren().clear();
        depositConfirmContext.getChildren().add(load);
    }

    private void refresh()  {
        try {
            URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
            assert resource != null;
            Parent load = FXMLLoader.load(resource);
            depositConfirmContext.getChildren().clear();
            depositConfirmContext.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
