package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.SampleTM;

public class DepositFormController {

    public TableColumn colDate;
    public TableColumn colAccount;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public TableView<SampleTM> latestTransactions;

    public void initialize(){

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAccount.setCellValueFactory(new PropertyValueFactory<>("account"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ObservableList<SampleTM> sampleTMS = FXCollections.observableArrayList();
        sampleTMS.add(new SampleTM(
                "Today",
                "Savings",
                "Deposit",
                "Rs. 7896.36"
        ));

        latestTransactions.setItems(sampleTMS);

    }


}
