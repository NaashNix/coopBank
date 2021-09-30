/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
import controller.components.LoanInstallmentCalculator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PayLoanInstallmentsController {
    public TextField txtAccountNumberSearch;
    public Rectangle SQSearchIcon;
    public ImageView imgSearchIcon;
    public Pane paneInstantLoan;
    public TextField txtLoanNumber;
    public TextField txtDebtorName;
    public TextField txtLoanAmount;
    public TextField txtInstallment;
    public TextField txtPaymentDate;
    public Pane summeryPane;
    public Label lblDebtorName;
    public Label lblLoanType;
    public Label lblLoanNumber;
    public Label lblLoanAmount;
    public Label lblLoanIssuingDate;
    public Label lblInterest;
    public Label lblPaymentDate;
    public Label lblInstallmentPrice;
    public TextField txtUserName;
    public TextField txtPassword;
    public JFXComboBox<String> cmbLoanTypes;
    public String accountNumber;
    public CustomerModel customerModel;
    public InstantLoanModel instantLoanModel = null;
    public LoanByDeposit loanByDeposit = null;
    public RationLoanModel rationLoanModel = null;
    public DecimalFormat df = new DecimalFormat("#.##");

    public void initialize() throws SQLException, ClassNotFoundException {
        accountNumber = ObjectPasser.getAccountNumberForPayLoan();
        ObjectPasser.setAccountNumberForPayLoan(null);
        if (accountNumber != null) {
            txtAccountNumberSearch.setText(accountNumber);
            deepSearchMethod();
        }

        cmbLoanTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setDataToFields(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        // * Fade Transition
    }

    private void setDataToFields(String loanType) throws SQLException, ClassNotFoundException {
        System.out.println("Set Data To Fields");
        accountNumber = txtAccountNumberSearch.getText();
        if (loanType.equals("Instant Loan")) {
            instantLoanModel = new InstantLoanController().getLoanByNumber(accountNumber);
            if (instantLoanModel != null) {
                txtLoanNumber.setText(instantLoanModel.getiLoanNumber());
                txtDebtorName.setText(customerModel.getName());
                txtLoanAmount.setText(String.valueOf(instantLoanModel.getiLoanAmount()));
                txtInstallment.setText(String.valueOf(instantLoanModel.getiMonthlyInstallment()));
                txtPaymentDate.setText(String.valueOf(instantLoanModel.getNextInstallmentDate()));
            }
        } else if (loanType.equals("Loan By Deposit")) {
            loanByDeposit = new LoanByDepositController().getLoanByNumber(accountNumber);
            System.out.println(loanByDeposit.toString());
            if (loanByDeposit != null) {
                txtLoanNumber.setText(loanByDeposit.getLoanNumber());
                txtDebtorName.setText(customerModel.getName());
                txtLoanAmount.setText(String.valueOf(loanByDeposit.getLoanAmount()));
                txtInstallment.setText(String.valueOf(loanByDeposit.getMonthlyInstallment()));
                txtPaymentDate.setText(String.valueOf(loanByDeposit.getNextInstallmentDate()));
            }
        } else if (loanType.equals("Ration Loan")) {
            rationLoanModel = new RationLoanController().getRationLoanModelByID(accountNumber);
            if (rationLoanModel != null) {
                txtLoanNumber.setText(rationLoanModel.getLoanNumber());
                txtDebtorName.setText(customerModel.getName());
                txtLoanAmount.setText(String.valueOf(rationLoanModel.getLoanAmount()));
                txtInstallment.setText(String.valueOf(rationLoanModel.getMonthlyInstallment()));
                txtPaymentDate.setText(String.valueOf(rationLoanModel.getNextInstallmentDate()));
            }
        } else {
            System.out.println("Wrong Loan Model Type");
        }
    }

    ArrayList<String> loanTypes = new ArrayList<>();
    private void setDataToComboBox(String accNumber) throws SQLException, ClassNotFoundException {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800));
        fadeTransition.setNode(paneInstantLoan);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        paneInstantLoan.setVisible(true);
        fadeTransition.playFromStart();
        customerModel = new CustomerDetailsController().getCustomerDetails(accNumber);
        System.out.println(customerModel.toString());
        loanTypes.clear();
        cmbLoanTypes.getItems().clear();
        if (customerModel.getLoanByDeposit() != null) {
            if (!customerModel.getLoanByDeposit().equals("NULL")){
                loanTypes.add("Loan By Deposit");
            }
        }
        if (customerModel.getRationLoan() != null) {
            if (!customerModel.getRationLoan().equals("NULL")){
                loanTypes.add("Ration Loan");
            }

        }
        if (customerModel.getInstantLoan() != null) {
            if (!customerModel.getInstantLoan().equals("NULL")){
                loanTypes.add("Instant Loan");
            }
        }
        cmbLoanTypes.getItems().addAll(loanTypes);
        if (loanTypes.isEmpty()) {
            ModifiedAlertBox alertBox = new ModifiedAlertBox(
                    "ERROR!", Alert.AlertType.ERROR,"ERROR!","No any issued loans"
            );
            alertBox.showAlert();
        }else{
            cmbLoanTypes.setDisable(false);
        }
    }

    public void payButtonOnAction(ActionEvent actionEvent) {
        if (cmbLoanTypes.getValue().equals("Instant Loan")) {
            lblDebtorName.setText(customerModel.getName());
            lblInterest.setText(String.valueOf(instantLoanModel.getInterest()) + " %");
            lblLoanNumber.setText(txtLoanNumber.getText());
            double loanAmount = (instantLoanModel.getiLoanAmount());
            lblLoanAmount.setText(df.format(loanAmount));
            lblLoanIssuingDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate today = LocalDate.from(LocalDateTime.now());
            LocalDate nextInstallmentDay = today.plusMonths(1);
            lblPaymentDate.setText(String.valueOf(nextInstallmentDay));
            lblLoanType.setText("Instant Loan");
            lblInstallmentPrice.setText(df.format(instantLoanModel.getiMonthlyInstallment()));
        }else if (cmbLoanTypes.getValue().equals("Ration Loan")){
            lblDebtorName.setText(customerModel.getName());
            lblInterest.setText(String.valueOf(rationLoanModel.getInterest()) + " %");
            lblLoanNumber.setText(txtLoanNumber.getText());
            double loanAmount = (rationLoanModel.getLoanAmount());
            lblLoanAmount.setText(df.format(loanAmount));
            lblLoanIssuingDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate today = LocalDate.from(LocalDateTime.now());
            LocalDate nextInstallmentDay = today.plusMonths(1);
            lblPaymentDate.setText(String.valueOf(nextInstallmentDay));
            lblLoanType.setText("Ration Loan");
            lblInstallmentPrice.setText(df.format(rationLoanModel.getMonthlyInstallment()));

        }else if (cmbLoanTypes.getValue().equals("Loan By Deposit")){
            lblDebtorName.setText(customerModel.getName());
            lblInterest.setText(String.valueOf(loanByDeposit.getInterest()) + " %");
            lblLoanNumber.setText(txtLoanNumber.getText());
            double loanAmount = (loanByDeposit.getLoanAmount());
            lblLoanAmount.setText(df.format(loanAmount));
            lblLoanIssuingDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate today = LocalDate.from(LocalDateTime.now());
            LocalDate nextInstallmentDay = today.plusMonths(1);
            lblPaymentDate.setText(String.valueOf(nextInstallmentDay));
            lblLoanType.setText("Instant Loan");
            lblInstallmentPrice.setText(df.format(loanByDeposit.getMonthlyInstallment()));
        }

        summeryPane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(800));
        ft.setNode(summeryPane);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.playFromStart();

    }
    public void cancelButtonOnAction(ActionEvent actionEvent) {
    }

    public void onKeyReleased(KeyEvent keyEvent) {
    }

    public void searchAccount(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        deepSearchMethod();
    }

    public void deepSearchMethod() throws SQLException, ClassNotFoundException {
        txtLoanAmount.setText(null);
        txtLoanNumber.setText(null);
        txtPaymentDate.setText(null);
        txtDebtorName.setText(null);
        txtInstallment.setText(null);

        instantLoanModel = null;
        loanByDeposit = null;
        rationLoanModel = null;
        if (!txtAccountNumberSearch.getText().isEmpty()) {
            if (new CustomerDetailsController().checkAccountNumberIsExist(txtAccountNumberSearch.getText())) {
                setDataToComboBox(txtAccountNumberSearch.getText());
            } else {
                ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR", Alert.AlertType.ERROR, "ERROR", "Invalid Account Number");
                alertBox.showAlert();
            }
        } else {
            ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR", Alert.AlertType.ERROR, "ERROR", "Invalid Account Number");
            alertBox.showAlert();
        }
    }

    public void onKeyReleasedAction(KeyEvent keyEvent) {

    }

    public void doneButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        // * Here goes the payment functions for this.
        // * 1.0 Installment should set installment=installment-1;
        // * Calculate the interest and the set to the investment table.
        // *

        // * make new loan model and send to update.
        if (cmbLoanTypes.getValue().equals("Instant Loan")) {
            if (instantLoanModel.getInstallmentsToBePaid()==1) {
                // * Here goes the code set for setting the customer status loan out and loan model loan active status completed.
                if (new InstantLoanController().setLoanStatusOver(instantLoanModel.getAccountNumber(),instantLoanModel.getiLoanNumber())){
                    ModifiedAlertBox alertBox = new ModifiedAlertBox(
                            "Done!",
                            Alert.AlertType.INFORMATION,
                            "Done!",
                            "Loan is Completed!"
                    );
                    alertBox.showAlert();
                    return;
                }else {
                    System.out.println();
                }
            }
            // * Save instant loan.
            InstantLoanPayModel model = new InstantLoanPayModel(
                    instantLoanModel.getiLoanNumber(),
                    instantLoanModel.getAccountNumber(),
                    instantLoanModel.getiNumberOfInstallments(),
                    instantLoanModel.getiLoanPaidAmount() + instantLoanModel.getiMonthlyInstallment(),
                    new LoanInstallmentCalculator().getNextInstallmentDate(),
                    instantLoanModel.getiLoanAmount(),
                    instantLoanModel.getiMonthlyInstallment(),
                    instantLoanModel.getInstallmentsToBePaid() - 1
            );


            if (new InstantLoanController().updateLoanInfo(model)) {

                ModifiedAlertBox alertBox = new ModifiedAlertBox(
                        "Done!",
                        Alert.AlertType.INFORMATION,
                        "Done!",
                        "Payment Successful!"
                );
                alertBox.showAlert();
            }

            // * Loan By Deposit
        } else if (cmbLoanTypes.getValue().equals("Loan By Deposit")) {

            // * If loan is over means the installment = 1, then this blelo code will run,
            if (loanByDeposit.getInstallmentsToBePaid()==1) {
                // * Here goes the code set for setting the customer status loan out and loan model loan active status completed.
                if (new LoanByDepositController().setLoanStatusOver(loanByDeposit.getLoanNumber(),loanByDeposit.getAccountNumber(),loanByDeposit.getLoanAmount())){
                    boolean holdedMoneyReturn = new OnHoldDetailController().updateOnHoldDetailMinus(loanByDeposit.getAccountNumber(),loanByDeposit.getLoanAmount());
                    boolean accountReset = new SavingsAccountController().updateSavingsForDepositTransaction(loanByDeposit.getAccountNumber(),loanByDeposit.getLoanAmount());
                    ModifiedAlertBox alertBox = new ModifiedAlertBox(
                            "Done!",
                            Alert.AlertType.INFORMATION,
                            "Done!",
                            "Loan is Completed!"
                    );
                    alertBox.showAlert();
                }else {
                    System.out.println();
                }
            }
            // --------------------

            LoanByDepositPayModel model = new LoanByDepositPayModel(
                    loanByDeposit.getLoanNumber(),
                    loanByDeposit.getAccountNumber(),
                    loanByDeposit.getNumberOfInstallments(),
                    loanByDeposit.getLoanPaidAmount() + loanByDeposit.getMonthlyInstallment(),
                    new LoanInstallmentCalculator().getNextInstallmentDate(),
                    loanByDeposit.getLoanAmount(),
                    loanByDeposit.getMonthlyInstallment(),
                    loanByDeposit.getInstallmentsToBePaid() - 1
            );

            if (new LoanByDepositController().updateLoanInfo(model)) {

                ModifiedAlertBox alertBox = new ModifiedAlertBox(
                        "Done!",
                        Alert.AlertType.INFORMATION,
                        "Done!",
                        "Payment Successful!"
                );
                alertBox.showAlert();
            } else {

            }
        }else if (cmbLoanTypes.getValue().equals("Ration Loan")){
            if (rationLoanModel.getInstallmentsToBePaid()==1) {
                // * Here goes the code set for setting the customer status loan out and loan model loan active status completed.
                if (new RationLoanController().setLoanStatusOver(rationLoanModel.getAccountNumber(),rationLoanModel.getLoanNumber())){
                    ModifiedAlertBox alertBox = new ModifiedAlertBox(
                            "Done!",
                            Alert.AlertType.INFORMATION,
                            "Done!",
                            "Loan is Completed!"
                    );
                    alertBox.showAlert();
                    return;
                }else {
                    System.out.println();
                }
            }
            RationLoanPayModel model = new RationLoanPayModel(
                    rationLoanModel.getLoanNumber(),
                    rationLoanModel.getAccountNumber(),
                    rationLoanModel.getNumberOfInstallments(),
                    rationLoanModel.getLoanPaidAmount() + rationLoanModel.getMonthlyInstallment(),
                    new LoanInstallmentCalculator().getNextInstallmentDate(),
                    rationLoanModel.getLoanAmount(),
                    rationLoanModel.getMonthlyInstallment(),
                    rationLoanModel.getInstallmentsToBePaid() - 1
            );

            if (new RationLoanController().updateLoanInfo(model)) {

                ModifiedAlertBox alertBox = new ModifiedAlertBox(
                        "Done!",
                        Alert.AlertType.INFORMATION,
                        "Done!",
                        "Payment Successful!"
                );
                alertBox.showAlert();
            } else {

            }
        }
    }
}
