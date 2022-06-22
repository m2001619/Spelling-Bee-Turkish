package com.example.spellingbee;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ControlButtons extends Button {
     {
        setStyle("-fx-background-color: transparent;-fx-border-width: 1;-fx-border-color: rgb(220,220,220);-fx-border-radius: 50");
        setPadding(new Insets(12,20,12,20));
        setCursor(Cursor.HAND);
    }
    public ControlButtons(String text){super(text);}
    public ControlButtons(ImageView imageView){
         setGraphic(imageView);
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
    }
}
