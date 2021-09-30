package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.NumberGenerator;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.SavingsAccountController;
import controller.dbControllers.WithdrawMoneyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.LastTransacTableModel;
import model.WithdrawObjectModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WithdrawFormController {

    public TableView<LastTransacTableModel> latestTransactions;
    public TableColumn<LastTransacTableModel,String> colDate;
    public TableColumn<LastTransacTableModel,String> colAccount;
    public TableColumn<LastTransacTableModel,String> colDescription;
    public TableColumn colAmount;
    public TextField txtAccNumber;
    public TextField txtName;
    public TextField txtAccType;
    public TextField txtDesc;
    public TextField txtAmount;
    public ComboBox<String> cmbAccountType;
    public WithdrawObjectModel model;
    public Label txtCurrentAccountBalanceShower;
    public AnchorPane withdrawConfirmContext;
    public Label lblTransactionID;


    public void initialize() throws SQLException, ClassNotFoundException, ParseException {
        // * Setting values to the combo box.
        cmbAccountType.setValue("Savings Account");

        // * Setting up table columns
        colAccount.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // * Get the account number from the passer and setting passer to the default.
        model = ObjectPasser.getWithdrawObjectModel();
        ObjectPasser.setWithdrawObjectModel(null);

        // * Set data to fields.
        setDataToFields();

        // * Getting the data to the table and load them.
        setDataToTable();
    }

    private void setDataToTable() throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<LastTransacTableModel> models = new WithdrawMoneyController().getLastWithdrawalTransactions(model.getAccountNumber());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tableDateFormatter = new SimpleDateFormat("dd/MM");
        ObservableList<LastTransacTableModel> tblModels = FXCollections.observableArrayList();
        for (LastTransacTableModel tempModel : models
             ) {
            Date date = format.parse(tempModel.getDate());

            tblModels.add(new LastTransacTableModel(
                        tableDateFormatter.format(date),
                    "Savings Account",
                    tempModel.getDescription(),
                    tempModel.getAmount()
            ));
        }
        latestTransactions.setItems(tblModels);
    }

    private void setDataToFields() throws SQLException, ClassNotFoundException {
        lblTransactionID.setText(new NumberGenerator().getWithdrawalID());
        txtAccNumber.setText(model.getAccountNumber());
        txtCurrentAccountBalanceShower.setText("Rs. "+new SavingsAccountController().getAccountBalance(txtAccNumber.getText()));
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
            printReceipt();
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION,
                    "Succeed!","Transaction succeed!");
            alertBox.showAlert();
        }else{
            System.out.println("ERROR->[@Code:01]");
        }

    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        withdrawConfirmContext.getChildren().clear();
        withdrawConfirmContext.getChildren().add(load);
    }

    private void printReceipt(){

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/reports/WithdrawReceipt.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            HashMap map = new HashMap();
            map.put("invoiceNumber",lblTransactionID.getText());
            map.put("accountNumber",txtAccNumber.getText());
            map.put("accountName",txtName.getText());
            map.put("accType",txtAccType.getText());
            map.put("description",txtDesc.getText());
            map.put("amount",txtAmount.getText());
            double balance = new SavingsAccountController().getAccountBalance(txtAccNumber.getText());
            map.put("balance",String.valueOf(balance));


            JasperPrint print = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
            JasperViewer.viewReport(print,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
