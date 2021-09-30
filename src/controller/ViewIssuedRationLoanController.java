/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.LoanByDepositController;
import controller.dbControllers.RationLoanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IssuedLoanTableModel;
import model.LoanByDeposit;
import model.RationLoanModel;

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

    }

    private void setDataToTables() throws SQLException, ClassNotFoundException {
        ArrayList<RationLoanModel> rationLoanSet = new RationLoanController().getIssuedLoans();
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
}
