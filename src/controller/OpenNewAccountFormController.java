package controller;

import com.jfoenix.controls.JFXToggleButton;
import controller.components.NumberGenerator;
import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import model.CustomerModel;
import model.OpenAccDepMoneyModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OpenNewAccountFormController {

    public TextField txtAccountNumber;
    public RadioButton radioMale;
    public RadioButton radioFemale;
    public String sex = "Male";
    public ComboBox<String> cmbAccountType;
    public TextField txtTelephone;
    public TextField txtEmail;
    public DatePicker pickerBirthday;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtNIC;
    public JFXToggleButton toggleDeposit;
    public TextField txtAccNumber;
    public TextField txtDescription;
    public TextField txtAmount;
    public AnchorPane openNewAccountContext;

    public void initialize(){
        // * Setting data to fields.
        setDataToFields();

        // * Setting the account number
        setAccountNumber();

        // * Adding values for the Account Type combo box.
            cmbAccountType.getItems().add("Savings Account");

        // * Overriding the date picker date format.
            datePickerFormat();

        // * Adding Sex radio buttons to toggle group. Default selection [Male].
        ToggleGroup radioGroup = new ToggleGroup();
        radioMale.setToggleGroup(radioGroup);
        radioFemale.setToggleGroup(radioGroup);

        // * Listener on deposit toggle. Toggled between deposit form enabled/disabled.
        toggleDeposit.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                disableDepositForm();
            }else{
                enableDepositForm();
            }
        });

    }

    private void setDataToFields() {
        CustomerModel customer = ObjectPasser.getCustomerModel();
        OpenAccDepMoneyModel depositModel = ObjectPasser.getDepositModel();

        if (customer!=null){
            txtAccNumber.setText(customer.getAccountNumber());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getCustomerAddress());
            pickerBirthday.getEditor().setText(customer.getCustomerBirthday());
            if (customer.getSex().equals("Male")){
                radioMale.selectedProperty().set(true);
            }else {
                radioFemale.selectedProperty().set(true);
            }
            cmbAccountType.setValue("Savings Account");
            txtNIC.setText(customer.getCustomerNIC());
            txtTelephone.setText(customer.getTelephoneNumber());
            txtEmail.setText(customer.getCustomerEmail());
        }

        if (depositModel!=null){
            txtAmount.setText(String.valueOf(depositModel.getAmount()));
            txtDescription.setText(depositModel.getDescription());
            txtAccNumber.setText(depositModel.getAccountNumber());
            toggleDeposit.selectedProperty().set(false);
        }else{
            toggleDeposit.selectedProperty().set(true);
            disableDepositForm();
        }
    }

    private void datePickerFormat() {
        /*
            --> This method will change the birthday picker date format.
         */

        // * Implementing formatter for the date.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

    public boolean validateFields(){
        /*
            --> This method will validate the all the text fields and there
                data types or format.
            --> Return the string, if all fields are fine returns -> "done"
         */

        // * If emptyFields = true; then system wait to fill other them.
        boolean emptyFields = false;

        String emptyStyle = "-fx-border-color:red";

        String normalFieldStyle = "-fx-border-color:#b5b5b5";


        // * Name Field
        if (txtName.getText().isEmpty()){
            emptyFields = true;
            txtName.setStyle(emptyStyle);
        }else{
            txtName.setStyle(normalFieldStyle);
        }

        // * Address Field
        if (txtAddress.getText().isEmpty()){
            emptyFields = true;
            txtAddress.setStyle(emptyStyle);
        }else{
            txtAddress.setStyle(normalFieldStyle);
        }

        // * Birthday and it's format, data type.
        if (pickerBirthday.getEditor().getText().isEmpty()){
            emptyFields = true;
            pickerBirthday.setStyle(emptyStyle);
        }else if (!(pickerBirthday.getEditor().getText().matches("\\d{2}-\\d{2}-\\d{4}"))){
            emptyFields = true;
            pickerBirthday.setStyle(emptyStyle);
        }else{
            pickerBirthday.setStyle(normalFieldStyle);
        }

        // * Account Type selected or not
        if (cmbAccountType.getSelectionModel().isEmpty()){
            emptyFields = true;
            cmbAccountType.setStyle(emptyStyle);
        }else{
            cmbAccountType.setStyle(normalFieldStyle);
        }

        // * NIC
        if (txtNIC.getText().isEmpty()){
            emptyFields = true;
            txtNIC.setStyle(emptyStyle);
        }else{
            txtNIC.setStyle(normalFieldStyle);
        }

        // * Telephone Number
        if (txtTelephone.getText().isEmpty()){
            emptyFields = true;
            txtTelephone.setStyle(emptyStyle);
        }else{
            txtTelephone.setStyle(normalFieldStyle);
        }

        // * E-mail
        if (txtEmail.getText().isEmpty()){
            emptyFields = true;
            txtEmail.setStyle(emptyStyle);
        }else{
            txtEmail.setStyle(normalFieldStyle);
        }

        return emptyFields;
    }

    private void setAccountNumber() {
        if (txtAccountNumber.getText().isEmpty()) {
            // * Generating the account number
            String accountNumber = new NumberGenerator().generateNumber();

            // * Setting to the field.
            txtAccountNumber.setText(accountNumber);
        }
    }

    public void setSexSelection(ActionEvent actionEvent){
        // * This method will set the selected sex value for the String 'sex'.
        if (radioMale.isSelected()){
            sex = "Male";
        }else{
            sex = "Female";
        }
    }

    public void openAccountButtonOnAction(ActionEvent actionEvent) throws ParseException, IOException, SQLException, ClassNotFoundException {
        /*
            --> This method will validate the all the fields and parse to the
                method that save the data to the tables.
         */

        // * If all fields are filled, then start the saving process.
        if (!validateFields()){
                // * Calculate the age by given birthday.
                int age = calculateAge(pickerBirthday.getEditor().getText());

                // * checking the opening deposit
                if (depositSection()){
                    createAccount(actionEvent);
                }else{
                 ModifiedAlertBox alertBox = new ModifiedAlertBox(
                         "Failed!",
                         Alert.AlertType.WARNING,
                         "ERROR!",
                         "Please fill all the fields."
                 );
                 alertBox.showAlert();

                }

            }else {
             // * Alter box shows that the "Please fill all the fields"
            ModifiedAlertBox alert = new ModifiedAlertBox(
                    "Warning!",
                    Alert.AlertType.WARNING,
                    "ERROR!",
                    "Please fill all the fields."
            );
            alert.showAlert();

        }

    }

    private boolean depositSection() {
        /*
            --> This method will validate the deposit form and
                returns the true or false.
         */


        if (toggleDeposit.isSelected()){
            return true;

        }else {
            System.out.println("Validator");
            FormFieldValidator validator = new FormFieldValidator(
                    txtAccNumber,
                    txtAmount,
                    txtDescription
            );
            validator.filledFieldProperties("-fx-border-color:#b5b5b5");
            validator.emptyFieldProperties("-fx-border-color:red");
            return !validator.checkEmptyFields() && txtAccNumber.getText().equals(
                    txtAccountNumber.getText()
            );

        }

    }

    private void createAccount(ActionEvent actionEvent) throws ParseException, IOException {
        /*
            --> This method will make Customer model and as well as if open with the
                opening deposit, then make the depositMoney model.
         */

        // * Make new Customer Model.
        CustomerModel customerModel = new CustomerModel(
                txtAccountNumber.getText(),
                txtName.getText(),
                calculateAge(pickerBirthday.getEditor().getText()),
                radioMale.isSelected()? "Male" : "Female",
                cmbAccountType.getValue(),
                txtAddress.getText(),
                txtTelephone.getText(),
                pickerBirthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                txtNIC.getText(),
                txtEmail.getText(),
                null,
                null,
                null
        );

        // * If account create with deposit, then this make model of transaction.
        OpenAccDepMoneyModel depositModel = null;
        if (!toggleDeposit.isSelected()){
            System.out.println("Not Disabled");
            depositModel = new OpenAccDepMoneyModel(
                    new NumberGenerator().getTransactionID(),
                    txtAccountNumber.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtAmount.getText()),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
            );
        }else{
            System.out.println("Disabled.");
        }

        // * Parsing customer and deposit model to parser to get in the confirm form.
        ObjectPasser.setModels(customerModel,depositModel);

        // * Opening confirm details form.
        URL resource = getClass().getResource("../view/ConfirmOpenAccount.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        openNewAccountContext.getChildren().clear();
        openNewAccountContext.getChildren().add(load);

    }

    private void disableDepositForm() {
        txtAccNumber.setDisable(true);
        txtAmount.setDisable(true);
        txtDescription.setDisable(true);
        txtAccNumber.setText("");
        txtAmount.setText("");
        txtDescription.setText("");
    }

    private void enableDepositForm() {
        txtAccNumber.setDisable(false);
        txtAmount.setDisable(false);
        txtDescription.setDisable(false);
    }


    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        openNewAccountContext.getChildren().clear();
        openNewAccountContext.getChildren().add(load);
    }
}
