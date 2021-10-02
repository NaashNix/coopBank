/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.FormFieldValidator;
import controller.components.ModifiedAlertBox;
import controller.components.NumberGenerator;
import controller.dbControllers.ExpenditureController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.BankExpenditureModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class AddBankExpendituresController {

    public TextField txtReceiptNumber;
    public TextField txtAmount;
    public Pane summeryPane;
    public Label lblReceiptNumber;
    public Label lblExpenditure;
    public Label lblDate;
    public Label lblTime;
    public Label lblAmount;
    public TextField txtUserName;
    public TextField txtPassword;
    public ComboBox<String> cmbExpenditureTypes;
    public AnchorPane expenditureContext;
    Pattern moneyPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");


    public void initialize(){
        txtReceiptNumber.setText(new NumberGenerator().getNumberForExpenditure());

        // * Load Values for the expenditure types (combo-box)
        cmbExpenditureTypes.getItems().addAll(
                "Electricity Bill",
                "Water Bill",
                "Hospitality Expenses",
                "Telephone Bill",
                "Internet Bill",
                "Transport",
                "Other"
        );

        // *

    }


    public void cancelButtonOnAction(ActionEvent actionEvent) {
        // * Set summery pane fade out
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(summeryPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        summeryPane.setVisible(false);
        ft.playFromStart();

        // * Set fields editable
        txtAmount.setEditable(true);
        txtAmount.setEditable(true);
        cmbExpenditureTypes.setDisable(false);
    }

    public void confirmButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Date date = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        BankExpenditureModel expenditureModel = new BankExpenditureModel(
                txtReceiptNumber.getText(),cmbExpenditureTypes.getValue(),date,time,Double.parseDouble(txtAmount.getText())
        );
        if (new ExpenditureController().saveExpenditure(expenditureModel)){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Done!", Alert.AlertType.ERROR,"Done!","Successfully Saved!");
            alertBox.showAlert();
            cancelOnAction(actionEvent);
        }

    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        expenditureContext.getChildren().clear();
        expenditureContext.getChildren().add(load);

    }

    public void doneButtonOnAction(ActionEvent actionEvent) {
        if (txtAmount.getText().isEmpty()){
            alertBoxShower();
            return;
        }else if (!moneyPattern.matcher(txtAmount.getText()).matches()){
            alertBoxShower();
            return;
        }

        if (cmbExpenditureTypes.getItems().isEmpty()){
            ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR!", Alert.AlertType.ERROR,"ERROR","Select Expenditure type");
            alertBox.showAlert();
            return;
        }

        // * Going to make new expenditure model to pass to the confirmation page.
        Date date = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        BankExpenditureModel expenditureModel = new BankExpenditureModel(
                txtReceiptNumber.getText(),cmbExpenditureTypes.getValue(),date,time,Double.parseDouble(txtAmount.getText())
        );

        setDataToSummeryPane(expenditureModel);

    }

    private void setDataToSummeryPane(BankExpenditureModel expenditureModel) {
        // * Fade transition code set
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(summeryPane);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        summeryPane.setVisible(true);
        ft.playFromStart();

        // * Setting data to the labels.
        lblAmount.setText(String.valueOf(expenditureModel.getAmount()));
        lblDate.setText(String.valueOf(expenditureModel.getDate()));
        lblTime.setText(expenditureModel.getTime());
        lblReceiptNumber.setText(expenditureModel.getReceiptNumber());
        lblExpenditure.setText(expenditureModel.getDescription());

        // * Disabling editing tools
        txtAmount.setEditable(false);
        txtAmount.setEditable(false);
        cmbExpenditureTypes.setDisable(true);


    }

    public void amountKeyReleased(KeyEvent keyEvent) {
        FormFieldValidator validator = new FormFieldValidator();
        validator.validateSingle(txtAmount,moneyPattern);
    }

    public void alertBoxShower(){
        ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR!", Alert.AlertType.INFORMATION,"Invalid Amount","");
        alertBox.showAlert();
    }
}
