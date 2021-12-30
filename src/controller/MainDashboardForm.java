package controller;

import controller.components.*;
import controller.dbControllers.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class MainDashboardForm {

    public Text calDisplay;
    public String operator = "";
    public boolean start = true;
    public AnchorPane mainDashboardForm;
    public TextField txtDepositSearch;
    public TextField txtDepositName;
    public TextField txtDepositAmount;
    public TextField txtDepositDescription;
    public TextField txtSearchWithdraw;
    public TextField txtWithdrawAmount;
    public TextField txtWithdrawName;
    public TextField txtWithdrawDesc;
    public TextField txtAccBalance;
    public Label lblMainBalance;
    public Label lblMainBalTime;
    public TextField txtAccountNumberSearch;
    private double numOne;
    public DecimalFormat df=new DecimalFormat("#.##");
    Pattern moneyPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern mainMoneyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern singleDecimal = Pattern.compile("^[1-9][0-9]*([.][0-9]{1})?$");



    public void initialize() throws SQLException, ClassNotFoundException {
        // * Enter key listener to the deposit account number field.
        txtDepositSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.ENTER){
                    ActionEvent actionEvent = null;
                    try {
                        btnDepositSearchOnAction(actionEvent);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        txtSearchWithdraw.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.ENTER){
                    ActionEvent actionEvent = null;
                    try {
                        withdrawFindButtonOnAction(actionEvent);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        // * Re-arranging amount field.
        txtDepositAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!txtDepositAmount.getText().isEmpty()){
                    Pattern pattern = Pattern.compile("^[1-9][0-9]*$");
                    if (pattern.matcher(txtDepositAmount.getText()).matches()) {
                        txtDepositAmount.setText(txtDepositAmount.getText()+".00");
                    }
                }
            }
        });

        txtWithdrawAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!txtWithdrawAmount.getText().isEmpty()){
                    Pattern pattern = Pattern.compile("^[1-9][0-9]*$");
                    if (pattern.matcher(txtWithdrawAmount.getText()).matches()) {
                        txtWithdrawAmount.setText(txtWithdrawAmount.getText()+".00");
                    }
                }
            }
        });

        // * Updating Main balance
        double mainBalance = new MoneyJournalController().getBalances("Main Balance");
        String finalBalance = null;
        if (mainMoneyPattern.matcher(String.valueOf(mainBalance)).matches()){
            finalBalance = mainBalance+".00";
        }else if (singleDecimal.matcher(String.valueOf(mainBalance)).matches()){
            finalBalance = mainBalance+"0";
        }else{
            finalBalance = String.valueOf(mainBalance);
        }
        lblMainBalance.setText("Rs. "+finalBalance);
        lblMainBalTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));

    }

    public void processCalKey(ActionEvent actionEvent){
        if (start){
            calDisplay.setText("");
            start = false;
        }
        String value = ((Button) actionEvent.getSource()).getText();
        calDisplay.setText(calDisplay.getText() + value);

    }

    public void processCalOperators(ActionEvent actionEvent){
        String value = ((Button) actionEvent.getSource()).getText();
            if (!value.equals("=")){
                if (!operator.isEmpty())
                    return;

                operator = value;
                numOne = Double.parseDouble(calDisplay.getText());
                calDisplay.setText("");

            }else{
                if (operator.isEmpty()) return;
                double numTwo = Double.parseDouble(calDisplay.getText());
                double result = calculator(numOne, numTwo, operator);
                calDisplay.setText(String.valueOf(result));
                operator = "";
                start = true;
            }

    }

    private double calculator(double numOne, double numTwo, String operator) {
        switch(operator){
            case "+" : {
                return numOne + numTwo;
            }
            case "-" : {
                return numOne - numTwo;
            }
            case "x" : {
                return numOne*numTwo;
            }
            case "/" : {
                if (numTwo==0){
                    return 0;
                }
                return numOne/numTwo;
            }
            case "%" : {
                return (numOne*100)/numTwo;
            }
            default: {
                return 0;
            }
        }
    }

    public void depositFormOnAction(ActionEvent actionEvent) throws IOException {
        /*
            --> This button will lead the deposit details to confirmation page.
         */

        FormFieldValidator validator = new FormFieldValidator(
                txtDepositAmount,txtDepositDescription,txtDepositSearch
        );
        validator.filledFieldProperties("-fx-border-color:#b5b5b5");
        validator.emptyFieldProperties("-fx-border-color:red");
        if (!validator.checkEmptyFields()){
            if (!moneyPattern.matcher(txtDepositAmount.getText()).matches()){
                return;
            }
            DepositObjectModel model = new DepositObjectModel(
                    txtDepositSearch.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:ss:mm")),
                    Double.parseDouble(txtDepositAmount.getText()),
                    txtDepositDescription.getText(),
                    new NumberGenerator().getTransactionID()
                    );
            ObjectPasser.setDepositObjectModel(model);
            URL resource = getClass().getResource("../view/DepositForm.fxml");
            System.out.println(resource);
            assert resource != null;
            Parent load = FXMLLoader.load(resource);
            mainDashboardForm.getChildren().clear();
            mainDashboardForm.getChildren().add(load);
        }


    }

    public void withdrawFormOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        /*
            --> This method will lead the withdraw details to the confirmation page.
         */

        FormFieldValidator validator = new FormFieldValidator(
                txtWithdrawAmount,txtWithdrawDesc,txtSearchWithdraw
        );
        validator.filledFieldProperties("-fx-border-color:#b5b5b5");
        validator.emptyFieldProperties("-fx-border-color:red");

        if (!validator.checkEmptyFields()){
            if (checkAreThereSufficientBalance()){
                WithdrawObjectModel model = new WithdrawObjectModel(
                        txtSearchWithdraw.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:ss:mm")),
                        Double.parseDouble(txtWithdrawAmount.getText()),
                        txtWithdrawDesc.getText(),
                        new NumberGenerator().getWithdrawalID()
                );
                ObjectPasser.setWithdrawObjectModel(model);
                URL resource = getClass().getResource("../view/WithdrawForm.fxml");
                System.out.println(resource);
                assert resource != null;
                Parent load = FXMLLoader.load(resource);
                mainDashboardForm.getChildren().clear();
                mainDashboardForm.getChildren().add(load);
            }else{
                ModifiedAlertBox alertBox = new ModifiedAlertBox(
                        "Insufficient Balance", Alert.AlertType.ERROR,"ERROR!","Insufficient account balance"
                );
                alertBox.showAlert();
            }

        }

    }

    public boolean checkAreThereSufficientBalance() throws SQLException, ClassNotFoundException {
        boolean existenceInSavingsAccount = new SavingsAccountController().checkRecordIsExist(txtSearchWithdraw.getText());
        if (existenceInSavingsAccount){
            if (checkBalanceOnHand()) {
                double accountBalance = new SavingsAccountController().getAccountBalance(txtSearchWithdraw.getText());
                if (!(Double.parseDouble(txtWithdrawAmount.getText()) + 100 <= accountBalance)) {
                    ModifiedAlertBox alertBox = new ModifiedAlertBox(
                            "Insufficient Balance", Alert.AlertType.ERROR,"ERROR!","Insufficient account balance"
                    );
                    alertBox.showAlert();
                    return false;
                }else{
                    return true;
                }
            }else{
                ModifiedAlertBox alertBox = new ModifiedAlertBox("Out Of Main Balance", Alert.AlertType.ERROR,"ERROR!"
                        ,"Insufficient Main balance");
                alertBox.showAlert();
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean checkBalanceOnHand() throws SQLException, ClassNotFoundException {
        double totalBalance = new MoneyJournalController().getBalances("Main Balance");
        return !(Double.parseDouble(txtWithdrawAmount.getText()) > totalBalance);
    }

    public void btnDepositSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        /*
            --> This method will find the searched account and display the account holder's
                name in the field.
         */

        if (txtDepositSearch.getText().isEmpty()){
            txtDepositSearch.setStyle("-fx-border-color:red");
        }else if(new CustomerDetailsController().checkAccountNumberIsExist(txtDepositSearch.getText())){
            txtDepositSearch.setStyle("-fx-border-color:#b5b5b5");
            txtDepositName.setText(new CustomerDetailsController().getAccountHolderName(txtDepositSearch.getText()));
        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox("Invalid!", Alert.AlertType.ERROR,"ERROR!","Invalid number!");
            alert.showAlert();
        }
    }

    public void withdrawFindButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
         /*
            --> This method will find the searched account and display the account holder's
                name in the field.
         */

        if (txtSearchWithdraw.getText().isEmpty()){
            txtSearchWithdraw.setStyle("-fx-border-color:red");
        }else if(new CustomerDetailsController().checkAccountNumberIsExist(txtSearchWithdraw.getText())){
            txtSearchWithdraw.setStyle("-fx-border-color:#b5b5b5");
            txtWithdrawName.setText(new CustomerDetailsController().getAccountHolderName(txtSearchWithdraw.getText()));
            // * Updating Main balance
            double accountBalance = new SavingsAccountController().getAccountBalance(txtSearchWithdraw.getText());
            String finalBalance = null;
            if (mainMoneyPattern.matcher(String.valueOf(accountBalance)).matches()){
                finalBalance = accountBalance+".00";
            }else if (singleDecimal.matcher(String.valueOf(accountBalance)).matches()){
                finalBalance = accountBalance+"0";
            }else{
                finalBalance = String.valueOf(accountBalance);
            }
            txtAccBalance.setText(finalBalance);

        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox("Invalid!", Alert.AlertType.ERROR,"ERROR!","Invalid number!");
            alert.showAlert();
        }
    }

    public void withdrawClearButtonOnAction(ActionEvent actionEvent) {
        FormFieldValidator validator = new FormFieldValidator(
                txtWithdrawAmount,txtSearchWithdraw,txtWithdrawName,txtWithdrawDesc,txtAccBalance
        );
        validator.clearAllTextFields();
    }

    public void depositClearButtonOnAction(ActionEvent actionEvent) {
        FormFieldValidator validator = new FormFieldValidator(
                txtDepositAmount,txtDepositDescription,txtDepositSearch,txtDepositName
        );
        validator.clearAllTextFields();
    }

    public void keyReleased(KeyEvent keyEvent) {
        FormFieldValidator validator = new FormFieldValidator();
        TextField currentTextField = (TextField) keyEvent.getSource();
        validator.validateSingle(currentTextField,moneyPattern);
    }

    public void payLoanOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (!txtAccountNumberSearch.getText().isEmpty()){
            if (new CustomerDetailsController().checkAccountNumberIsExist(txtAccountNumberSearch.getText())){
                ObjectPasser.setAccountNumberForPayLoan(txtAccountNumberSearch.getText());

                // * Load paying information page.
                URL resource = getClass().getResource("../view/PayLoanInstallments.fxml");
                Parent load = FXMLLoader.load(resource);
                mainDashboardForm.getChildren().clear();
                mainDashboardForm.getChildren().add(load);

            }else{
                ModifiedAlertBox alertBox = new ModifiedAlertBox(
                        "ERROR!", Alert.AlertType.ERROR,"ERROR!","Invalid Account Number."
                );
                alertBox.showAlert();
            }
        }else{
            ModifiedAlertBox alertBox = new ModifiedAlertBox(
                    "ERROR!", Alert.AlertType.ERROR,"ERROR!","Search field is empty."
            );
            alertBox.showAlert();
        }
    }


}
