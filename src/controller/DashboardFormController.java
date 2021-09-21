package controller;

import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DashboardFormController {
    public AnchorPane menuBarContext;
    public AnchorPane playGroundContext;
    public Label lblDashboardTime;
    public Label lblDashboardDate;
    public MenuButton buttonOther;
    public TextField searchField;

    // * Initialize Method For Date And Time.
    public void initialize() throws IOException {

        // * Load system time and date.
        currentDateAndTime();

        // * Load Main Dashboard to the 'Anchor pane.'
        try {
            loadPlayGround();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    private void loadPlayGround() throws IOException {
        /*
            --> Load MainDashboardForm.fxml to the Playing Anchor Pane.
         */

        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void currentDateAndTime() {

        //* This method is for live date and time.
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDashboardDate.setText(LocalDateTime.now().format(dateFormat));
            lblDashboardTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void openUserDetailsOnAction(ActionEvent actionEvent) throws IOException {
        /*
            --> This method is to load the 'UserDetailsForm.fxml' to the Anchor Pane.
            --> Method is RUN when clicking the 'User Details' Menu-item in the Menu Bar.
         */

        URL resource = getClass().getResource("../view/UserDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);

    }

    public void openBankDetailsOnAction(ActionEvent actionEvent) throws IOException {
        /*
            --> This method is to load the 'BankDetailsForm.fxml' to the Anchor Pane.
            --> Method is RUN when clicking the 'Bank Details' Menu-item in the Menu Bar.
         */

        URL resource = getClass().getResource("../view/BankDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void openNewAccountOnAction(ActionEvent actionEvent) throws IOException {

        /*
            --> This method will load the 'OpenNewAccountForm.fxml'
            --> Method is RUN when clicking the 'Open New Account' Menu-item in the Menu Bar.
         */

        URL resource = getClass().getResource("../view/OpenNewAccount.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);


    }

    public void openLoanDetailsOnAction(ActionEvent actionEvent) throws IOException {

        /*
            --> This method will load the 'LoanDetails.fxml'
            --> Method is RUN when clicking the 'Loan Details' Menu-item in the Menu Bar.
         */

        URL resource = getClass().getResource("../view/LoanDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void openDashboardOnAction(ActionEvent actionEvent) throws IOException {
        /*
            --> This method will load 'Main Dashboard.fxml' to playGround anchor pane.
         */

        // * This method is will load. This is the method that is called in  'initialize()' also.
        loadPlayGround();

    }

    public void openLoginForm(ActionEvent actionEvent) throws IOException {


        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) menuBarContext.getScene().getWindow();
        window.setScene(new Scene(load));
        window.show();
        window.centerOnScreen();




    }

    public void searchFieldOnKeyPressed(KeyEvent keyEvent) throws SQLException, ClassNotFoundException, IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            if (searchField.getText().isEmpty()){
                ModifiedAlertBox alert = new ModifiedAlertBox("Invalid Number",
                        Alert.AlertType.ERROR,
                        "ERROR!","Invalid Account Number!");
                alert.showAlert();
            }else{
                if (checkAccountNumber()){
                    ObjectPasser.setAccountNumber(searchField.getText());
                    searchField.setText(null);
                    URL resource = getClass().getResource("../view/SearchAccountResult.fxml");
                    Parent load = FXMLLoader.load(resource);
                    playGroundContext.getChildren().clear();
                    playGroundContext.getChildren().add(load);
                }else{
                    ModifiedAlertBox alertBox = new ModifiedAlertBox("Invalid Number!", Alert.AlertType.ERROR,"ERROR!","Invalid Account Number!");
                    alertBox.showAlert();
                }
            }
        }

    }

    private boolean checkAccountNumber() throws SQLException, ClassNotFoundException {
        return new CustomerDetailsController().checkAccountNumberIsExist(searchField.getText());
    }

    public void openSearchAccount(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchAccountResult.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }
}
