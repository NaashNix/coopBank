package controller;

import com.jfoenix.controls.JFXComboBox;
import controller.dbControllers.LoanDetailsController;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.LoanDetailsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoanDetailsFormController {
    public AnchorPane loanDetailsContext;
    public Pane UserVerificationPane;
    public TextField txtMinimumBalance;
    public TextField txtInterestType;
    public TextField txtInterest;
    public TextField txtNoMaxInstallments;
    public ComboBox<String> cmbForWhom;
    public CheckBox tickMinimumBalance;
    public TextField txtAccMaturity;
    public ComboBox<String> cmbInterestCalPeriod;
    public CheckBox tickFromDeposit;
    public CheckBox tickLegalAction;
    public TextField txtLoanAmount;
    public TextField txtLoanCode;
    public TextField txtLoanType;
    public JFXComboBox<String> cmbLoanType;
    public ArrayList<LoanDetailsModel> loanDetails;
    public TextField txtName;

    public void initialize() throws SQLException, ClassNotFoundException {
        // * Setting a data to fields.
        setDataToComboBox();

        // * Adding Listener to CmbLoanType.
        cmbLoanType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDataToFields(newValue);
        });

        // * Set Styles to needed components.
        tickFromDeposit.setStyle("-fx-opacity: 1");
        tickLegalAction.setStyle("-fx-opacity: 1");
        tickMinimumBalance.setStyle("-fx-opacity: 1");
        cmbForWhom.setStyle("-fx-opacity: 1");
        cmbInterestCalPeriod.setStyle("-fx-opacity: 1");
    }

    private void setDataToFields(String newValue) {
        if (newValue.equals("Instant Loan")){
            for (int i = 0; i < loanDetails.size(); i++){
                if ("Instant Loan".equals(loanDetails.get(i).getLoanName())){
                    setData(loanDetails.get(i));
                }
            }
        }
    }

    private void setData(LoanDetailsModel loanDetailsModel) {
        txtName.setText(loanDetailsModel.getLoanName());
        txtAccMaturity.setText(String.valueOf(loanDetailsModel.getMinAccountMaturity())+" Months");
        txtInterest.setText(String.valueOf(loanDetailsModel.getInterest())+" %");
        txtLoanAmount.setText("Rs. "+String.valueOf(loanDetailsModel.getMaximumLoanAmount()));
        txtInterestType.setText(loanDetailsModel.getInterestType());
        txtNoMaxInstallments.setText(String.valueOf(loanDetailsModel.getMaximumNoOfInstallments()));
        txtMinimumBalance.setText(String.valueOf(loanDetailsModel.getMinimumAccountBalance()));
        txtLoanType.setText("not specified");
        txtLoanCode.setText(loanDetailsModel.getLoanCode());
        cmbForWhom.setValue(loanDetailsModel.getForWhom());
        if (loanDetailsModel.getMinimumAccountBalance()!=0){
            tickMinimumBalance.setSelected(true);
        }
        if (loanDetailsModel.getNonPayOption1() != null){
            tickFromDeposit.setSelected(true);
        }

        if (loanDetailsModel.getNonPayOption2() != null){
            tickLegalAction.setSelected(true);
        }

        cmbInterestCalPeriod.setValue(loanDetailsModel.getInterestCalPeriod());

    }

    private void setDataToComboBox() throws SQLException, ClassNotFoundException {
        System.out.println("Set Data To Loan Types");
        loanDetails = new LoanDetailsController().getLoanDetails();
        for (LoanDetailsModel model  : loanDetails
             ) {
            System.out.println("Name : "+model.getLoanName());
            cmbLoanType.getItems().add(model.getLoanName());
        }

    }
}