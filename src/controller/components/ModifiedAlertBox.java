package controller.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;


public class ModifiedAlertBox {
    public Alert alert;

    public ModifiedAlertBox(String title, Alert.AlertType alertType, String header,
                            String context){


        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        // * Accessing dialogPane of the alert box.
        DialogPane dialogPane = alert.getDialogPane();

        // * Styling dialogPane by adding css.
        try {
            dialogPane.getStylesheets().add((Objects.requireNonNull(getClass()
                    .getResource("AlertBoxStyle.css"))).toExternalForm());

            // * Setting style class
            dialogPane.getStyleClass().add("myDialog");

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
        //buttonBar.setStyle("-fx-text-fill:white;");
        buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-color:#2c3e50;" +
                "-fx-font-weight:bold; -fx-text-fill:white;"));

    }

    public void setMethodToButtons(AnchorPane pane, String fxmlFile) throws IOException {
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()){
            navigateToFxml(pane,fxmlFile);
        }else if (result.get()==ButtonType.OK){
            navigateToFxml(pane,fxmlFile);
        }else{
            navigateToFxml(pane,fxmlFile);
        }
    }

    private void navigateToFxml(AnchorPane pane, String fxmlFile) throws IOException {
        URL resource = getClass().getResource(fxmlFile);
        Parent load = FXMLLoader.load(resource);
        pane.getChildren().clear();
        pane.getChildren().add(load);
    }



    /*
        --> default .show() method will run when calling this
            from the object's reference name.
     */
    public void showAlert(){
        this.alert.show();
    }

}
