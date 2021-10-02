/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller;

import controller.dbControllers.ExpenditureController;
import controller.dbControllers.IncomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BankExpenditureModel;
import model.IncomeViewerModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewIncomesFormController {

    public ComboBox<String> cmbTimePeriod;
    public Label lblTotalExpenditure;
    public TableView<IncomeViewerModel> tableExpenditures;
    public TableColumn colReceiptNumber;
    public TableColumn colDesc;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colAmount;

    public void initialize() throws SQLException, ClassNotFoundException {
        // * Set cell values factories
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReceiptNumber.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        // * set values to column and set default selected values to the last month
        cmbTimePeriod.getItems().addAll("All The Time","Last Month");
        cmbTimePeriod.setValue("Last Month");


        cmbTimePeriod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDataToFields(newValue);
        });

        // * initialize last month data.
        getLastMonthData();

    }

    private void setDataToFields(String timePeriod) {
        if (timePeriod.equals("Last Month")){
            try {
                getLastMonthData();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else if (timePeriod.equals("All The Time")){
            try {
                getAllTheTimeData();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    private void getAllTheTimeData() throws SQLException, ClassNotFoundException {
        lblTotalExpenditure.setText("Rs. "+new IncomeController().calculateAllTheTimeIncomes());
        ArrayList<IncomeViewerModel> bankModels = new IncomeController().getAllTheTimeIncomes();
        ObservableList<IncomeViewerModel> tableModelsAllTheTime = FXCollections.observableArrayList();
        tableModelsAllTheTime.clear();
        if (bankModels!=null){
            tableModelsAllTheTime.addAll(bankModels);
        }
        tableExpenditures.refresh();
        tableExpenditures.setItems(tableModelsAllTheTime);

    }

    public void getLastMonthData() throws SQLException, ClassNotFoundException {

        lblTotalExpenditure.setText("Rs. "+(new IncomeController().calculateLastMonthIncomes()));
        ArrayList<IncomeViewerModel> bankModels = new IncomeController().getLastMonthIncomes();
        ObservableList<IncomeViewerModel> tableModels = FXCollections.observableArrayList();
        tableModels.clear();
        if (bankModels!=null){
            tableModels.addAll(bankModels);
        }
        tableExpenditures.refresh();
        tableExpenditures.setItems(tableModels);

    }

}
