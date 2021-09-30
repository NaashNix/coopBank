/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.DepositMoneyController;
import controller.dbControllers.WithdrawMoneyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.LastTransacTableModel;
import model.WithdrawObjectModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewWithdrawTransactionController {

    public TableColumn colDate;
    public TableColumn colAccount;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public TableView<LastTransacTableModel> latestTransactions;
    public TextField txtAccNumber;
    public TextField txtName;
    public TextField txtAccType;
    public AnchorPane viewWithdrawContext;

    public void initialize() throws SQLException, ClassNotFoundException {
        String accountNumber = ObjectPasser.getAccountNumberForShowWithdrawals();
        if (accountNumber != null){
            ObjectPasser.setAccountNumberForShowWithdrawals(null);
            try {
                setDataToTableAndFields(accountNumber);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // * Setting cell value factories.
        colAccount.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setDataToTableAndFields(String accountNumber) throws SQLException, ClassNotFoundException, ParseException {
        CustomerModel model = new CustomerDetailsController().getCustomerDetails(accountNumber);

        if (model!=null){
            txtAccNumber.setText(model.getAccountNumber());
            txtName.setText(model.getName());
            txtAccType.setText(model.getAccountType());
            setDataToTable(model);
        }

    }

    private void setDataToTable(CustomerModel model) throws SQLException, ClassNotFoundException, ParseException {
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

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewAllCustomersForm.fxml");
        Parent load = FXMLLoader.load(resource);
        viewWithdrawContext.getStylesheets().clear();
        viewWithdrawContext.getChildren().clear();
        viewWithdrawContext.getChildren().add(load);
    }
}
