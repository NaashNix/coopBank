/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import model.CustomerModel;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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


    public void initialize() throws SQLException, ClassNotFoundException {
        findAccount();
        datePickerFormat();
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

    private void setDataToFields(CustomerModel model) {
        txtAccountNumberShower.setText(model.getAccountNumber());
        pickerBirthday.getEditor().setText(model.getCustomerBirthday());
        txtAccType.setText(model.getAccountType());
        txtAccNumberInDetails.setText(model.getAccountNumber());
        txtAddress.setText(model.getCustomerEmail());
        txtNIC.setText(model.getCustomerNIC());
        txtName.setText(model.getName());
        txtTelephone.setText(model.getTelephoneNumber());
        pickerBirthday.getEditor().setDisable(true);
        pickerBirthday.getEditor().setOpacity(1);
    }


    public void BtnEditOrDoneOnAction(ActionEvent actionEvent) throws SQLException, ParseException, ClassNotFoundException {
        EventHandler<MouseEvent> handler = MouseEvent::consume; // * This is for the remove and add filer methods.
        if (!editButtonAction){
             // * If the button says edit, then it runs this code.
            editButtonAction = true;
            btnEditOrDone.setText("Done!");
            // * Will disable and remove the all the methods in the image search and equare as well as text.
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
                saveDataToDatabase();
                // * Below codes set the default settings when after the edited model is saved.
                btnEditOrDone.setText("Edit");
                validator.setEditableFalse();
                txtAccountNumberShower.setDisable(false);
                imgSearchIcon.addEventFilter(MouseEvent.ANY,handler);
                pickerBirthday.setDisable(true);
                editButtonAction = false;
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
            model = new CustomerDetailsController().getCustomerDetails(txtAccountNumberShower.getText());
            setDataToFields(model);
        }else{
            ModifiedAlertBox alert = new ModifiedAlertBox("Invalid!", Alert.AlertType.ERROR,"ERROR!","Invalid Account Number");
            alert.showAlert();
        }
    }
}
