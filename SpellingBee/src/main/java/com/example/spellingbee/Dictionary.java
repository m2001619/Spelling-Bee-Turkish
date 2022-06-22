package com.example.spellingbee;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Dictionary {
    private int score = 0;
    private File file = new File("sozluk.txt");
    private ArrayList<String> dectionaryArray = new ArrayList<>();
    private ArrayList<Character> turkishLetters = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş','t', 'u', 'ü', 'v', 'y', 'z'));
    private ArrayList<Character> sevenLetters = new ArrayList<>();
    private ArrayList<String> pangramWords= new ArrayList<>();
    private ArrayList<String> answerWords = new ArrayList<>();
    public Dictionary() {
        // Read the words from the dectionary
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                dectionaryArray.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Start Methods */

    // Find Pangram Words in Dectionary
    public ArrayList<String> pangramWordInDectionary(){
        ArrayList<String> arr = new ArrayList<>();
        for(int i=0;i<dectionaryArray.size();i++){
            if(isPangram(dectionaryArray.get(i)))
                arr.add(dectionaryArray.get(i));
        }
        return arr;
    }

    // rand the letters and import it in the array of letters
    public ArrayList<Character> randomLetters() {
        ArrayList<Character> sevenLetters = new ArrayList<>();
        ArrayList<String> inDectionary = pangramWordInDectionary();
        Collections.shuffle(inDectionary);
        char[] pargramLetters = inDectionary.get(0).toCharArray();
        for(int i=0;i<pargramLetters.length;i++){
            sevenLetters.add(pargramLetters[i]);
        }
        return sevenLetters;
    }

    // Filter the words from the dectionary and improt it in the Answers Array
    public ArrayList<String> filterFromDectionary(ArrayList<String> dectionary, ArrayList<Character> sevenLetters) {
        ArrayList<String> answerWords = new ArrayList<>();
        for(int i=0;i<dectionary.size();i++){
            String word = dectionary.get(i);
            if(word.length() > 3){
                char[] arr = word.toCharArray();
                boolean importantChar = false;
                boolean check = false;
                for (int j=0;j<arr.length;j++){
                    check = sevenLetters.contains(arr[j]);
                    if(arr[j] == sevenLetters.get(2))
                        importantChar = true;
                    if(!check)
                        break;
                }
                if(check && importantChar)
                    answerWords.add(word);
            }
        }
        return answerWords;
    }
    // Return if the word is pangram word
    public boolean isPangram(String word) {
        char[] chars = word.toCharArray();
        if(word.length() == 7){
            for(int i=0;i<chars.length;i++){
                for(int j=0;j<chars.length;j++){
                    if(i != j && chars[j] == chars[i])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    // upadate the score and import the pangram word in the Pangram words Array
    public void setScoreAndParagramWords(){
        for(int i=0;i<answerWords.size();i++){
            if(isPangram(answerWords.get(i)) && !pangramWords.contains(answerWords.get(i))) {
                pangramWords.add(answerWords.get(i));
                score += 7;
            }else {
                score += answerWords.get(i).length() - 3;
            }
        }
    }

    // Play Automatically
    public void playAutomatically() {
        sevenLetters = randomLetters();
        answerWords = filterFromDectionary(dectionaryArray, sevenLetters);
        pangramWords.clear();
        score = 0;
        while(pangramWords.isEmpty() || (score<100 || score>400)){
            while(answerWords.size() < 20 || answerWords.size() > 80) {
                sevenLetters = randomLetters();
                answerWords = filterFromDectionary(dectionaryArray, sevenLetters);
            }
            setScoreAndParagramWords();
        }
        // Creat new file and import the arrays values in it to see there
        creatFile();
    }

    // Play with user Letters
    public void playLettersFromUser() {
        Collections.shuffle(sevenLetters);
        answerWords = filterFromDectionary(dectionaryArray, sevenLetters);
        setScoreAndParagramWords();
        for(int i = 0; i< sevenLetters.size(); i++){
            if(score == 0) {
                Collections.shuffle(sevenLetters);
                answerWords = filterFromDectionary(dectionaryArray, sevenLetters);
                setScoreAndParagramWords();
            }else {break;}
        }
        // Creat new file and import the arrays values in it to see there
        creatFile();
    }

    // File Methods
    public void creatFile() {

        try {
            FileWriter fileWriter = new FileWriter("cozum.txt");
            fileWriter.write("The answer words :" + getAnswerWords() + "\n");
            fileWriter.write("Number of answer words : " + getAnswerWords().size() + "\n");
            fileWriter.write("Pangram words : " + getPangramWords() + "\n");
            fileWriter.write("Score : " + getScore() + "\n");

            fileWriter.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //

    /* End Methods */

    /* Getter and Setter Methods */

    public int getScore() {
        return score;
    }

    public ArrayList<String> getDectionaryArray() {
        return dectionaryArray;
    }

    public ArrayList<Character> getTurkishLetters() {
        return turkishLetters;
    }

    public ArrayList<Character> getSevenLetters() {
        return sevenLetters;
    }

    public ArrayList<String> getPangramWords() {
        return pangramWords;
    }

    public ArrayList<String> getAnswerWords() {
        return answerWords;
    }


    public void setSevenLetters(ArrayList<Character> sevenLetters) {
        this.sevenLetters = sevenLetters;
    }

    public void setAnswerWords(ArrayList<String> answerWords) {
        this.answerWords = answerWords;
    }
}

