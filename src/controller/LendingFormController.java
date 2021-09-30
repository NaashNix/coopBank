/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.LoanAlgorithm;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.LoanDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.CustomerModelMini;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LendingFormController {

    public TextField txtAccountNumberSearchField;
    public TextField txtAccountNumber;
    public TextField txtName;
    public TextField txtTelephone;
    public TextField txtRationLoan;
    public TextField txtLoanByDeposit;
    public TextField txtInstantLoan;
    public Button btnGoForRationLoan;
    public Button btnGoForDepositLoan;
    public Button btnGoForInstantLoan;
    public CustomerModelMini customer = null;
    public AnchorPane lendingFormContext;

    public void findButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!txtAccountNumberSearchField.getText().isEmpty()){
             customer = new CustomerDetailsController().getMiniCustomerModel(
                    txtAccountNumberSearchField.getText()
            );
            if (customer!=null){
                setDataToFields(customer);
            }else {
                ModifiedAlertBox alert = new ModifiedAlertBox(
                        "Couldn't find", Alert.AlertType.ERROR,"ERROR!","Invalid account number"
                );
                alert.showAlert();
            }
        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox(
              "Couldn't find", Alert.AlertType.ERROR,"ERROR!","Invalid account number"
            );
            alert.showAlert();
        }
    }

    private void setDataToFields(CustomerModelMini customerModel) throws SQLException, ClassNotFoundException {
        txtAccountNumber.setText(customerModel.getAccountNumber());
        txtName.setText(customerModel.getName());
        txtTelephone.setText(customerModel.getTelephone());

        loadingButtonsAndDoingAlgorithm(customerModel);


    }

    private void loadingButtonsAndDoingAlgorithm(CustomerModelMini customerModel) throws SQLException, ClassNotFoundException {
        if (customerModel.getInstantLoan()==null || customerModel.getInstantLoan().equals("NULL")) {
            LoanAlgorithm loanAction = new LoanAlgorithm();
            if (loanAction.doTheAlgorithmForInstantLoan(customerModel)) {
                btnGoForInstantLoan.setDisable(false);
                txtInstantLoan.setText("Eligible");
            }else{
                txtInstantLoan.setText("Not Eligible");
            }
        }else{
            txtInstantLoan.setText("Already have a loan");
        }

        if (customerModel.getLoanByDeposit()==null || customerModel.getLoanByDeposit().equals("NULL")) {
            LoanAlgorithm loanAction = new LoanAlgorithm();
            if (loanAction.doTheAlgorithmForLoanByDeposit(customerModel)) {
                btnGoForDepositLoan.setDisable(false);
                txtLoanByDeposit.setText("Eligible");
            }else{
                txtLoanByDeposit.setText("Not Eligible");
            }
        }else{
            txtLoanByDeposit.setText("Already have a loan");
        }

        if (customerModel.getRationLoan()==null || customerModel.getRationLoan().equals("NULL")){
            LoanAlgorithm loanAction = new LoanAlgorithm();
            if (loanAction.doTheAlgorithmForRationLoan(customerModel)){
              btnGoForRationLoan.setDisable(false);
              txtRationLoan.setText("Eligible");
            }else{
                txtRationLoan.setText("Not Eligible");
            }
        }else{
            txtRationLoan.setText("Already have a loan");
        }
    }


    public void rationLoanButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (customer!=null){
            ObjectPasser.setModelsForLoanPassing(customer,"Ration Loan");
            navigateMainLendingPage();
        }else{
            System.out.println("ERROR [code@002]'Customer=null'");
        }

    }

    private void navigateMainLendingPage() throws IOException {
        URL resource = getClass().getResource("../view/MainLendingFormPage.fxml");
        Parent load = FXMLLoader.load(resource);
        lendingFormContext.getChildren().clear();
        lendingFormContext.getChildren().add(load);
    }

    public void loanByDepositButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (customer!=null){
            ObjectPasser.setModelsForLoanPassing(customer,"Loan By Deposit");
            navigateMainLendingPage();
        }else{
            System.out.println("ERROR [code@002]'Customer=null'");
        }

    }

    public void instantLoanButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (customer!=null){
            ObjectPasser.setModelsForLoanPassing(customer,"Instant Loan");
            navigateMainLendingPage();
        }else{
            System.out.println("ERROR [code@002]'Customer=null'");
        }

    }
}
