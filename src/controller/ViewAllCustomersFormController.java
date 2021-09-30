/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.CustomerDetailModelForView;
import model.CustomerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewAllCustomersFormController {


    public TableColumn colName;
    public TableColumn colAccountNumber;
    public TableColumn colMainBalance;
    public TableColumn colHoldedBalance;
    public TableColumn colInstantLoan;
    public TableColumn colLoanByDeposit;
    public TableColumn colRationLoan;
    public TableColumn colOpned;
    public TableView<CustomerDetailModelForView> tableCustomerDetails;
    public int selectedRow = -1;
    public Button btnViewWithTransaction;
    public Button btnViewDepTransaction;
    public Button btnViewDetails;
    public ArrayList<CustomerDetailModelForView> customerDataSet;
    public AnchorPane allCustomerContext;

    public void initialize() throws SQLException, ClassNotFoundException {

        // * Set data to tables.
        setDataToTable();

        // * Set cell value factories.
        colAccountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMainBalance.setCellValueFactory(new PropertyValueFactory<>("mainBalance"));
        colHoldedBalance.setCellValueFactory(new PropertyValueFactory<>("holedBalance"));
        colInstantLoan.setCellValueFactory(new PropertyValueFactory<>("instantLoan"));
        colLoanByDeposit.setCellValueFactory(new PropertyValueFactory<>("loanByDeposit"));
        colRationLoan.setCellValueFactory(new PropertyValueFactory<>("rationLoan"));
        colOpned.setCellValueFactory(new PropertyValueFactory<>("opened"));

        // * Listener for the selected row.
        tableCustomerDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = newValue.intValue();
        });

        /*tableCustomerDetails.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                selectedRow = -1;
            }
        });*/

    }

    private void setDataToTable() throws SQLException, ClassNotFoundException {
        customerDataSet = new CustomerDetailsController().getCustomersForView();
        ObservableList<CustomerDetailModelForView> tblData = FXCollections.observableArrayList();
        tblData.addAll(customerDataSet);
        tableCustomerDetails.setItems(tblData);
    }

    public void tableRowSelectOrNot(MouseEvent mouseEvent) {
        if (selectedRow==-1){
            btnViewDetails.setDisable(true);
            btnViewWithTransaction.setDisable(true);
            btnViewDepTransaction.setDisable(true);
        }else{
            btnViewDetails.setDisable(false);
            btnViewWithTransaction.setDisable(false);
            btnViewDepTransaction.setDisable(false);
        }
    }

    public void viewWithdrawalButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedRow!=-1){

            // * Get the selected attributes from the table.
            CustomerDetailModelForView customer = customerDataSet.get(selectedRow);
            ObjectPasser.setAccountNumberForShowWithdrawals(customer.getAccountNumber());
            URL resource = getClass().getResource("../view/ViewWithdrawTransaction.fxml");
            Parent load = FXMLLoader.load(resource);
            allCustomerContext.getChildren().clear();
            allCustomerContext.getChildren().add(load);

        }
    }

    public void viewAllDepositTransactions(ActionEvent actionEvent) throws IOException {
        if (selectedRow!=-1){

            // * Get the selected attributes from the table.
            CustomerDetailModelForView customer = customerDataSet.get(selectedRow);
            ObjectPasser.setAccountNumberForShowWithdrawals(customer.getAccountNumber());
            URL resource = getClass().getResource("../view/ViewDepositTransaction.fxml");
            Parent load = FXMLLoader.load(resource);
            allCustomerContext.getChildren().clear();
            allCustomerContext.getChildren().add(load);

        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        allCustomerContext.getChildren().clear();
        allCustomerContext.getChildren().add(load);
    }

    public void viewDetailsOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedRow != -1){
            CustomerDetailModelForView model = customerDataSet.get(selectedRow);
            ObjectPasser.setAccountNumber(model.getAccountNumber());
            URL resource = getClass().getResource("../view/SearchAccountResult.fxml");
            Parent load = FXMLLoader.load(resource);
            allCustomerContext.getChildren().clear();
            allCustomerContext.getChildren().add(load);
        }
    }
}
