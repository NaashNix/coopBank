/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import model.CustomerModel;
import model.InstantLoanModel;
import model.LoanByDeposit;
import model.RationLoanModel;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class
SearchAccountResultController {

    public TextField txtAccountNumberShower;
    public TextField txtAccNumberInDetails;
    public TextField txtAccType;
    public TextField txtName;
    public TextField txtNIC;
    public TextField txtTelephone;
    public TextField txtAddress;
    public DatePicker pickerBirthday;
    public Button btnEditOrDone;
    public CustomerModel model;
    public boolean editButtonAction = false;
    public Rectangle SQSearchIcon;
    public ImageView imgSearchIcon;
    public Label lblAccountBalance;
    public DecimalFormat df=new DecimalFormat("#.##");
    public JFXComboBox<String> cmbAccountType;
    public Label lblOnHold;
    public Pane paneInstantLoan;
    public Pane paneRationLoan;
    public Pane paneLoanByDeposit;
    public Label instantLoanNextInstallment;
    public Label instantLoanNDate;
    public Label rationLoanNextInstallment;
    public Label rationLoanNDate;
    public Label loanByDepNextIns;
    public Label loanByDepositNDate;
    Pattern namePattern = Pattern.compile("^[a-zA-Z ]*$");
    Pattern moneyPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern idPattern = Pattern.compile("^[0-9]{12}$");
    Pattern telephonePattern = Pattern.compile("^[0-9]{10}$");
    Pattern emailPattern = Pattern.compile("^[A-z0-9]{3,}[@][a-z]{2,}[.](com|lk|uk|[a-z]{2,})$");
    LinkedHashMap<TextField,Pattern> hashMap;





    public void initialize() throws SQLException, ClassNotFoundException {
        findAccount();
        datePickerFormat();

        // * CMB
        cmbAccountType.setValue("Savings Account");

        // * Set data to hashmap
        hashMap = new LinkedHashMap<TextField, Pattern>();
        hashMap.put(txtName, namePattern);
        hashMap.put(txtNIC, idPattern);
        hashMap.put(txtTelephone, telephonePattern);
    }

    private void datePickerFormat() {
        /*
            --> This method will change the birthday picker date format.
         */

            // * Implementing formatter for the date.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            pickerBirthday.setConverter(new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate date) {
                    if (date!= null){
                        return formatter.format(date);
                    }else{
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    } else {
                        return null;
                    }
                }
            });
        }
    private void findAccount() throws SQLException, ClassNotFoundException {
        String accountNumber = ObjectPasser.getAccountNumber();
        model = new CustomerDetailsController().getCustomerDetails(accountNumber);
        if (model!=null){
            ObjectPasser.setAccountNumber(null);
            setDataToFields(model);
        }else{
            System.out.println("Customer Model Not Found!");
        }
    }

    private void setDataToFields(CustomerModel model) throws SQLException, ClassNotFoundException {
        txtAccountNumberShower.setText(model.getAccountNumber());
        pickerBirthday.getEditor().setText(model.getCustomerBirthday());
        txtAccType.setText(model.getAccountType());
        txtAccNumberInDetails.setText(model.getAccountNumber());
        txtAddress.setText(model.getCustomerAddress());
        txtNIC.setText(model.getCustomerNIC());
        txtName.setText(model.getName());
        txtTelephone.setText(model.getTelephoneNumber());
        pickerBirthday.getEditor().setDisable(true);
        pickerBirthday.getEditor().setOpacity(1);
        double accountBalance = new SavingsAccountController().getAccountBalance(model.getAccountNumber());
        lblAccountBalance.setText("Rs. "+df.format(accountBalance));
        double onHoldAmount = new OnHoldDetailController().getTheHoldedAmount(model.getAccountNumber());
        lblOnHold.setText(df.format(onHoldAmount));

        loadLoanDetails(model);

    }

    private void loadLoanDetails(CustomerModel model) throws SQLException, ClassNotFoundException {
        if (model.getRationLoan()!=null){
            if (!model.getRationLoan().equals("NULL")) {
                RationLoanModel rationLoan = new RationLoanController().
                        getRationLoanModelByID(model.getAccountNumber());
                rationLoanNDate.setText(String.valueOf(rationLoan.getNextInstallmentDate()));
                rationLoanNextInstallment.setText("Rs. " + rationLoan.getMonthlyInstallment());
                paneRationLoan.setVisible(true);
            }
        }

        if (model.getInstantLoan()!=null){
            if (!model.getInstantLoan().equals("NULL")) {
                InstantLoanModel instantLoan = new InstantLoanController().getLoanByNumber(
                        model.getAccountNumber()
                );
                instantLoanNDate.setText(String.valueOf(instantLoan.getNextInstallmentDate()));
                instantLoanNextInstallment.setText("Rs. " + instantLoan.getiMonthlyInstallment());
                paneInstantLoan.setVisible(true);
            }
        }

        if (model.getLoanByDeposit()!=null) {
            if (!model.getLoanByDeposit().equals("NULL")) {
                LoanByDeposit loanByDeposit = new LoanByDepositController().getLoanByNumber(
                        model.getAccountNumber()
                );
                loanByDepositNDate.setText(String.valueOf(loanByDeposit.getNextInstallmentDate()));
                loanByDepNextIns.setText("Rs. " + loanByDeposit.getMonthlyInstallment());
                paneLoanByDeposit.setVisible(true);
            }
        }
    }


    public void BtnEditOrDoneOnAction(ActionEvent actionEvent) throws SQLException, ParseException, ClassNotFoundException {
        EventHandler<MouseEvent> handler = MouseEvent::consume; // * This is for the remove and add filer methods.
        if (!editButtonAction){
             // * If the button says edit, then it runs this code.
            editButtonAction = true;
            btnEditOrDone.setText("Done!");
            // * Will disable and remove the all the methods in the image search and square as well as text.
            txtAccountNumberShower.setDisable(true);
            imgSearchIcon.removeEventFilter(MouseEvent.ANY,handler);
            SQSearchIcon.removeEventFilter(MouseEvent.ANY,handler);

            FormFieldValidator validator = new FormFieldValidator(
                    txtAddress,txtName,txtNIC,txtTelephone
            );
            validator.setFieldsTextReadyToEdit();
            pickerBirthday.setDisable(false);

        }else{
            FormFieldValidator validator = new FormFieldValidator(
                    txtAddress,txtTelephone,txtNIC,txtName
            );
            validator.filledFieldProperties("-fx-border-color:#b5b5b5");
            validator.emptyFieldProperties("-fx-border-color:red");
            if (!validator.checkEmptyFields()){
                if (new FormFieldValidator().validate(hashMap)) {
                    saveDataToDatabase();
                    // * Below codes set the default settings when after the edited model is saved.
                    btnEditOrDone.setText("Edit");
                    validator.setEditableFalse();
                    txtAccountNumberShower.setDisable(false);
                    imgSearchIcon.addEventFilter(MouseEvent.ANY, handler);
                    pickerBirthday.setDisable(true);
                    editButtonAction = false;
                }else {
                    return;
                }
            }


        }
    }


    public int calculateAge(String birthday) throws ParseException {

        // * //Instantiating the SimpleDateFormat class.
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        // * Parsing the given String to Date object
        Date date = formatter.parse(birthday);

        // * Converting obtained Date object to LocalDate object.
        Instant instant = date.toInstant();
        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
        LocalDate givenDate = zone.toLocalDate();

        Period period = Period.between(givenDate,LocalDate.now());
        return period.getYears();
    }

    private void saveDataToDatabase() throws ParseException, SQLException, ClassNotFoundException {
        CustomerModel editedModel = new CustomerModel(
                model.getAccountNumber(),
                txtName.getText(),
                calculateAge(pickerBirthday.getEditor().getText()),
                model.getSex(),
                model.getAccountType(),
                txtAddress.getText(),
                txtTelephone.getText(),
                pickerBirthday.getEditor().getText(),
                model.getJoinedDate(),
                txtNIC.getText(),
                model.getCustomerEmail(),
                model.getRationLoan(),
                model.getLoanByDeposit(),
                model.getInstantLoan()
        );

        if (new CustomerDetailsController().updateDetails(editedModel)){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.CONFIRMATION,"Successful","Details are updated!");
            alertBox.showAlert();
        }
    }

    public void searchAccount(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (txtAccountNumberShower.getText().isEmpty()){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Empty Field!", Alert.AlertType.ERROR,"ERROR!","Invalid Account Number");
            alertBox.showAlert();
        }else if (new CustomerDetailsController().checkAccountNumberIsExist(txtAccountNumberShower.getText())){
            paneInstantLoan.setVisible(false);
            paneLoanByDeposit.setVisible(false);
            paneRationLoan.setVisible(false);
            model = new CustomerDetailsController().getCustomerDetails(txtAccountNumberShower.getText());
            setDataToFields(model);
        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox("Invalid!", Alert.AlertType.ERROR,"ERROR!","Invalid Account Number");
            alert.showAlert();
        }
    }

    public void onKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode()== KeyCode.ENTER){
            MouseEvent event = null;
            searchAccount(event);
        }
    }

    public void onKeyReleasedAction(KeyEvent keyEvent){
        FormFieldValidator validator = new FormFieldValidator();
        TextField currentTextField = (TextField) keyEvent.getSource();
        Pattern patternToPass = null;
        switch (currentTextField.getId()){
            case "txtName" : {patternToPass=namePattern; break;}
            case "txtNIC" : {patternToPass=idPattern; break;}
            case "txtTelephone" : {patternToPass=telephonePattern; break;}
            case "txtAmount" : {patternToPass=moneyPattern; break;}
        }
        if (patternToPass != null) {
            validator.validateSingle(currentTextField,patternToPass);
        }
    }

    public void addressKeyReleased(KeyEvent keyEvent) {
        if (txtAddress.getText().isEmpty()){
            txtAddress.setStyle("-fx-border-color:red");
        }else{
            txtAddress.setStyle("-fx-border-color:green");
        }
    }
}
