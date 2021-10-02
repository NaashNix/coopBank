/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoanArrearsFormController {

    public TableView<ArrearsLoanDisplayModel> tableExpenditures;
    public TableColumn colLoanNumber;
    public TableColumn colDebtorName;
    public TableColumn colInstallment;
    public TableColumn colLoanAmount;
    public TableColumn colPaymentDate;
    public TableColumn colRemainingInstallments;
    public TableColumn colDebtorTelephone;
    public TableColumn colLoanType;
    public ObservableList<ArrearsLoanDisplayModel> allModels;
    public ArrayList<InstantLoanModel> instantLoanModels;
    public ArrayList<LoanByDeposit> loanByDepositsModels;
    public ArrayList<RationLoanModel> rationLoanModels;
    public int selectedRow = -1;

    public void initialize() throws SQLException, ClassNotFoundException {
        instantLoanModels = ObjectPasser.getArrearsInstantLoans();
        loanByDepositsModels = ObjectPasser.getArrearsLoanByDeposit();
        rationLoanModels = ObjectPasser.getArrearsRationLoans();

        ObjectPasser.setArrearsLoans(null,null,null);

        // * Observable List to add the models.
        allModels = FXCollections.observableArrayList();

        // * Move all to the one arrayList and make the object all of them.
        addAllToTheMainList();

        // * table cell factories.
        colLoanNumber.setCellValueFactory(new PropertyValueFactory<>("loanNumber"));
        colDebtorName.setCellValueFactory(new PropertyValueFactory<>("debtorName"));
        colInstallment.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colLoanAmount.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colRemainingInstallments.setCellValueFactory(new PropertyValueFactory<>("remainingInstallments"));
        colDebtorTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));


    }


    public void addAllToTheMainList() throws SQLException, ClassNotFoundException {
        if (instantLoanModels!=null){
            for (InstantLoanModel iModel : instantLoanModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(iModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        iModel.getiLoanNumber(),
                        cModel.getName(),
                        "Instant Loan", iModel.getiMonthlyInstallment(),
                        iModel.getiLoanAmount(),
                        iModel.getNextInstallmentDate(),
                        iModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress()
                ));
            }
        }

        if (loanByDepositsModels!=null){
            for (LoanByDeposit lModel : loanByDepositsModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(lModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        lModel.getLoanNumber(),
                        cModel.getName(),
                        "Loan By Dep.", lModel.getMonthlyInstallment(),
                        lModel.getLoanAmount(),
                        lModel.getNextInstallmentDate(),
                        lModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress()
                ));
            }
        }

        if (rationLoanModels!=null){
            for (RationLoanModel rModel : rationLoanModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(rModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        rModel.getLoanNumber(),
                        cModel.getName(),
                        "Ration Loan", rModel.getMonthlyInstallment(),
                        rModel.getLoanAmount(),
                        rModel.getNextInstallmentDate(),
                        rModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress()
                ));
            }
        }

        tableExpenditures.setItems(allModels);
    }

    public void sendEmailOnAction(ActionEvent actionEvent) {

    }
}
