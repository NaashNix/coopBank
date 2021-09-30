package controller;

import controller.dbControllers.CustomerDetailsController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.CustomerModel;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
   public String userName = "user";
   public String password = "1234";


   public void loginTemp(ActionEvent actionEvent) throws IOException {
      Parent load = FXMLLoader.load(getClass().getResource("../view/MenuBar.fxml"));
      Scene scene = new Scene(load);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setResizable(false);
      stage.setTitle("Co-operative Bank Management ");
      stage.show();
      Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
      loginStage.close();
   }

   /*public void calculateInterestSavings() throws SQLException, ClassNotFoundException {
      // * Get the customer models to the arraylist.
      ArrayList<CustomerModel> customerModels =

   }*/

}
