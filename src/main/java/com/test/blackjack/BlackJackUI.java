package com.test.blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.util.Objects;

public class BlackJackUI extends Application {
    private final BlackJack blackJack = new BlackJack();

    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1911, 1940);

        // Create buttons for New Game, Stop Game, and Save Game
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
        Label userLabel = new Label(blackJack.getHuman().getName());
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().add(userLabel);

        // dealerVBox
        VBox dealerVBox = new VBox();
        Label dealerLabel = new Label(blackJack.getDealer().getName());
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        VBox computerOneVBox = new VBox();
        Label computerOneLabel = new Label(blackJack.getComputerOne().getName());
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().add(computerOneLabel);

        // computerTwoVBox
        VBox computerTwoVBox = new VBox();
        Label computerTwoLabel = new Label(blackJack.getComputerTwo().getName());
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().add(computerTwoLabel);

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

        root.getChildren().addAll(backgroundImage, borderPane, chips, buttons);

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
