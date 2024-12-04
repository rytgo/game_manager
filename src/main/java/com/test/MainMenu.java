package com.test;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainMenu{
    private String highScoresFile;
    private LoginManager loginManager;
    private String user;

    public MainMenu(String highScoresFile, LoginManager loginManager, String user){
        this.highScoresFile = highScoresFile;
        this.loginManager = loginManager;
        this.user = user;
    }

    //Note that Map.Entry allows us to sort by value. Map.Entry allows you to access each pair as an object.
    //Once you sort Map.Entry objects in a list, it becomes a list of sorted pairs (Username, Score)
    private List<Map.Entry<String, Integer>> loadHighScores(){
        Map<String, Integer> scores = new HashMap<>(); 
        try{
            List<String> lines = Files.readAllLines(Paths.get(highScoresFile));
            for (String l: lines){
                String[] parts = l.split(":");
                if (parts.length == 2){
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e){
            System.out.println("Error loading high scores: " + e.getMessage());
        }

        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return sortedScores;
    }

    public VBox launchMainMenu(Stage stage){
        VBox root = new VBox();
        root.getStyleClass().add("container");

        Label title = new Label("Main Menu");
        title.getStyleClass().add("label");

        HBox scoresBox = new HBox();
        VBox blackjackScores = new VBox();
        VBox snakeScores = new VBox();
        
        return root;

    }
}