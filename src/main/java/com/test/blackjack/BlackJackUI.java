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
    private final BlackJack blackJack = new BlackJack();
    private final Button startGame = new Button("Start Game");
    private final Button hit = new Button("Hit");
    private final Button stand = new Button("Stand");
    private final TextField messageField = new TextField("Choose a chip to select your bet...");
    private final TextField newGameMessage = new TextField("Press 'New Game' to start a new game...");
    private final HBox userHand = new HBox(10);
    private final HBox computerOneHand = new HBox(10);
    private final HBox computerTwoHand = new HBox(10);
    private final HBox dealerHand = new HBox(10);
    private final HBox hitAndStand = new HBox(10);
    private final TextField result = new TextField();
    private final VBox userVBox = new VBox(10);
    private final Label userTotal = new Label();
    private final Label computerOneTotal = new Label();
    private final Label computerTwoTotal = new Label();

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
        Label userLabel = new Label("userName");
        Label userBet = new Label("Bet: 0");
        userTotal.setText("Balance: 1000");
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
        computerOneTotal.setText("Balance: 1000");
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().addAll(computerOneLabel, computerOneBet, computerOneTotal);

        // computerTwoVBox
        VBox computerTwoVBox = new VBox(10);
        Label computerTwoLabel = new Label("Computer 2");
        Label computerTwoBet = new Label("Bet: 0");
        computerTwoTotal.setText("Balance: 1000");
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
        Label chip10Label = new Label("10");
        chip10Box.getChildren().addAll(chip10, chip10Label);
        chip10Box.setAlignment(Pos.CENTER);

        VBox chip20Box = new VBox();
        ImageView chip20 = setImageView("20-chip.png");
        Label chip20Label = new Label("20");
        chip20Box.getChildren().addAll(chip20, chip20Label);
        chip20Box.setAlignment(Pos.CENTER);

        VBox chip50Box = new VBox();
        ImageView chip50 = setImageView("50-chip.png");
        Label chip50Label = new Label("50");
        chip50Box.getChildren().addAll(chip50, chip50Label);
        chip50Box.setAlignment(Pos.CENTER);

        VBox chip100Box = new VBox();
        ImageView chip100 = setImageView("100-chip.png");
        Label chip100Label = new Label("100");
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
            // Remove the previous game UI elements
            userHand.getChildren().clear();
            computerOneHand.getChildren().clear();
            computerTwoHand.getChildren().clear();
            dealerHand.getChildren().clear();

            // Reset bets and balances
            userBet.setText("Bet: 0");
            computerOneBet.setText("Bet: 0");
            computerTwoBet.setText("Bet: 0");

            // Hide Hit and Stand buttons
            userVBox.getChildren().remove(hitAndStand);

            // Remove the result and new game message
            userVBox.getChildren().remove(newGameMessage);
            userVBox.getChildren().remove(result);

            // Show the chips and message field and start game button
            chips.setVisible(true);
            messageField.setVisible(true);

            // Show a message about selecting a bet
            messageField.setEditable(false);
            if (!root.getChildren().contains(messageField)) {
                root.getChildren().add(messageField);
            }

            AnchorPane.setBottomAnchor(messageField, 180.0);
            AnchorPane.setLeftAnchor(messageField, 60.0);
            AnchorPane.setRightAnchor(messageField, 60.0);

            // Restart the game
            blackJack.resetGame();

            addChipClickHandler(chip10, 10, messageField, userBet, root);
            addChipClickHandler(chip20, 20, messageField, userBet, root);
            addChipClickHandler(chip50, 50, messageField, userBet, root);
            addChipClickHandler(chip100, 100, messageField, userBet, root);
        });

        // Start Game button function
        startGame.setOnAction(e -> {

            // Hide start game, message field and chips
            startGame.setVisible(false);
            messageField.setVisible(false);
            chips.setVisible(false);

            // Random bet for computers
            int betOne = blackJack.getComputerOne().randomBet();
            computerOneBet.setText("Bet: " + betOne);
            blackJack.getComputerOne().setBet(betOne);

            int betTwo = blackJack.getComputerTwo().randomBet();
            computerTwoBet.setText("Bet: " + betTwo);
            blackJack.getComputerTwo().setBet(betTwo);

            // Deal 8 cards to 4 players
            blackJack.dealCard();

            List<HBox> playerHands = List.of(userHand, computerOneHand, computerTwoHand, dealerHand);

            if (!userVBox.getChildren().contains(userHand)) {
                userVBox.getChildren().add(userHand);
            }

            if (!computerOneVBox.getChildren().contains(computerOneHand)) {
                computerOneVBox.getChildren().add(computerOneHand);
            }

            if (!computerTwoVBox.getChildren().contains(computerTwoHand)) {
                computerTwoVBox.getChildren().add(computerTwoHand);
            }

            if (!dealerVBox.getChildren().contains(dealerHand)) {
                dealerVBox.getChildren().add(dealerHand);
            }

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

            // Set alignment for all HBoxes
            userHand.setAlignment(Pos.CENTER);
            computerOneHand.setAlignment(Pos.CENTER);
            computerTwoHand.setAlignment(Pos.CENTER);
            dealerHand.setAlignment(Pos.CENTER);

            // Create Hit and Stand buttons
            if (!userVBox.getChildren().contains(hitAndStand)) {
                if (hitAndStand.getChildren().isEmpty()) {
                    hitAndStand.getChildren().addAll(hit, stand);
                }
                userVBox.getChildren().add(hitAndStand);
                hitAndStand.setAlignment(Pos.CENTER);
            }

            // Calculate the total for all players
            blackJack.getHuman().calculateTotal();
            blackJack.getComputerOne().calculateTotal();
            blackJack.getComputerTwo().calculateTotal();
            blackJack.getDealer().calculateTotal();

            dealerHand.getChildren().remove(1);  // Remove the second card from the dealer's hand
            dealerHand.getChildren().add(backImage);  // Add the back image to the dealer's hand
        });

        // Hit button function
        hit.setOnAction(e -> {
            if (blackJack.getHuman().getTotal() == 21) {
                showAlert("Warning", "You have 21 or Blackjack!", "You have 21 or Blackjack! Stand to finish your turn.");
            } else {
                blackJack.getHuman().play(blackJack.getDeck());
                userHand.getChildren().add(createCardImage(blackJack.getHuman().getHand().getLast()));
                // If the user busts, auto lose
                if (blackJack.getHuman().getTotal() > 21) {
                    notUserPlay();
                }
            }
        });

        // Stand button function
        stand.setOnAction(e -> {
            if (blackJack.getHuman().getTotal() < 16) {
                showAlert("Warning", "You must hit!", "You must hit! Your total is less than 16.");
            } else {
                notUserPlay();
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
            // Show start game button
            startGame.setVisible(true);

            blackJack.getHuman().setBet(betAmount); // Set the bet
            userBet.setText("Bet: " + betAmount);
            messageField.setVisible(false);

            if (!root.getChildren().contains(startGame)) {
                root.getChildren().add(startGame);
                AnchorPane.setBottomAnchor(startGame, 250.0);
                AnchorPane.setLeftAnchor(startGame, 30.0);
                AnchorPane.setRightAnchor(startGame, 30.0);
            }
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

    // Helper method to reveal the dealer's second card
    private void revealDealerCard() {
        dealerHand.getChildren().remove(1);  // Remove the back image
        dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(1)));  // Add the second card
    }

    // Helper method for computers and dealer to play and determine the winner
    public void notUserPlay() {
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
        // If all players are busted, reveal the dealer's second card
        if (blackJack.getHuman().getTotal() > 21 && blackJack.getComputerOne().getTotal() > 21 && blackJack.getComputerTwo().getTotal() > 21) {
            revealDealerCard();
        } else {
            revealDealerCard();
            blackJack.getDealer().play(blackJack.getDeck());
            for (int i = 2; i < blackJack.getDealer().getHand().size(); i++) {
                dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(i)));
            }
        }

        // Determine the winner
        blackJack.determineWinner(blackJack.getComputerOne());
        computerOneTotal.setText("Balance: " + blackJack.getComputerOne().getMoney());

        blackJack.determineWinner(blackJack.getComputerTwo());
        computerTwoTotal.setText("Balance: " + blackJack.getComputerTwo().getMoney());

        result.setText(blackJack.determineWinner(blackJack.getHuman()));
        result.setEditable(false);
        newGameMessage.setEditable(false);
        userTotal.setText("Balance: " + blackJack.getHuman().getMoney());
        userVBox.getChildren().addAll(result, newGameMessage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
