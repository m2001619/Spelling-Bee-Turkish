package com.example.spellingbee;

import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Hexagon extends StackPane {
    public Text text = new Text();
    public static double hexagonWidthHeight = 60;
    public static double hexagonLayoutY = 30;
    public Polyline hexagon = new Polyline(60.0,90.0,
            105.0,90.0,
            120.0,60.0,
            105.0,30.0,
            60.0,30.0,
            45.0,60.0,
            60.0,90.0);
    public Hexagon(Color color) {
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Lato", FontWeight.BOLD,20));
        hexagon.setFill(color);
        hexagon.setStroke(Color.WHITE);
        getChildren().addAll(hexagon,text);
        setCursor(Cursor.HAND);
    }
    public void setLetter(Character text) {
        this.text.setText(text.toString());
    }
    public String getLetter() {
        return text.getText();
    }

}
