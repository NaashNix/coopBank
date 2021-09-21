package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.NumberGenerator;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.SavingsAccountController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.DepositObjectModel;
import model.WithdrawObjectModel;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    private double numOne;



    public void initialize(){




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
            double accountBalance = new SavingsAccountController().getAccountBalance(txtSearchWithdraw.getText());
            return Double.parseDouble(txtWithdrawAmount.getText() + 100) <= accountBalance;
        }else{
            return false;
        }

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
            txtAccBalance.setText(String.valueOf(new SavingsAccountController().getAccountBalance(txtSearchWithdraw.getText())));
        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox("Invalid!", Alert.AlertType.ERROR,"ERROR!","Invalid number!");
            alert.showAlert();
        }
    }
}
