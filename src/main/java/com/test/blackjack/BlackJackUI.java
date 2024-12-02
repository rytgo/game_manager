package com.test.blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.util.Objects;

public class BlackJackUI extends Application {
    private BlackJack blackJack;

    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 600);

        // Create buttons for New Game, Save Game, View Scores and Go back to Main Menu
        Button newGame = createButton("New Game");
        Button saveGame = createButton("Save Game");
        Button viewGameScores = createButton("View Scores");
        Button backToMainMenu = createButton("Main Menu");

        // Set button styles
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newGame, saveGame, viewGameScores, backToMainMenu);

        // Create VBox to hold the players' spots
        // userVBox
        VBox userVBox = new VBox();
        Label userLabel = new Label("userName");
        Label userBet = new Label("Bet: 0");
        Label userTotal = new Label("Total: 1000");
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().addAll(userLabel, userBet, userTotal);

        // dealerVBox
        VBox dealerVBox = new VBox();
        Label dealerLabel = new Label("Dealer");
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        VBox computerOneVBox = new VBox();
        Label computerOneLabel = new Label("Computer 1");
        Label computerOneBet = new Label("Bet: 0");
        Label computerOneTotal = new Label("Total: 1000");
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().addAll(computerOneLabel, computerOneBet, computerOneTotal);

        // computerTwoVBox
        VBox computerTwoVBox = new VBox();
        Label computerTwoLabel = new Label("Computer 2");
        Label computerTwoBet = new Label("Bet: 0");
        Label computerTwoTotal = new Label("Total: 1000");
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().addAll(computerTwoLabel, computerTwoBet, computerTwoTotal);

        // Create deck and background images
        ImageView deckImage = setImageView("deck.png");
        ImageView backgroundImage = setImageView("background.png");

        // Set deck image size
        deckImage.setFitHeight(100);
        deckImage.setFitWidth(100);

        // Create a BorderPane to hold the players' spots
        BorderPane borderPane = new BorderPane(deckImage, dealerVBox, computerOneVBox, userVBox, computerTwoVBox);

        // Set the background image size
        backgroundImage.setFitHeight(1911);
        backgroundImage.setFitWidth(1940);

        root.getChildren().add(backgroundImage);

        // Set chip images and labels for each chip
        VBox chip10Box = new VBox();
        ImageView chip10 = setImageView("10-chip.png");
        Label chip10Label = new Label("$10");
        chip10Box.getChildren().addAll(chip10, chip10Label);
        chip10Box.setAlignment(Pos.CENTER);

        VBox chip20Box = new VBox();
        ImageView chip20 = setImageView("20-chip.png");
        Label chip20Label = new Label("$20");
        chip20Box.getChildren().addAll(chip20, chip20Label);
        chip20Box.setAlignment(Pos.CENTER);

        VBox chip50Box = new VBox();
        ImageView chip50 = setImageView("50-chip.png");
        Label chip50Label = new Label("$50");
        chip50Box.getChildren().addAll(chip50, chip50Label);
        chip50Box.setAlignment(Pos.CENTER);

        VBox chip100Box = new VBox();
        ImageView chip100 = setImageView("100-chip.png");
        Label chip100Label = new Label("$100");
        chip100Box.getChildren().addAll(chip100, chip100Label);
        chip100Box.setAlignment(Pos.CENTER);

        // Set chip image size
        chip10.setFitHeight(70);
        chip20.setFitHeight(70);
        chip50.setFitHeight(70);
        chip100.setFitHeight(70);

        // Create an HBox for chips
        HBox chips = new HBox(chip10Box, chip20Box, chip50Box, chip100Box);
        chips.getStyleClass().add("chips");

        // Set the CSS file for the scene
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());

        AnchorPane.setTopAnchor(borderPane, 40.0);
        AnchorPane.setBottomAnchor(borderPane, 40.0);
        AnchorPane.setLeftAnchor(borderPane, 40.0);
        AnchorPane.setRightAnchor(borderPane, 40.0);

        AnchorPane.setTopAnchor(chips, 40.0);
        AnchorPane.setBottomAnchor(chips, 40.0);
        AnchorPane.setLeftAnchor(chips, 40.0);
        AnchorPane.setRightAnchor(chips, 40.0);

        root.getChildren().addAll(borderPane, chips, buttons);

        // Start the game
        newGame.setOnAction(e -> {
            // Show a message about selecting a bet
            TextField messageField = new TextField("Choose a chip to select your bet...");
            messageField.setEditable(false);
            root.getChildren().add(messageField);

            AnchorPane.setBottomAnchor(messageField, 180.0);
            AnchorPane.setLeftAnchor(messageField, 60.0);
            AnchorPane.setRightAnchor(messageField, 60.0);

            // Initialize the BlackJack game object
            blackJack = new BlackJack();

            // Add event handlers for the chip clicks
            chip10.setOnMouseClicked(e1 -> {
                blackJack.getHuman().setBet(10); // Set the bet to $10
                userBet.setText("Bet: $10");
                messageField.setVisible(false);
            });

            chip20.setOnMouseClicked(e1 -> {
                blackJack.getHuman().setBet(20); // Set the bet to $20
                userBet.setText("Bet: $20");
                messageField.setVisible(false);
            });

            chip50.setOnMouseClicked(e1 -> {
                blackJack.getHuman().setBet(50); // Set the bet to $50
                userBet.setText("Bet: $50");
                messageField.setVisible(false);
            });

            chip100.setOnMouseClicked(e1 -> {
                blackJack.getHuman().setBet(100); // Set the bet to $100
                userBet.setText("Bet: $100");
                messageField.setVisible(false);
            });
        });

        // Set the stage
        stage.setTitle("BlackJack Game");
        stage.setScene(scene);
        stage.show();
    }

    // Set ImageView for images
    private ImageView setImageView(String imageName) {
        File file = new File("blackjack_images/" + imageName);
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);

        return imageView;
    }

    // Create a button with the specified size
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(130, 70);
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
