/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewAllCustomersFormController {


    public TableColumn colName;
    public TableColumn colAccountNumber;
    public TableColumn colMainBalance;
    public TableColumn colHoldedBalance;
    public TableColumn colInstantLoan;
    public TableColumn colLoanByDeposit;
    public TableColumn colRationLoan;
    public TableColumn colOpned;
    public TableView tableCustomerDetails;

    public void initialize(){
        colAccountNumber.setCellValueFactory(new PropertyValueFactory<>(""));
        colName.setCellValueFactory(new PropertyValueFactory<>(""));
        colMainBalance.setCellValueFactory(new PropertyValueFactory<>(""));
        colHoldedBalance.setCellValueFactory(new PropertyValueFactory<>(""));
        colInstantLoan.setCellValueFactory(new PropertyValueFactory<>(""));
        colLoanByDeposit.setCellValueFactory(new PropertyValueFactory<>(""));
        colRationLoan.setCellValueFactory(new PropertyValueFactory<>(""));
        colOpned.setCellValueFactory(new PropertyValueFactory<>(""));

    }
}
