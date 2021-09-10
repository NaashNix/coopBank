package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DashboardFormController {
    public AnchorPane menuBarContext;
    public AnchorPane playGroundContext;
    public Label lblDashboardTime;
    public Label lblDashboardDate;

    //Initialize Method For Date And Time.
    public void initialize() throws IOException {
        currentDateAndTime();
        try {
            loadPlayGround();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void loadPlayGround() throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        System.out.println(resource);
        Parent load = FXMLLoader.load(resource);
        playGroundContext.getChildren().clear();
        playGroundContext.getChildren().add(load);
    }


    public void currentDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDashboardDate.setText(LocalDateTime.now().format(dateFormat));
            lblDashboardTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


}
