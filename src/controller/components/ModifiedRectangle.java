package controller.components;

import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ModifiedRectangle extends Rectangle {

    /*
        --> This class is for creating the object of modified rectangle tile.
     */


    public  ModifiedRectangle(double height, double width){
        super();
        createRectangle(height,width);

    }

    public  ModifiedRectangle(double height, double width, String topic, double fontSize){
        super();
        createRectangle(height,width);
        setTopic(topic, fontSize);


    }


    private void setTopic(String topic, double fontSize) {
        Label txtTopic = new Label();
        txtTopic.setText(topic);
        txtTopic.setFont(Font.font("Roboto Black", FontWeight.BOLD,fontSize));
        txtTopic.setLayoutX(getLayoutX()-10);
        txtTopic.setLayoutY(getLayoutY()-10);
    }


    public void createRectangle(double height, double width){
        setHeight(height);
        setWidth(width);
        setFill(Color.rgb(255,255,255,1));
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(Color.rgb(0,0,0,1));
        shadow.setHeight(21.0);
        shadow.setWidth(21.0);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setSpread(0.1);
        shadow.setRadius(10.0);
        setArcHeight(40.0);
        setArcWidth(40.0);
        setEffect(shadow);
    }
}
