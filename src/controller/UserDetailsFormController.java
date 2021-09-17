package controller;

import controller.components.ModifiedAlertBox;
import controller.dbControllers.UserDetailsController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.UserDetailsModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDetailsFormController {

    public TableView<UserDetailsModel> tblUserDetails;
    public TableColumn colName;
    public TableColumn colPosition;
    public ArrayList<UserDetailsModel> userDetailsModels;
    public TextField txtName;
    public TextField txtTelephone;
    public TextField txtPosition;
    public TextField txtUserName;
    public TextField txtPassword;
    public String selectedUserName = null;
    public AnchorPane userDetailsContext;

    public void initialize() throws SQLException, ClassNotFoundException {
        // * Setting data to table
        settingDataToTable();

        // * Setting properties to table columns.
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("bankPosition"));

        // * Table row selected listener, When selected -> selectedRow (int) changed.
        tblUserDetails.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {

                    // * Calling load data to edit/view form
                    setDataToEditViewForm((int) newValue);
                }
        );

    }

    private void setDataToEditViewForm(int selectedRowNumber) {
        /*
            --> This method is for setting data to edit/view form.
            --> This runs after selecting row in the table(User Details).
         */

        // * getting the selected user name.
        UserDetailsModel selectedModel = selectedRowNumber == -1 ? null :
                userDetailsModels.get(selectedRowNumber);

        // * Setting data to the fields.
        if (selectedModel!= null){

            // * This variable is for saving and updating/ deleting the existing detail packet.
            selectedUserName = selectedModel.getUserName();

            txtName.setText(selectedModel.getName());
            txtPosition.setText(selectedModel.getBankPosition());
            txtTelephone.setText(selectedModel.getTelephone());
            txtUserName.setText(selectedUserName);
            txtPassword.setText(selectedModel.getPassword());
        }

    }

    private void settingDataToTable() throws SQLException, ClassNotFoundException {
        // * getting all the User Details Models
        userDetailsModels = new UserDetailsController().getAllUsersDetails();

        // * Setting Data to table.
        for (UserDetailsModel model : userDetailsModels
             ) {
            tblUserDetails.setItems(FXCollections.observableArrayList(model));
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        /*
            --> This method is for save/update the selected edited user details.
         */

        // * Validating the all fields are not empty.
        if (validatingFields()){

            // * Creating user details model and send to updateUserDetails().
            if (new UserDetailsController().updateUserDetails(
                    selectedUserName,
                    new UserDetailsModel(
                            txtName.getText(),
                            txtTelephone.getText(),
                            txtPosition.getText(),
                            txtUserName.getText(),
                            txtPassword.getText()
                    )))
            {

                // * Run Load Data to table method again
                settingDataToTable();

                //Clear Text Fields
                clearFields();

                // * Creating modified alert box and show when successfully saved!.
                ModifiedAlertBox alert = new ModifiedAlertBox(
                        "Successfully Updated!",
                        Alert.AlertType.INFORMATION,
                        "Done!",
                        "User Details  have been updated!"
                );
                alert.showAlert();
            }else {

                // * If any error occured in saving process
                System.out.println("Failed! [@updatingUserDetails]");
            }

        }else {

            // * If fields are empty,
            System.out.println("Failed! [@validatingFields]");
        }

    }

    private void clearFields() {
        /*
            --> Clear all fields in the view/edit form.
         */

        txtPosition.clear();
        txtTelephone.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtName.clear();

        // * Selected User Name clear
        selectedUserName = null;

    }

    private boolean validatingFields() {
        /*
            --> Ensuring that the all the fields are filled.
            --> This method is called in the saveBranchDetails() method.
            --> Return (boolean), if true * then saving details process is starts.
        */

        // * If all fields are filled, returns true.
        return !txtPassword.getText().isEmpty() && !txtName.getText().isEmpty()
                && !txtTelephone.getText().isEmpty() && !txtUserName.getText().isEmpty()
                && !txtPosition.getText().isEmpty();

    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        /*
            --> This method will navigate to home screen without saving any data.
            --> All data that are changed without saving will be reset.
         */

        // * Load Main dashboard fxml.
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        Parent load = FXMLLoader.load(resource);
        userDetailsContext.getChildren().clear();
        userDetailsContext.getChildren().add(load);


    }
}
