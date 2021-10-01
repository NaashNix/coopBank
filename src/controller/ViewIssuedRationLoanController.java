/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.LoanByDepositController;
import controller.dbControllers.RationLoanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.IssuedLoanTableModel;
import model.LoanByDeposit;
import model.RationLoanModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewIssuedRationLoanController {
    public TableView<IssuedLoanTableModel> tableRationLoan;
    public TableColumn colName;
    public TableColumn colLoanCode;
    public TableColumn colLoanAmount;
    public TableColumn colNextInstallment;
    public TableColumn colNumOfInstallments;
    public TableColumn colPaymentDate;
    public TableColumn colInterest;
    public TableColumn colLoanStatus;
    public int selectedRow = -1;
    public Button btnPay;
    public Button btnViewDetails;
    public AnchorPane rationLoanContext;


    public void initialize() throws SQLException, ClassNotFoundException {
        // * Get the instant loan models and set to the table.
        setDataToTables();

        // * Set the column to the setPropertyValuesFactories.
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        colLoanAmount.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colLoanCode.setCellValueFactory(new PropertyValueFactory<>("loanNumber"));
        colNextInstallment.setCellValueFactory(new PropertyValueFactory<>("nextInstallment"));
        colLoanStatus.setCellValueFactory(new PropertyValueFactory<>("loanStatus"));
        colNumOfInstallments.setCellValueFactory(new PropertyValueFactory<>("numberOfInstallments"));

        // * Table
        tableRationLoan.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = newValue.intValue();
        });
    }

    public void tableRowSelectOrNot(MouseEvent mouseEvent) {
        if (selectedRow==-1){
            btnPay.setDisable(true);
            btnViewDetails.setDisable(true);
        }else{
            btnViewDetails.setDisable(false);
            btnPay.setDisable(false);
        }
    }

    public ArrayList<RationLoanModel> rationLoanSet;

    private void setDataToTables() throws SQLException, ClassNotFoundException {
        rationLoanSet = new RationLoanController().getIssuedLoans();
        ObservableList<IssuedLoanTableModel> tableData = FXCollections.observableArrayList();
        if (rationLoanSet == null) {
            new Alert(Alert.AlertType.ERROR,"No Loans Issued").show();
        } else {
            for (RationLoanModel model : rationLoanSet
            ) {
                String name = new CustomerDetailsController().getAccountHolderName(model.getAccountNumber());
                tableData.add(new IssuedLoanTableModel(
                        name,
                        model.getLoanNumber(),
                        model.getLoanAmount(),
                        model.getMonthlyInstallment(),
                        model.getNextInstallmentDate().toString(),
                        model.getInterest(),
                        model.getNumberOfInstallments(),
                        model.getLoanStatus()
                ));

            }
            tableRationLoan.setItems(tableData);
        }
    }

    public void payOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedRow!=-1){
            RationLoanModel rationLoanModel = rationLoanSet.get(selectedRow);
            if (rationLoanModel.getLoanStatus().equals("Active")){
                ObjectPasser.setAccountNumberForPayLoan(rationLoanModel.getAccountNumber());
                URL resource = getClass().getResource("../view/PayLoanInstallments.fxml");
                Parent load = FXMLLoader.load(resource);
                rationLoanContext.getChildren().clear();
                rationLoanContext.getChildren().add(load);
            }else{
                ModifiedAlertBox alertBox = new ModifiedAlertBox("No Payment", Alert.AlertType.ERROR,"Complete","Loan is already ended.");
                alertBox.showAlert();
            }
        }else{
            ModifiedAlertBox alertBox = new ModifiedAlertBox("No Row Selected", Alert.AlertType.ERROR,"ERROR!","Please select a record.");
            alertBox.showAlert();
        }
    }


    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        rationLoanContext.getChildren().clear();
        rationLoanContext.getChildren().add(load);
    }
}
