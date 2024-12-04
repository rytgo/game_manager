package com.test.blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class BlackJackUI extends Application {
    private BlackJack blackJack;
    private final Button startGame = new Button("Start Game");
    private final Button hit = new Button("Hit");
    private final Button stand = new Button("Stand");
    private final TextField messageField = new TextField("Choose a chip to select your bet...");
    private final HBox userHand = new HBox(10);
    private final HBox computerOneHand = new HBox(10);
    private final HBox computerTwoHand = new HBox(10);
    private final HBox dealerHand = new HBox(10);
    private final HBox hitAndStand = new HBox(10);

    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1800, 1200);

        // Create buttons for New Game, Save Game, View Scores and Go back to Main Menu
        Button newGame = new Button("New Game");
        Button saveGame = new Button("Save Game");
        Button loadGame = new Button("Load Game");
        Button backToMainMenu = new Button("Main Menu");

        // Set button styles
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newGame, saveGame, loadGame, backToMainMenu);

        // Create VBox to hold the players' spots
        // userVBox
        VBox userVBox = new VBox(10);
        Label userLabel = new Label("userName");
        Label userBet = new Label("Bet: 0");
        Label userTotal = new Label("Balance: 1000");
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().addAll(userLabel, userBet, userTotal);

        // dealerVBox
        VBox dealerVBox = new VBox(10);
        Label dealerLabel = new Label("Dealer");
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        VBox computerOneVBox = new VBox(10);
        Label computerOneLabel = new Label("Computer 1");
        Label computerOneBet = new Label("Bet: 0");
        Label computerOneTotal = new Label("Balance: 1000");
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().addAll(computerOneLabel, computerOneBet, computerOneTotal);

        // computerTwoVBox
        VBox computerTwoVBox = new VBox(10);
        Label computerTwoLabel = new Label("Computer 2");
        Label computerTwoBet = new Label("Bet: 0");
        Label computerTwoTotal = new Label("Balance: 1000");
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().addAll(computerTwoLabel, computerTwoBet, computerTwoTotal);

        // Create deck and background images
        ImageView backImage = setImageView("back.png");
        ImageView backgroundImage = setImageView("background.png");

        // Set deck image size
        backImage.setFitHeight(160);
        backImage.setFitWidth(100);

        // Create a BorderPane to hold the players' spots
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(userVBox);
        borderPane.setLeft(computerTwoVBox);
        borderPane.setRight(computerOneVBox);
        borderPane.setTop(dealerVBox);

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

        AnchorPane.setTopAnchor(borderPane, 20.0);
        AnchorPane.setBottomAnchor(borderPane, 20.0);
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        AnchorPane.setRightAnchor(borderPane, 20.0);

        AnchorPane.setTopAnchor(chips, 30.0);
        AnchorPane.setBottomAnchor(chips, 30.0);
        AnchorPane.setLeftAnchor(chips, 30.0);
        AnchorPane.setRightAnchor(chips, 30.0);

        root.getChildren().addAll(borderPane, chips, buttons);

        // Initialize a new game
        newGame.setOnAction(e -> {
            // Show a message about selecting a bet
            messageField.setEditable(false);
            root.getChildren().add(messageField);

            AnchorPane.setBottomAnchor(messageField, 180.0);
            AnchorPane.setLeftAnchor(messageField, 60.0);
            AnchorPane.setRightAnchor(messageField, 60.0);

            // Initialize the BlackJack game object
            blackJack = new BlackJack();

            addChipClickHandler(chip10, 10, messageField, userBet, root);
            addChipClickHandler(chip20, 20, messageField, userBet, root);
            addChipClickHandler(chip50, 50, messageField, userBet, root);
            addChipClickHandler(chip100, 100, messageField, userBet, root);
        });

        // Start Game button function
        startGame.setOnAction(e -> {
            // Remove the start game button, message field, and chips
            root.getChildren().remove(startGame);
            root.getChildren().remove(messageField);
            root.getChildren().remove(chips);
            // Random bet for computers
            computerOneBet.setText("Bet: $" + blackJack.getComputerOne().randomBet());
            computerTwoBet.setText("Bet: $" + blackJack.getComputerTwo().randomBet());

            // Create Hit and Stand buttons
            hitAndStand.getChildren().addAll(hit, stand);
            userVBox.getChildren().add(hitAndStand);
            hitAndStand.setAlignment(Pos.CENTER);

            blackJack.dealCard();
                List<HBox> playerHands = List.of(userHand, computerOneHand, computerTwoHand, dealerHand);
                userVBox.getChildren().add(userHand);
                dealerVBox.getChildren().add(dealerHand);
                computerOneVBox.getChildren().add(computerOneHand);
                computerTwoVBox.getChildren().add(computerTwoHand);
                List<Player> players = blackJack.getPlayers();

                // Iterate over players and their corresponding HBoxes
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    HBox handUI = playerHands.get(i); // Get the corresponding HBox

                    // Add each card in the player's hand to their UI HBox
                    for (Card card : player.getHand()) {
                        System.out.println(card.getRank() + card.getSuit());   // Debugging
                        handUI.getChildren().add(createCardImage(card));
                    }
                }
                dealerHand.getChildren().remove(1);  // Remove the second card from the dealer's hand
                dealerHand.getChildren().add(backImage);  // Add the back image to the dealer's hand

            // Set alignment for all HBoxes
            userHand.setAlignment(Pos.CENTER);
            computerOneHand.setAlignment(Pos.CENTER);
            computerTwoHand.setAlignment(Pos.CENTER);
            dealerHand.setAlignment(Pos.CENTER);

            // Calculate the total for all players
            blackJack.getHuman().calculateTotal();
            blackJack.getComputerOne().calculateTotal();
            blackJack.getComputerTwo().calculateTotal();
            blackJack.getDealer().calculateTotal();
        });

        // Hit button function
        hit.setOnAction(e -> {
            if (blackJack.getHuman().getTotal() == 21) {
                showAlert("Warning", "You have 21 or Blackjack!", "You have 21 or Blackjack! Stand to finish your turn.");
            } else if (blackJack.getHuman().getTotal() > 21) {
                showAlert("Warning", "You are busted!", "You are busted! Stand to finish your turn.");
            } else {
                blackJack.getHuman().setUserChoice("hit");
                blackJack.getHuman().play(blackJack.getDeck());
                userHand.getChildren().add(createCardImage(blackJack.getHuman().getHand().getLast()));
                System.out.println("Human total: " + blackJack.getHuman().getTotal());  // Debugging
            }
        });

        // Stand button function
        stand.setOnAction(e -> {
            if (blackJack.getHuman().getTotal() < 16) {
                showAlert("Warning", "You must hit!", "You must hit! Your total is less than 16.");
            } else {
                userVBox.getChildren().remove(hitAndStand);

                // Computer 1's turn to play
                blackJack.getComputerOne().play(blackJack.getDeck());
                for (int i = 2; i < blackJack.getComputerOne().getHand().size(); i++) {
                    computerOneHand.getChildren().add(createCardImage(blackJack.getComputerOne().getHand().get(i)));
                }

                // Computer 2's turn to play
                blackJack.getComputerTwo().play(blackJack.getDeck());
                for (int i = 2; i < blackJack.getComputerTwo().getHand().size(); i++) {
                    computerTwoHand.getChildren().add(createCardImage(blackJack.getComputerTwo().getHand().get(i)));
                }

                // Dealer's turn to play
                dealerHand.getChildren().remove(1);  // Remove the back image from the dealer's hand
                dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(1)));  // Add the second card to the dealer's hand
                blackJack.getDealer().play(blackJack.getDeck());
                for (int i = 2; i < blackJack.getDealer().getHand().size(); i++) {
                    dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(i)));
                }
            }
        });

        // Set the stage
        stage.setTitle("Blackjack Game");
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to set ImageView for images
    private ImageView setImageView(String imageName) {
        File file = new File("blackjack_images/" + imageName);
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);

        return imageView;
    }

    // Helper method to add chip click handlers
    private void addChipClickHandler(ImageView chip, int betAmount, TextField messageField, Label userBet, AnchorPane root) {
        chip.setOnMouseClicked(e -> {
            blackJack.getHuman().setBet(betAmount); // Set the bet
            userBet.setText("Bet: $" + betAmount);
            messageField.setVisible(false);
            root.getChildren().add(startGame);

            AnchorPane.setBottomAnchor(startGame, 250.0);
            AnchorPane.setLeftAnchor(startGame, 30.0);
            AnchorPane.setRightAnchor(startGame, 30.0);
        });
    }

    // Helper method to create card images
    private ImageView createCardImage(Card card) {
        String cardImageFile = "blackjack_images/" + card.getRank() + "-" + card.getSuit() + ".png";  // Ensure the correct path for your images
        ImageView cardImageView = new ImageView(new Image(new File(cardImageFile).toURI().toString()));
        cardImageView.setFitHeight(140);  // Set size of the card image
        cardImageView.setFitWidth(100);
        return cardImageView;
    }

    // Helper method for showing alert messages
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Get the Stage of the Alert and set fixed size
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setWidth(400); // Set desired width
        alertStage.setHeight(300); // Set desired height
        alertStage.setResizable(false); // Disable resizing

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
