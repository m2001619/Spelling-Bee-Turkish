package com.example.spellingbee;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class HelloApplication extends Application {
    private ArrayList<String> answersArray = new ArrayList<>();
    private int score = 0;
    private Dictionary dictionary = new Dictionary();
    private boolean checkFromUser = false;
    @Override
    public void start(Stage stage) throws IOException {

        // Start the program with set the letters Automatically
        if(!checkFromUser) {
            dictionary.playAutomatically();
        }

        // the Root
        Pane root = new Pane();

        // Input pane
        Pane paneInput = new Pane();
        paneInput.setPrefSize(MainPane.width/2, MainPane.height);
        double InputLayoutY = 60;
        double InputChildWidth = paneInput.getPrefWidth()-100;

            // Word Text
            TextField wordText = new TextField();
            double WordTextWidth = 50;
            wordText.setPrefWidth(InputChildWidth);
            wordText.setLayoutY(InputLayoutY);
            wordText.setLayoutX(paneInput.getPrefWidth() * 0.5 - wordText.getPrefWidth() * 0.5);
            wordText.setFont(Font.font("Smooch", FontWeight.BOLD,26));
            wordText.setAlignment(Pos.CENTER);
            wordText.setPromptText("Type or Click");
            wordText.setStyle("-fx-text-box-border: #fff;-fx-focus-color: transparent;-fx-background-color: #fff;");
            paneInput.getChildren().add(wordText);

            // Letters Box
            LettersBox letterBox = new LettersBox();
            letterBox.setLayoutY(InputLayoutY + WordTextWidth);
            letterBox.setLayoutX(paneInput.getPrefWidth()/2-letterBox.getPrefWidth()/2);
            letterBox.shuffleLetters(dictionary.getSevenLetters());
            paneInput.getChildren().add(letterBox);

            // Buttons Box
            HBox buttonsBox = new HBox();
            buttonsBox.setPrefWidth(letterBox.getPrefWidth());
            buttonsBox.setLayoutY(letterBox.getLayoutY() + letterBox.getPrefHeight());
            buttonsBox.setLayoutX(paneInput.getPrefWidth()/2 - buttonsBox.getPrefWidth()/2);
            buttonsBox.setAlignment(Pos.CENTER);
            buttonsBox.setSpacing(10);
                ControlButtons delete = new ControlButtons("Delete");
                ControlButtons shuffle = new ControlButtons(new ImageView("file:images/shuffle.png"));
                ControlButtons enter = new ControlButtons("Enter");
                buttonsBox.getChildren().addAll(delete,shuffle,enter);
            paneInput.getChildren().add(buttonsBox);

            // Playing Type Buttons
            VBox ButtonsBox = new VBox();
            ButtonsBox.setPrefWidth(InputChildWidth);
            ButtonsBox.setLayoutX(paneInput.getPrefWidth()/2 - buttonsBox.getPrefWidth()/2);
            ButtonsBox.setLayoutY(buttonsBox.getLayoutY() + 60);
            ButtonsBox.setSpacing(10);
                Button BAuto = new Button("Play Automatically");

                HBox selectFromUserBox = new HBox();
                selectFromUserBox.setSpacing(5);
                selectFromUserBox.setAlignment(Pos.CENTER_LEFT);
                    Button BSelect = new Button("Select Your Own Letters");
                    TextField userText = new TextField();
                    selectFromUserBox.getChildren().addAll(BSelect,userText);
                ButtonsBox.getChildren().addAll(BAuto,selectFromUserBox);
            paneInput.getChildren().add(ButtonsBox);

        root.getChildren().add(paneInput);

        // Output Pane
        Pane paneOutput = new Pane();
        paneOutput.setLayoutX(MainPane.width/2);

            // Range HBox
            HBox rangeBox = new HBox();
            rangeBox.setAlignment(Pos.CENTER_LEFT);
            rangeBox.setSpacing(20);
            rangeBox.setLayoutY(30);

                // Range Label
                Label rangeLbl = new Label("Beginner");
                rangeLbl.setStyle("-fx-font-size: 16px;-fx-font-weight: 700");
                rangeBox.getChildren().add(rangeLbl);

                // Range Line
                RangeLine rangeLine = new RangeLine();
                rangeBox.getChildren().add(rangeLine);

            paneOutput.getChildren().add(rangeBox);

            // Answers Area
            TextArea answersArea = new TextArea();
            answersArea.setLayoutY(rangeBox.getLayoutY() + 40);
            answersArea.setScrollTop(Integer.MAX_VALUE);
            answersArea.setPrefHeight(MainPane.height - 100);
            answersArea.setPrefWidth(MainPane.width * 0.48);
            answersArea.setEditable(false);
            answersArea.setStyle("-fx-font-size: 16px;-fx-focus-color: rgb(230,230,230);");
            answersArea.appendText("  You have found 0 word and your score is 0\n");
            paneOutput.getChildren().add(answersArea);

        root.getChildren().add(paneOutput);

        // Start Actions

            // Play Automatically Action
            BAuto.setOnAction(e -> {
                dictionary.playAutomatically();
                answersArray.clear();
                score = 0;
                reStart(stage);
            });

            // Play with user Letters Action
            BSelect.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Can not play the game");
                setDectionary(new Dictionary());
                if(userText.getText().length() != 7){
                    alert.setHeaderText("The puzzle must contain exactly seven characters.");
                    alert.show();
                }else if(!isIncludeTurkishLetters(userText.getText())) {
                    alert.setHeaderText("Every character must be one of the 29 Turkish letters.");
                    alert.show();
                }else if(!dictionary.isPangram(userText.getText())){
                    alert.setHeaderText("No letter may appear more than once in the puzzle.");
                    alert.show();
                }else {
                    dictionary.setSevenLetters(filterUserLetters(userText.getText()));
                    dictionary.playLettersFromUser();
                    if(dictionary.getScore() == 0) {
                        alert.setHeaderText("For Your letters, there is no word matches there in the dectionary");
                        alert.show();
                    }else {
                        score = 0;
                        checkFromUser = true;
                        answersArray.clear();
                        reStart(stage);
                    }
                }
            });

            // letterBox Action
            letterBox.getHexagons().forEach(e -> {
                e.setOnMouseClicked(el -> {
                    wordText.appendText(e.getLetter());
                });
            });

            // Delete Action
            delete.setOnMouseClicked(e -> {
                char[] arr = wordText.getText().toCharArray();
                String text = "";
                for (int i=0;i<arr.length -1;i++){
                    text += arr[i];
                }
                wordText.setText(text);
            });

            // Shuffle Action
            shuffle.setOnMouseClicked(e -> {
                letterBox.shuffleLetters(dictionary.getSevenLetters());
            });

            // Enter Action
            enter.setOnMouseClicked(e -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                String word = wordText.getText().toLowerCase(Locale.ROOT);
                if(word.length() < 4){
                    alert.setHeaderText("The word does not include at least four letters.");
                    alert.show();
                }else if(isIncludeDiffrentLetter(word)){
                    alert.setHeaderText("The word includes letters not in the beehive.");
                    alert.show();
                }else if(!isIncludeCenter(word)){
                    alert.setHeaderText("The word does not include the center letter.");
                    alert.show();
                }else if(!dictionary.getAnswerWords().contains(word)){
                    alert.setHeaderText("The word is not in the dictionary.");
                    alert.show();
                }else if(answersArray.contains(word)){
                    alert.setHeaderText("The user has already found the word and is not allowed to score it twice.");
                    alert.show();
                }else {
                    if(dictionary.getPangramWords().contains(word)){
                        score += 7;
                    }else {
                        score += word.length() - 3;
                    }
                    changeRange(rangeLbl,rangeLine);
                    appendAnswer(word,answersArea);
                    if(score == dictionary.getScore()) {
                        alert1.setTitle("You Win");
                        alert1.setHeaderText("Excellent, you completed the puzzle successfully");
                        alert1.setContentText("You have found all the word that contain the letters");
                        alert1.show();
                    }
                }
                wordText.clear();
                wordText.requestFocus();
            });


        // End Action

        // Creat Scene and Stage
        Scene scene = new Scene(root,MainPane.width,MainPane.height);
        stage.setScene(scene);
        stage.setTitle("Spelling Bee");
        stage.show();

    }

    /* Start Methods */

    // Append Answer in textArea
    public void appendAnswer(String word,TextArea answersArea) {
        answersArray.add(word);
        answersArea.clear();
        answersArea.appendText("  You have found " + answersArray.size() + " word  and your score is " + score +"\n");
        for(int i=0;i<answersArray.size();i++){
            answersArea.appendText(i+1 + "- " + answersArray.get(i) + "\n");
        }
    }

    // Change tha range
    public void changeRange(Label rangeLabel, RangeLine rangeLine) {
        int number = dictionary.getScore() / 7;
        if(score < number){
            rangeLine.getCircles().get(0).setFill(Color.rgb(247,218,33));
        }else if(score < number*2){
            rangeLabel.setText("Good Start");
            rangeLine.getLines().get(0).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(1).setFill(Color.rgb(247,218,33));
        }else if(score < number*3){
            rangeLabel.setText("Moving Up");
            rangeLine.getLines().get(1).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(2).setFill(Color.rgb(247,218,33));
        }else if(score < number*4) {
            rangeLabel.setText("Good");
            rangeLine.getLines().get(2).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(3).setFill(Color.rgb(247,218,33));
        }else if(score < number*5) {
            rangeLabel.setText("Solid");
            rangeLine.getLines().get(3).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(4).setFill(Color.rgb(247,218,33));
        }else if(score < number*6) {
            rangeLabel.setText("Nice");
            rangeLine.getLines().get(4).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(5).setFill(Color.rgb(247,218,33));
        }else if(score < number*7) {
            rangeLabel.setText("Great");
            rangeLine.getLines().get(5).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(6).setFill(Color.rgb(247,218,33));
        }else if(score < dictionary.getScore()) {
            rangeLabel.setText("Amazing");
            rangeLine.getLines().get(6).setStroke(Color.rgb(247,218,33));
            rangeLine.getCircles().get(7).setFill(Color.rgb(247,218,33));
        }else if(score == dictionary.getScore()) {
            rangeLabel.setText("Genius");
            rangeLine.getLines().get(7).setStroke(Color.rgb(247,218,33));
            rangeLine.getRectangle().setFill(Color.rgb(247,218,33));
        }
    }

    // Is the Word Include the center letter
    public Boolean isIncludeCenter(String word) {
        char[] chars = word.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(chars[i] == dictionary.getSevenLetters().get(2))
                return true;
        }
        return false;
    }

    // Is the world Include letter not in the beehive
    public boolean isIncludeDiffrentLetter(String word) {
        char[] chars = word.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(!dictionary.getSevenLetters().contains(chars[i]))
                return true;
        }
        return false;
    }

    // Restart Method
    public void reStart(Stage stage) {
        try {
            start(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // Filter User Letters
    public ArrayList<Character> filterUserLetters(String userWord) {
        ArrayList<Character> filterArr = new ArrayList<>();
        char[] lettersArr = userWord.toCharArray();
        for(int i=0;i<lettersArr.length;i++){
            filterArr.add(lettersArr[i]);
        }
        return filterArr;
    }

    // isIncludeTurkishLetters
    public boolean isIncludeTurkishLetters(String word) {
        ArrayList<Character> letterArr = filterUserLetters(word);
        return dictionary.getTurkishLetters().containsAll(letterArr);
    }

    /* End Methods */

    /* Setter And Getter Methods */

    public void setDectionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}