/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import com.sun.istack.internal.logging.Logger;
import controller.components.ModifiedAlertBox;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

public class ReportProblemFormController {
    public TextArea txtMessageBody;
    public AnchorPane reportContext;
    public Pane sendingPane;
    public ProgressBar sendingBar;
    public AnchorPane mainContext;
    public AnchorPane overlayContext;

    public void sendButtonOnAction(ActionEvent actionEvent) throws MessagingException, IOException {
        if (!txtMessageBody.getText().isEmpty()) {
            sendMail("thilakshanaravindu@gmail.com");
        }else{
            ModifiedAlertBox alertBox = new ModifiedAlertBox("ERROR!", Alert.AlertType.ERROR
            ,"Empty!","Can't send empty message");
            alertBox.showAlert();
            URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            reportContext.getChildren().clear();
            reportContext.getChildren().add(load);
        }

    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        reportContext.getChildren().clear();
        reportContext.getChildren().add(load);
    }

    public void sendMail(String receipt) throws MessagingException, IOException {

        mainContext.setDisable(true);
        overlayContext.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(sendingPane);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        sendingPane.setVisible(true);
        ft.playFromStart();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sendingBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), e-> {
                    // do anything you need here on completion...
                    ModifiedAlertBox box = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION
                            ,"Done!","Message sent successfully!");
                    box.showAlert();
                    URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
                    Parent load = null;
                    try {
                        load = FXMLLoader.load(resource);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    reportContext.getChildren().clear();
                    reportContext.getChildren().add(load);
                }, new KeyValue(sendingBar.progressProperty(), 1))
        );
        timeline.setCycleCount(1);
        timeline.play();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "sanasabankgalthude@gmail.com";
        String password = "5744210Sanasa";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });



        Message message = prepareMessage(session, myAccountEmail, receipt, txtMessageBody.getText());
        try {
            Transport.send(message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String msgBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Reported Problem : Sanasa Bank Galthude");
            /*String htmlCode = "<h1> WE LOVE JAVA </h1> <br/> <h2><b>Next Line </b></h2>";*/
            message.setContent(msgBody, "text/html");
            return message;
        } catch (AddressException ex) {
            /*Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);*/
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
