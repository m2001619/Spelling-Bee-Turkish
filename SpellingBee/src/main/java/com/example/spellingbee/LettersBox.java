package com.example.spellingbee;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;

public class LettersBox extends Pane {
    private ArrayList<Hexagon> hexagons = new ArrayList<>();
    private double hexWidth = Hexagon.hexagonWidthHeight;
    private double hexLayoutY = Hexagon.hexagonLayoutY;
    public LettersBox() {
        setPrefSize(MainPane.width * 0.5 -100, MainPane.height * 0.5);
        //setStyle("-fx-background-color: #000;");
        hexagons.addAll(Arrays.asList(
                new Hexagon(Color.rgb(230,230,230)),
                new Hexagon(Color.rgb(230,230,230)),
                new Hexagon(Color.rgb(247,218,33)),
                new Hexagon(Color.rgb(230,230,230)),
                new Hexagon(Color.rgb(230,230,230)),
                new Hexagon(Color.rgb(230,230,230)),
                new Hexagon(Color.rgb(230,230,230))
        ));
        hexagons.get(0).setLayoutY(hexLayoutY);
        hexagons.get(0).setLayoutX(getPrefWidth() * 0.5 - hexWidth * 0.5);
        hexagons.get(1).setLayoutY(hexLayoutY + hexWidth * 0.6);
        hexagons.get(1).setLayoutX(getPrefWidth() * 0.5 - hexWidth * 1.6);
        hexagons.get(2).setLayoutY(hexLayoutY + hexWidth * 1.1);
        hexagons.get(2).setLayoutX(getPrefWidth() * 0.5 - hexWidth * 0.5);
        hexagons.get(3).setLayoutY(hexLayoutY + hexWidth * 0.6);
        hexagons.get(3).setLayoutX(getPrefWidth() * 0.5 + hexWidth * 0.6);
        hexagons.get(4).setLayoutY(hexLayoutY + hexWidth * 1.7);
        hexagons.get(4).setLayoutX(getPrefWidth() * 0.5 - hexWidth * 1.6);
        hexagons.get(5).setLayoutY(hexLayoutY + hexWidth * 2.2);
        hexagons.get(5).setLayoutX(getPrefWidth() * 0.5 - hexWidth * 0.5);
        hexagons.get(6).setLayoutY(hexLayoutY + hexWidth * 1.7);
        hexagons.get(6).setLayoutX(getPrefWidth() * 0.5 + hexWidth * 0.6);
        getChildren().addAll(hexagons);
    }
    public void shuffleLetters(ArrayList<Character> arr) {
        char importantChar = arr.remove(2);
        hexagons.get(2).setLetter(importantChar);
        Collections.shuffle(arr);
        arr.add(2,'.');
        for(int i=0; i<hexagons.size();i++){
            if (arr.get(i) != '.')
            hexagons.get(i).setLetter(arr.get(i));
        }
        arr.set(2,importantChar);
    }

    public ArrayList<Hexagon> getHexagons() {
        return hexagons;
    }
}
