package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;

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
}
