package controller;

import controller.components.ModifiedAlertBox;
import controller.dbControllers.CustomerDetailsController;
import controller.dbControllers.LoginDetailsController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.CustomerModel;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
   //public String userName = "user";
   //public String password = "1234";
    public TextField txtUserName;
    public PasswordField txtPassword;


    public void loginTemp(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (new LoginDetailsController().checkUserNameExists(txtUserName.getText())){
            if (new LoginDetailsController().getPassword(txtUserName.getText()).equals(txtPassword.getText())){
                Parent load = FXMLLoader.load(getClass().getResource("../view/MenuBar.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Co-operative Bank Management ");
                stage.show();
                Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                loginStage.close();
            }else{
                ModifiedAlertBox alertBox = new ModifiedAlertBox("Failed!", Alert.AlertType.ERROR,"Failed!","Login Failed!");
                alertBox.showAlert();
            }
        }else{
            ModifiedAlertBox alertBox = new ModifiedAlertBox("Failed!", Alert.AlertType.ERROR,"Failed!","Login Failed!");
            alertBox.showAlert();
        }





   }


}
