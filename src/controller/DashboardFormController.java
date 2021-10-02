package controller;

import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.InstantLoanController;
import controller.dbControllers.LoanByDepositController;
import controller.dbControllers.RationLoanController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.InstantLoanModel;
import model.LoanByDeposit;
import model.RationLoanModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DashboardFormController {
    public AnchorPane menuBarContext;
    public AnchorPane playGroundContext;
    public Label lblDashboardTime;
    public Label lblDashboardDate;
    public MenuButton buttonOther;
    public TextField searchField;
    public Circle notificationCircle;
    public Text txtNotificationNumber;

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

        searchField.onKeyReleasedProperty().set(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.ENTER){

                }
            }
        });

        // * Check arrears loans.
        try {
            checkOutDatedLoans();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
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

    public void IssueALoanOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LendingForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void IssueInstantLoanOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewIssuedInstantLoans.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void viewAllAccountOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewAllCustomersForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void payLoanOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PayLoanInstallments.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void IssueLoanByDepositLoanOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewIssuedLoanByDeposit.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void IssueRationLoanOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewIssuedRationLoan.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void reportProblemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportProblemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void viewExpenditures(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/BankExpendituresForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void addExpenditures(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddBankExpenditures.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void viewAddIncome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/IncomeAdder.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void viewIncomes(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewIncomesForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }

    public void checkOutDatedLoans() throws SQLException, ClassNotFoundException {
        ArrayList<InstantLoanModel> ArreasedInstantLoan = new InstantLoanController().getArreasedLoan();
        ArrayList<LoanByDeposit> ArreasedLoanByDeposits = new LoanByDepositController().getArreasedLoan();
        ArrayList<RationLoanModel> ArreasedRationLoan = new RationLoanController().getArreasedLoan();

        int numberOfAreasLoans = 0;
        ArrayList<String> arreasLoanNumbers = new ArrayList<>();

        if (ArreasedInstantLoan!=null){
            for (InstantLoanModel model : ArreasedInstantLoan
            ) {
                numberOfAreasLoans++;
            }
        }

        if (ArreasedLoanByDeposits!=null){
            for (LoanByDeposit rModels : ArreasedLoanByDeposits
            ) {
                numberOfAreasLoans++;
            }
        }

        if (ArreasedRationLoan!=null){
            for (RationLoanModel rModel : ArreasedRationLoan
            ) {
                numberOfAreasLoans++;
            }
        }

        if (numberOfAreasLoans>0){
            txtNotificationNumber.setText(String.valueOf(numberOfAreasLoans));
            notificationCircle.setFill(Color.RED);
        }

        ObjectPasser.setArrearsLoans(ArreasedInstantLoan,ArreasedRationLoan,ArreasedLoanByDeposits);

    }


    public void openArrearsLoanForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoanArreasForm.fxml");
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }
}
