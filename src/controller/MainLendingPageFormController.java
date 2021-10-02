/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.*;
import controller.dbControllers.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class MainLendingPageFormController {

    public TextField txtAccountNumber;
    public TextField txtLoanType;
    public TextField txtLoanNumber;
    public TextField txtMaxAmount;
    public TextField txtInterestRate;
    public TextField txtInterestType;
    public TextField txtMaxInstallments;
    public TextField txtInstallments;
    public TextField txtLoanAmount;
    public Label lblLoanType;
    public Label lblLoanNumber;
    public Label lblLoanAmount;
    public Label lblLoanIssuingDate;
    public Label lblInterestType;
    public Label lblInterest;
    public Label lblNumOfInstallments;
    public Label lblPaymentDate;
    public Label lblDebtorName;
    public Pane summeryPane;
    public CustomerModelMini customerMiniModel;
    public LoanDetailsModel loanModel;
    public Label lblInstallmentPrice;
    public AnchorPane confirmLendingContext;
    FadeTransition ft;
    public double installmentValue =0;
    public DecimalFormat df=new DecimalFormat("#.##");
    Pattern moneyPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern pattern = Pattern.compile("^[0-9]*$");

    public void initialize() throws SQLException, ClassNotFoundException {
        // * First it should take the loanModel from the confirmationPage.
        String loanType = ObjectPasser.getLoanType();
        customerMiniModel = ObjectPasser.getCustomerForLoanPassing();
        ObjectPasser.setModelsForLoanPassing(null,null);

        // * Get the loan model from database.
        loanModel = new LoanDetailsController().getLoanModelByType(loanType);
        // * Set data to fields.
        setDataToFields(customerMiniModel,loanModel);

        // * Animation for the window load
        ft = new FadeTransition(Duration.millis(2000));
        ft.setNode(summeryPane);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
    }

    private void setDataToFields(CustomerModelMini customerMiniModel, LoanDetailsModel loanModel) throws SQLException, ClassNotFoundException {
        double customerAccountBalance = new CustomerDetailsController().getAccountBalance(customerMiniModel.getAccountNumber());

        txtAccountNumber.setText(customerMiniModel.getAccountNumber());
        txtLoanType.setText(loanModel.getLoanName());
        txtLoanNumber.setText(new NumberGenerator().getLoanNumber());
        if (loanModel.getLoanName().equals("Loan By Deposit")){
            double maxAmount = (customerAccountBalance*loanModel.getMaximumLoanAmount())/100;
            double maxAmountWithInterest = (customerAccountBalance*loanModel.getMaximumLoanAmount()+8)/100;
            if (maxAmountWithInterest>new MoneyJournalController().getBalances("Main Balance")){
                txtMaxAmount.setText(String.valueOf(new MoneyJournalController().getBalances("Main Balance")-5000));
            }else{
                txtMaxAmount.setText(String.valueOf(maxAmount));
            }
        }else{
            double mainAccountBalance = new MoneyJournalController().getBalances("Main Balance");
            double maximumAmount = loanModel.getMaximumLoanAmount();
            if (mainAccountBalance+5000.0 > maximumAmount){
                txtMaxAmount.setText(String.valueOf(maximumAmount));
            }else{
                txtMaxAmount.setText(String.valueOf(mainAccountBalance-5000.0));
            }
        }
        txtMaxInstallments.setText(String.valueOf(loanModel.getMaximumNoOfInstallments()));
        txtInterestRate.setText(String.valueOf(loanModel.getInterest()));
        txtInterestType.setText(loanModel.getInterestType());
    }

    public void confirmButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FormFieldValidator validator = new FormFieldValidator(
                txtLoanAmount,txtInstallments
        );
        validator.emptyFieldProperties("-fx-border-color:red");
        validator.filledFieldProperties("-fx-border-color:#b5b5b5");

        if (!validator.checkEmptyFields()){
            if (!moneyPattern.matcher(txtLoanAmount.getText()).matches()){
                return;
            }
            // * If requirement is to get all main balances, then change here.
            /*if (Double.parseDouble(txtLoanAmount.getText()) > new MoneyJournalController().getBalances("Main Balance")+5000){
                ModifiedAlertBox alertBox = new ModifiedAlertBox("Out Of Main Balance", Alert.AlertType.ERROR,"ERROR!"
                        ,"Insufficient Main balance");
                alertBox.showAlert();
                return;
            }*/

            if (Integer.parseInt(txtInstallments.getText()) > loanModel.getMaximumNoOfInstallments()){
                ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR!", Alert.AlertType.CONFIRMATION,"ERROR!",
                        "Max. ins. is "+loanModel.getMaximumNoOfInstallments());
                alertBox.showAlert();
                return;
            }
            if (Double.parseDouble(txtLoanAmount.getText())>Double.parseDouble(txtMaxAmount.getText())){
                ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR!", Alert.AlertType.CONFIRMATION,"ERROR!",
                        "Max. Loan amount is out");
                alertBox.showAlert();
                return;
            }
            validator.setEditableFalse();
            lblDebtorName.setText(customerMiniModel.getName());
            lblInterest.setText(String.valueOf(loanModel.getInterest())+" %");
            lblLoanNumber.setText(txtLoanNumber.getText());
            lblInterestType.setText(loanModel.getInterestType());
            double loanAmount = Double.parseDouble(txtLoanAmount.getText());
            lblLoanAmount.setText(df.format(loanAmount));
            lblLoanIssuingDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate today = LocalDate.from(LocalDateTime.now());
            LocalDate nextInstallmentDay = today.plusMonths(1);
            lblPaymentDate.setText(String.valueOf(nextInstallmentDay));
            lblNumOfInstallments.setText(txtInstallments.getText());
            lblLoanType.setText(loanModel.getLoanName());
            installmentValue = new LoanInstallmentCalculator().
                    calculateInstallments(txtLoanType.getText(),Double.parseDouble(txtLoanAmount.getText()),
                            Integer.parseInt(txtInstallments.getText()));
            lblInstallmentPrice.setText(df.format(installmentValue));
            summeryPane.setVisible(true);
            ft.playFromStart();
        }

    }

    public void doneButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        LocalDate today = LocalDate.from(LocalDateTime.now());
        LocalDate nextInstallmentDay = today.plusMonths(1);
        Date sqlNextInstallmentDay = Date.valueOf(nextInstallmentDay);
        Alert alertBoxSaved = new Alert(Alert.AlertType.INFORMATION,"Successfully Saved!");
        switch (txtLoanType.getText()) {
            case "Instant Loan":

                // * Here goes savings process of the instant loan.
                InstantLoanModel instantLoanModel = new InstantLoanModel(
                        lblLoanNumber.getText(),
                        txtAccountNumber.getText(),
                        Double.parseDouble(lblLoanAmount.getText()),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        installmentValue,
                        loanModel.getInterest(),
                        Integer.parseInt(lblNumOfInstallments.getText()),
                        Integer.parseInt(lblNumOfInstallments.getText()), 0.0,
                        "Active",
                        sqlNextInstallmentDay
                );
                if (new InstantLoanController().saveLoan(instantLoanModel)) {
                    // * Here goes the code set to dashboard.
                    Optional<ButtonType> buttonType = alertBoxSaved.showAndWait();
                    if (!buttonType.isPresent()){
                        navigateToDashboard();
                    }else{
                        navigateToDashboard();
                    }

                } else {
                    System.out.println("Instant Model not saved!");
                }

                break;
            case "Ration Loan":

                // * Here goes savings process of the Ration loan.
                RationLoanModel rationLoanModel = new RationLoanModel(
                        lblLoanNumber.getText(),
                        txtAccountNumber.getText(),
                        Double.parseDouble(lblLoanAmount.getText()),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        installmentValue,
                        Integer.parseInt(lblNumOfInstallments.getText()),
                        Integer.parseInt(lblNumOfInstallments.getText()), 0.0,
                        "Active",
                        sqlNextInstallmentDay,
                        loanModel.getInterest()
                );
                System.out.println("Interest : "+loanModel.getInterest());
                if (new RationLoanController().saveLoan(rationLoanModel)) {
                    Optional<ButtonType> buttonType = alertBoxSaved.showAndWait();
                    if (!buttonType.isPresent()){
                        navigateToDashboard();
                    }else{
                        navigateToDashboard();
                    }
                    break;
                } else {
                    System.out.println("Not Saved Ration Loan Model");
                }
                break;
            case "Loan By Deposit":

                // * Here goes savings process of the Loan By Deposit loan.
                LoanByDeposit loanByDeposit = new LoanByDeposit(
                        lblLoanNumber.getText(),
                        txtAccountNumber.getText(),
                        Double.parseDouble(lblLoanAmount.getText()),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        installmentValue,
                        Integer.parseInt(lblNumOfInstallments.getText()),
                        Integer.parseInt(lblNumOfInstallments.getText()), 0.0,
                        "Active",
                        sqlNextInstallmentDay,
                        loanModel.getInterest()
                );
                if (new LoanByDepositController().saveLoan(loanByDeposit)) {
                    System.out.println("Loan By Deposit Saved!");
                    // * Here goes successes Message at the loan by deposit saved.
                    Optional<ButtonType> buttonType = alertBoxSaved.showAndWait();
                    if (!buttonType.isPresent()){
                        navigateToDashboard();
                    }else{
                        navigateToDashboard();
                    }
                    break;
                } else {
                    System.out.println("Loan By Deposit not saved!");
                }
                break;
            default:
                System.out.println("Loan type is not valid.");
                break;
        }
    }

    private void navigateToDashboard() throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        confirmLendingContext.getChildren().clear();
        confirmLendingContext.getChildren().add(load);
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException ignored) {
        }
        confirmLendingContext.getChildren().clear();
        confirmLendingContext.getChildren().add(load);
    }

    public void keyReleasedAction(KeyEvent keyEvent) {
        FormFieldValidator validator = new FormFieldValidator();
        TextField currentField = (TextField) keyEvent.getSource();
        validator.validateSingle(currentField,moneyPattern);
    }

    public void numberReleasedAction(KeyEvent keyEvent) {
        FormFieldValidator validator = new FormFieldValidator();
        TextField currentField = (TextField) keyEvent.getSource();
        validator.validateSingle(currentField,pattern);
    }
}
