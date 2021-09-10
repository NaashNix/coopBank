package controller;

import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.Scanner;


public class MainDashboardForm {

    public Text calDisplay;
    public String operator = "";
    public boolean start = true;
    private double numOne;
    private TransformationList<Object, Object> actionEvent;


    public void initialize(){


    }

    public void processCalKey(ActionEvent actionEvent){
        if (start){
            calDisplay.setText("");
            start = false;
        }
        String value = ((Button) actionEvent.getSource()).getText();
        calDisplay.setText(calDisplay.getText() + value);

    }

    public void processCalOperators(ActionEvent actionEvent){
        String value = ((Button) actionEvent.getSource()).getText();
            if (!value.equals("=")){
                if (!operator.isEmpty())
                    return;

                operator = value;
                numOne = Double.parseDouble(calDisplay.getText());
                calDisplay.setText("");

            }else{
                if (operator.isEmpty()) return;
                double numTwo = Double.parseDouble(calDisplay.getText());
                double result = calculator(numOne, numTwo, operator);
                calDisplay.setText(String.valueOf(result));
                operator = "";
                start = true;
            }

    }

    private double calculator(double numOne, double numTwo, String operator) {
        switch(operator){
            case "+" : {
                return numOne + numTwo;
            }
            case "-" : {
                return numOne - numTwo;
            }
            case "x" : {
                return numOne*numTwo;
            }
            case "/" : {
                if (numTwo==0){
                    return 0;
                }
                return numOne/numTwo;
            }
            case "%" : {
                return (numOne*100)/numTwo;
            }
            default: {
                return 0;
            }
        }
    }

    public void depositFormOnAction(ActionEvent actionEvent) {

    }
}
