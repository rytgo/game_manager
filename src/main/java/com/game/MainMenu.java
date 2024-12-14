package com.game;

import com.game.SnakeGame.SnakeGame;
import com.game.blackjack.BlackjackMainMenu;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MainMenu {
    private HighScoresManager highScoresManager;
    private LoginManager loginManager;
    private String user;
    private Consumer<Void> onLogout; // Callback for logout behavior

    public MainMenu(HighScoresManager highScoresManager, LoginManager loginManager, String user) {
        this.highScoresManager = highScoresManager;
        this.loginManager = loginManager;
        this.user = user;
    }

    // Set a callback for logout
    public void setOnLogout(Consumer<Void> onLogout) {
        this.onLogout = onLogout;
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

    Label title = new Label("Main Menu - Welcome " + user);
    title.getStyleClass().add("main-title");

    GridPane gridPane = new GridPane();
    gridPane.setHgap(20);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);

    // High Scores Section
    VBox highScoresBox = new VBox();
    highScoresBox.getStyleClass().add("high-scores-container");
    Label highScoresTitle = new Label("High Scores");
    highScoresTitle.getStyleClass().add("section-title");

    HBox scoresBox = new HBox(20);
    VBox blackjackScores = new VBox();
    VBox snakeScores = new VBox();

    Label blackjackTitle = new Label("Blackjack:");
    Label snakeTitle = new Label("Snake:");

    blackjackScores.getChildren().add(blackjackTitle);
    snakeScores.getChildren().add(snakeTitle);

    // Populate BlackJack scores
    List<Map.Entry<String, Integer>> blackjackTopScores = getTopScores("BlackJack");
    for (int i = 0; i < blackjackTopScores.size(); i++) {
        Map.Entry<String, Integer> entry = blackjackTopScores.get(i);
        Label scoreLabel = new Label((i + 1) + ". " + entry.getKey() + ": " + entry.getValue());
        blackjackScores.getChildren().add(scoreLabel);
    }

    // Populate Snake scores
    List<Map.Entry<String, Integer>> snakeTopScores = getTopScores("Snake");
    for (int i = 0; i < snakeTopScores.size(); i++) {
        Map.Entry<String, Integer> entry = snakeTopScores.get(i);
        Label scoreLabel = new Label((i + 1) + ". " + entry.getKey() + ": " + entry.getValue());
        snakeScores.getChildren().add(scoreLabel);
    }

    scoresBox.getChildren().addAll(blackjackScores, snakeScores);
    highScoresBox.getChildren().addAll(highScoresTitle, scoresBox);

    // Game Buttons Section
    VBox buttonsBox = new VBox(15);
    buttonsBox.getStyleClass().add("buttons-container");

    Button blackjackButton = new Button("Play Blackjack");
    Button snakeButton = new Button("Play Snake");

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> {
        App app = new App();
        app.start(stage);
    });

    buttonsBox.getChildren().addAll(blackjackButton, snakeButton, logoutButton);

    // Add both sections to the GridPane
    gridPane.add(highScoresBox, 0, 0);
    gridPane.add(buttonsBox, 1, 0);

    // Call Blackjack game for blackjackButton
    blackjackButton.setOnAction(e -> {
        BlackjackMainMenu blackJackUI = new BlackjackMainMenu(user, this);
        blackJackUI.start(stage);
    });

    // Call Snake game for snakeButton
    snakeButton.setOnAction(e -> {
        SnakeGame snakeGame = new SnakeGame(user, this, this.highScoresManager);
        snakeGame.start(stage);
    });

    root.getChildren().addAll(title, gridPane);
    return root;
}

    
}
