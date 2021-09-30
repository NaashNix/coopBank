/*
 * Copyright (c) 2021. Coded By Ravindu Thilakshana (naashnix) naashnix.bio.link
 */

package controller.components;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class FormFieldValidator {
    private final ArrayList<TextField> emptyTextFields = new ArrayList<>();
    private final ArrayList<TextField> correctlyFormatted = new ArrayList<>();
    private final ArrayList<TextField> notFormatted = new ArrayList<>();
    private final ArrayList<TextField> allTextFields = new ArrayList<>();
    private Pair[] textFields;
    private String pattern;


    /*
     * Initialization FormFieldValidator validator = new FormFieldValidator(textField1,textField2,...);
     */

    // * Constructor will separate empty fields and store them in a ArrayList.
    public FormFieldValidator(TextField...textFields){
        allTextFields.addAll(Arrays.asList(textFields));
        for (TextField temp :allTextFields
        ) {
            if (temp.getText().isEmpty()){
                emptyTextFields.add(temp);
            }
        }
    }

    // * @regex -> text format that you want (eg. "\\d{2}-\\d{2}-\\d{4}")
    // * This will return the matched text fields that are passed in the
    // * constructor according to passed regex.
    public ArrayList<TextField> getMatchedRegex(String regex){
        ArrayList<TextField> formatted = new ArrayList<>();
        for (TextField temp: allTextFields
             ) {
            if (temp.getText().matches(regex)){
                formatted.add(temp);
            }
        }
        return formatted;
    }

    // * @regex -> text format that you want (eg. "\\d{2}-\\d{2}-\\d{4}")
    // * This will return the misMatched text fields that are passed in the
    // * constructor according to passed regex.
    public ArrayList<TextField> getMisMatchedRegex(String regex){
        ArrayList<TextField> NotFormatted = new ArrayList<>();
        for (TextField temp: allTextFields
        ) {
            if (!(temp.getText().matches(regex))){
                NotFormatted.add(temp);
            }
        }
        return NotFormatted;
    }

    // * This will return all the empty text fields that are passed in the constructor.
    public  ArrayList<TextField> getEmptyFields(){
        emptyTextFields.clear();
        for (TextField temp :allTextFields
             ) {
            if (temp.getText().isEmpty()){
                emptyTextFields.add(temp);
            }
        }
        return emptyTextFields;
    }

    // * @style -> css style that you want to set to the EMPTY text fields.
    // * This will set given css style to the all empty text fields that are
    // * passed in the constructor.
    public void emptyFieldProperties(String style){
        for (TextField tempTextField: emptyTextFields
             ) {
            if (tempTextField.getText().isEmpty()){
                // * Set your desired style when field is EMPTY.
                tempTextField.setStyle(style);
            }
        }
    }

    // * @style -> css style that you want to set to the FILLED text fields.
    // * This will set given css style to the all empty text fields that are
    // * passed in the constructor.
    // * I recommend to use this before using emptyTextFieldProperties.
    // * The best implementation is in this link.
    // * https://ibb.co/k1hqtNV
    public void filledFieldProperties(String style){

        for (TextField tempTextField: allTextFields
             ) {
           if (!tempTextField.getText().isEmpty()){
               // * Set your desired style when field is filled.
               tempTextField.setStyle(style);
           }
        }
    }

    // * clear all the text fields that are passed to constructor.
    public void clearAllTextFields(){
        for (TextField temp : allTextFields
             ) {
            temp.setText("");
        }
    }

    // * This will clear the given text field set.
    // * The sample implementation is in the link below.
    // * https://ibb.co/gr2L9wN
    public void clearAllTextFields(TextField...textFieldsToClear){
        for (TextField tempField:textFieldsToClear
             ) {
            tempField.clear();
        }
    }

    // * @anchorpane -> Anchorpane.
    // * This will clear all the text fields that are in the given anchorpane.
    // * A sample implementation is in the below link.
    // * https://ibb.co/RQCZC13
    public void clearAllTextFields(AnchorPane anchorPane){
        for (Node node : anchorPane.getChildren()) {
            //System.out.println("Id: " + node.getId());
            if (node instanceof TextField) {
                // clear
                ((TextField)node).setStyle("-fx-border-color:#b5b5b5");
            }
        }
    }

    // * return are there any empty text fields in the passed text fields in the constructor.
    // * @return true -> if there is a one or more than one empty fields.
    // * @return false -> if there are no empty fields.
    public boolean checkEmptyFields(){
        boolean result = false;

        for (TextField temp:allTextFields
             ) {
            if (temp.getText().isEmpty()){
                return true;
            }
        }
        return result;
    }

    // * Returns the inputted text in the set of text fields that are passed in the
    // * main constructor respectively.
    public ArrayList<String> getText(){
        ArrayList<String> texts = new ArrayList<>();
        for (TextField temp:allTextFields
             ) {
            texts.add(temp.getText());
        }
        return texts;
    }

    public void setFieldsTextReadyToEdit(){
        for (TextField field : allTextFields
             ) {
            field.setEditable(true);
        }

    }

    public void setEditableFalse(){
        for (TextField field : allTextFields
        ) {
            field.setEditable(false);
        }
    }

    public boolean validate(LinkedHashMap<TextField, Pattern> map) {
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                 textFieldKey.setStyle("-fx-border-color:red");
                 return false;
                }
            }
        }
        return true;
    }

    public void validateSingle(TextField textField, Pattern pattern){
        if (!pattern.matcher(textField.getText()).matches()){
            if (!textField.getText().isEmpty()){
                textField.setStyle("-fx-border-color:red");
            }
        }else{
            textField.setStyle("-fx-border-color:green");
        }
    }


}
