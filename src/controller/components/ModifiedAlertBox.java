package controller.components;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.util.Objects;


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

    }


    /*
        --> default .show() method will run when calling this
            from the object's reference name.
     */
    public void showAlert(){
        this.alert.show();
    }

}
