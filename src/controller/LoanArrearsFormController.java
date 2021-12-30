/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.components.ModifiedAlertBox;
import controller.components.ObjectPasser;
import controller.dbControllers.CustomerDetailsController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class LoanArrearsFormController {

    public TableView<ArrearsLoanDisplayModel> tableExpenditures;
    public TableColumn colLoanNumber;
    public TableColumn colDebtorName;
    public TableColumn colInstallment;
    public TableColumn colLoanAmount;
    public TableColumn colPaymentDate;
    public TableColumn colRemainingInstallments;
    public TableColumn colDebtorTelephone;
    public TableColumn colLoanType;
    public ObservableList<ArrearsLoanDisplayModel> allModels;
    public ArrayList<InstantLoanModel> instantLoanModels;
    public ArrayList<LoanByDeposit> loanByDepositsModels;
    public ArrayList<RationLoanModel> rationLoanModels;
    public int selectedRow = -1;
    public Button btnSendEmail;
    public Button btnPay;
    public Button btnPrint;
    public Button btnBadCredit;
    public Pane sendingPane;
    public ProgressBar sendingBar;
    public AnchorPane arrearsContext;

    public void initialize() throws SQLException, ClassNotFoundException {
        instantLoanModels = ObjectPasser.getArrearsInstantLoans();
        loanByDepositsModels = ObjectPasser.getArrearsLoanByDeposit();
        rationLoanModels = ObjectPasser.getArrearsRationLoans();

        ObjectPasser.setArrearsLoans(null,null,null);

        // * Observable List to add the models.
        allModels = FXCollections.observableArrayList();

        // * Move all to the one arrayList and make the object all of them.
        addAllToTheMainList();

        // * table cell factories.
        colLoanNumber.setCellValueFactory(new PropertyValueFactory<>("loanNumber"));
        colDebtorName.setCellValueFactory(new PropertyValueFactory<>("debtorName"));
        colInstallment.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colLoanAmount.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colRemainingInstallments.setCellValueFactory(new PropertyValueFactory<>("remainingInstallments"));
        colDebtorTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        // * Table Listener
        tableExpenditures.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = newValue.intValue();
        });


    }
    public void tableRowSelectOrNot(MouseEvent mouseEvent) {
        if (selectedRow==-1){
            btnPay.setDisable(true);
            btnBadCredit.setDisable(true);
            btnSendEmail.setDisable(true);
            btnPrint.setDisable(true);
        }else{
            btnSendEmail.setDisable(false);
            btnPay.setDisable(false);
            btnPrint.setDisable(false);
            btnBadCredit.setDisable(false);
        }
    }



    public void addAllToTheMainList() throws SQLException, ClassNotFoundException {
        if (instantLoanModels!=null){
            for (InstantLoanModel iModel : instantLoanModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(iModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        iModel.getiLoanNumber(),
                        cModel.getName(),
                        "Instant Loan", iModel.getiMonthlyInstallment(),
                        iModel.getiLoanAmount(),
                        iModel.getNextInstallmentDate(),
                        iModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress(),
                        cModel.getAccountNumber()));
            }
        }

        if (loanByDepositsModels!=null){
            for (LoanByDeposit lModel : loanByDepositsModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(lModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        lModel.getLoanNumber(),
                        cModel.getName(),
                        "Loan By Dep.", lModel.getMonthlyInstallment(),
                        lModel.getLoanAmount(),
                        lModel.getNextInstallmentDate(),
                        lModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress(),
                        cModel.getAccountNumber()));
            }
        }

        if (rationLoanModels!=null){
            for (RationLoanModel rModel : rationLoanModels
            ) {
                CustomerModel cModel = new CustomerDetailsController().getCustomerDetails(rModel.getAccountNumber());
                allModels.add(new ArrearsLoanDisplayModel(
                        rModel.getLoanNumber(),
                        cModel.getName(),
                        "Ration Loan", rModel.getMonthlyInstallment(),
                        rModel.getLoanAmount(),
                        rModel.getNextInstallmentDate(),
                        rModel.getInstallmentsToBePaid(),
                        cModel.getTelephoneNumber(),
                        cModel.getCustomerEmail(),
                        cModel.getCustomerAddress(),
                        cModel.getAccountNumber()));
            }
        }

        tableExpenditures.setItems(allModels);
    }

    public void sendEmailOnAction(ActionEvent actionEvent) {
        if (selectedRow!=-1) {
            try {
                sendMail(allModels.get(selectedRow).getEmail());
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String msgBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Inform About Arrears Loan Installment");
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



    public void sendMail(String receipt) throws MessagingException, IOException {

        arrearsContext.setDisable(true);
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
                new KeyFrame(Duration.seconds(7), e-> {
                    // do anything you need here on completion...
                    ModifiedAlertBox box = new ModifiedAlertBox("Done!", Alert.AlertType.INFORMATION
                            ,"Done!","Message sent successfully!");
                    box.showAlert();
                    sendingPane.setVisible(false);
                    arrearsContext.setDisable(false);
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

        String name = allModels.get(selectedRow).getDebtorName();
        String loanNumber = allModels.get(selectedRow).getLoanNumber();
        double loanAmount = allModels.get(selectedRow).getLoanAmount();
        double installment = allModels.get(selectedRow).getInstallment();


        String messageBody = "<h3>To : "+name+"</h3><h3> Dear sir/madam,</h3><h4>You Loan Number : "+loanNumber+"</h4>" +
                "<p>You have borrowed amount of <b>Rs. "+loanAmount+"</b> from Galthude Sanasa Bank. This email is for you to inform" +
                " that the you have missed a installment of amount <b> Rs. "+installment+".</b></br>" +
                " Please send your cheque for the whole amount so that the outstanding in your account is cleared.<br>" +
                "If you want any further information regarding this email</br>please feel free to call us via +94 38 96 96 452 (Sanasa Bank).<br>" +
                "Thank you!</br>" +
                "Your Faithfully,<br>" +
                "Branch Manager,<br>";

        Message message = prepareMessage(session, myAccountEmail, receipt, messageBody);
        try {
            Transport.send(message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }

    private void printReceipt(){

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/reports/ArrearsLetter.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            HashMap map = new HashMap();
            map.put("name",allModels.get(selectedRow).getDebtorName());
            map.put("loanNumber",allModels.get(selectedRow).getLoanNumber());
            map.put("loanAmount",allModels.get(selectedRow).getLoanAmount());
            map.put("installmentAmount",allModels.get(selectedRow).getInstallment());

            JasperPrint print = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
            JasperViewer.viewReport(print,false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void printButtonOnAction(ActionEvent actionEvent) {
        if (selectedRow!=-1) {
            printReceipt();
        }
    }

    public void payButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedRow!=-1) {
            ObjectPasser.setAccountNumberForPayLoan(allModels.get(selectedRow).getAccountNumber());
            URL resource = getClass().getResource("../view/PayLoanInstallments.fxml");
            Parent load = FXMLLoader.load(resource);
            arrearsContext.getChildren().clear();
            arrearsContext.getChildren().add(load);
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MainDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        arrearsContext.getChildren().clear();
        arrearsContext.getChildren().add(load);
    }
}
