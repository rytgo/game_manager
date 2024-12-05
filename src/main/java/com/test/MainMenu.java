package com.test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainMenu {
    private HighScoresManager highScoresManager;
    private LoginManager loginManager;
    private String user;

    public MainMenu(HighScoresManager highScoresManager, LoginManager loginManager, String user) {
        this.highScoresManager = highScoresManager;
        this.loginManager = loginManager;
        this.user = user;
    }

    // Helper method to get top scores for a specific game
    private List<Map.Entry<String, Integer>> getTopScores(String game) {
        return highScoresManager.getScores()
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get(game)))
                .filter(entry -> entry.getValue() != null) // Ensure the score exists
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Sort descending by score
                .limit(5) // Get the top 5
                .collect(Collectors.toList());
    }

    public VBox launchMainMenu(Stage stage) {
        VBox root = new VBox();
        root.getStyleClass().add("container");

        Label title = new Label("Main Menu");
        title.getStyleClass().add("label");

        HBox scoresBox = new HBox();
        VBox blackjackScores = new VBox();
        VBox snakeScores = new VBox();

        Label blackjackTitle = new Label("BlackJack High Scores: ");
        Label snakeTitle = new Label("Snake High Scores: ");

        blackjackScores.getChildren().add(blackjackTitle);
        snakeScores.getChildren().add(snakeTitle);

        // Get top 5 scores for each game
        List<Map.Entry<String, Integer>> blackjackTopScores = getTopScores("BlackJack");
        List<Map.Entry<String, Integer>> snakeTopScores = getTopScores("Snake");

        // Populate BlackJack scores
        for (int i = 0; i < blackjackTopScores.size(); i++) {
            Map.Entry<String, Integer> entry = blackjackTopScores.get(i);
            Label scoreLabel = new Label((i + 1) + ". " + entry.getKey() + ": " + entry.getValue());
            blackjackScores.getChildren().add(scoreLabel);
        }

        // Populate Snake scores
        for (int i = 0; i < snakeTopScores.size(); i++) {
            Map.Entry<String, Integer> entry = snakeTopScores.get(i);
            Label scoreLabel = new Label((i + 1) + ". " + entry.getKey() + ": " + entry.getValue());
            snakeScores.getChildren().add(scoreLabel);
        }

        scoresBox.getChildren().addAll(blackjackScores, snakeScores);

        // Add buttons for games
        Button blackjackButton = new Button("Play BlackJack");
        Button snakeButton = new Button("Play Snake");
        blackjackButton.setOnAction(e -> System.out.println("Launching BlackJack game...")); // Placeholder
        snakeButton.setOnAction(e -> System.out.println("Launching Snake game...")); // Placeholder

        // Add logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> stage.getScene().setRoot(loginManager.getLoginScreen(stage)));

        root.getChildren().addAll(title, scoresBox, blackjackButton, snakeButton, logoutButton);
        return root;
    }
}
