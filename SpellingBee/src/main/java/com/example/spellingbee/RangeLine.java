package com.example.spellingbee;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class RangeLine extends HBox {
    private int CirclesNumber = 8;
    private int LinesNumber = 8;
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private Rectangle rectangle = new Rectangle(10,10);
    public RangeLine() {
        setAlignment(Pos.CENTER_LEFT);
        for(int i=0;i<CirclesNumber;i++){
            circles.add(new Circle(10,10,5));
            circles.get(i).setFill(Color.rgb(230,230,230));
        }
        for(int i=0;i<LinesNumber;i++){
            lines.add(new Line(1,1,30,1));
            lines.get(i).setStroke(Color.rgb(230,230,230));
        }
        for (int i=0;i<CirclesNumber+LinesNumber;i++){
            if(i%2 == 0)
                getChildren().add(circles.get(i/2));
            else
                getChildren().add(lines.get(i/2));
        }
        rectangle.setFill(Color.rgb(230,230,230));
        getChildren().add(rectangle);
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
