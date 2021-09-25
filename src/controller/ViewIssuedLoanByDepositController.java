/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.InstantLoanController;
import controller.dbControllers.LoanByDepositController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.InstantLoanModel;
import model.IssuedLoanTableModel;
import model.LoanByDeposit;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewIssuedLoanByDepositController {
    public TableColumn colName;
    public TableColumn colLoanCode;
    public TableColumn colLoanAmount;
    public TableColumn colNextInstallment;
    public TableColumn colPaymentDate;
    public TableColumn colInterest;
    public TableView<IssuedLoanTableModel> tableInstantLoan;
    public TableColumn colNumOfInstallments;
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
        ArrayList<LoanByDeposit> loanByDepositSet = new LoanByDepositController().getIssuedLoans();
        ObservableList<IssuedLoanTableModel> tableData = FXCollections.observableArrayList();
        if (loanByDepositSet == null) {
            new Alert(Alert.AlertType.ERROR,"No Loans Issued").show();
        } else {
            for (LoanByDeposit model : loanByDepositSet
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
            tableInstantLoan.setItems(tableData);
        }
    }

}
