package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.DepositMoneyController;
import controller.dbControllers.SavingsAccountController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DepositObjectModel;
import model.LastTransacTableModel;
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

public class DepositFormController {

    public TableColumn colDate;
    public TableColumn colAccount;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public TableView<LastTransacTableModel> latestTransactions;
    public ComboBox<String> cmbBalanceShower;
    public TextField txtAccNumber;
    public TextField txtName;
    public TextField txtAccType;
    public TextField txtDescription;
    public TextField txtAmount;
    public Label lblTransactionID;
    public AnchorPane depositConfirmContext;
    public Label lblAccountBalance;
    public DepositObjectModel model;

    public void initialize() throws SQLException, ClassNotFoundException {
        // * Set values to the balance shower account type selector.
        cmbBalanceShower.setValue("Savings Account");

        // * Get the account number from the passer and setting passer to the default.
        model = ObjectPasser.getDepositObjectModel();
        ObjectPasser.setDepositObjectModel(null);

        // * Get required details and set data to fields.
        setDataToFields();

        // * Set data to table.
        try {
            setDataToTable();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // * Set cell value factories.
        colAccount.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    private void setDataToFields() throws SQLException, ClassNotFoundException {
        txtAccNumber.setText(model.getAccountNumber());
        txtName.setText(new CustomerDetailsController().getAccountHolderName(model.getAccountNumber()));
        txtAccType.setText("Savings Account");
        txtDescription.setText(model.getDescription());
        txtAmount.setText(String.valueOf(model.getAmount()));
        lblTransactionID.setText(model.getDepTransactionID());
        lblAccountBalance.setText("Rs. "+(new SavingsAccountController().getAccountBalance(model.getAccountNumber())));
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
            printReceipt();
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
    private void setDataToTable() throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<LastTransacTableModel> models = new DepositMoneyController().getLastDepositTransactions(model.getAccountNumber());
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

    private void printReceipt(){

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/reports/DepositReceipt.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            HashMap map = new HashMap();
            map.put("invoiceNumber",lblTransactionID.getText());
            map.put("accountNumber",txtAccNumber.getText());
            map.put("accountName",txtName.getText());
            map.put("accType",txtAccType.getText());
            map.put("description",txtDescription.getText());
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
