/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.OpenAccountObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.CustomerModel;
import model.OpenAccDepMoneyModel;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ConfirmOpenAccountController implements Initializable {
    public CustomerModel customer = new CustomerModel();
    public OpenAccDepMoneyModel deposit = new OpenAccDepMoneyModel();
    public Label txtAccNumber;
    public Label txtName;
    public Label txtAddress;
    public Label txtNIC;
    public Label txtAge;
    public Label txtTelephone;
    public Label txtAccType;
    public Label txtEmail;
    public Label txtOpeningDeposit;
    public Label txtDescription;
    public Label txtAmount;
    public Pane paneCreatingAccount;
    public ProgressBar progressBarDone;
    public Label txtStatus;
    public ImageView imgDone;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
            --> Initialize method will set the data to all the fields.
         */

        setDataToCustomerFields(OpenAccountObjectPasser.getCustomerModel());
        setDataToDepositFields(OpenAccountObjectPasser.getDepositModel());
    }

    private void setDataToDepositFields(OpenAccDepMoneyModel depositModel) {
        deposit = depositModel;
        // * This will set the deposit details to the fields
        if (depositModel!=null){
            txtOpeningDeposit.setText("YES");
            txtAmount.setText(depositModel.getAmount());
            txtDescription.setText(depositModel.getDescription());

        }else{
            // * If account open without deposit, all fields are set to N/A.
            txtOpeningDeposit.setText("NO");
            txtAmount.setText("N/A");
            txtDescription.setText("N/A");
        }
    }

    private void setDataToCustomerFields(CustomerModel customerModel) {
                customer = customerModel;
        // * This will set the data to the customer details.
            txtAccNumber.setText(customer.getAccountNumber());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getCustomerAddress());
            txtNIC.setText(customer.getCustomerNIC());
            txtAge.setText(String.valueOf(customer.getAge()));
            txtTelephone.setText(customer.getTelephoneNumber());
            txtAccType.setText(customer.getAccountType());
            txtEmail.setText(customer.getCustomerEmail());
        }

    public void confirmButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FadeTransition ft = new FadeTransition(Duration.millis(666));
        ft.setNode(paneCreatingAccount);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        if (!paneCreatingAccount.isVisible()){
            paneCreatingAccount.setVisible(true);
            ft.playFromStart();
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBarDone.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(7), e-> {
                    // do anything you need here on completion...
                    txtStatus.setVisible(false);
                    ft.setNode(txtStatus);
                    txtStatus.setText("Done!");
                    txtStatus.setVisible(true);
                    ft.playFromStart();
                    progressBarDone.setVisible(false);
                    ft.setNode(imgDone);
                    imgDone.setVisible(true);
                    ft.playFromStart();
                }, new KeyValue(progressBarDone.progressProperty(), 1))
        );
        timeline.setCycleCount(1);
        timeline.play();

        if (new CustomerDetailsController().savingDetails(customer)){
            if (deposit != null){
                if (new )
            }
        }

    }
}
