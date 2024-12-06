package com.test.blackjack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class BlackJackUI {
    private final BlackJack blackJack = new BlackJack();
    private final Button startGame = new Button("Start Game");
    private final Button hit = new Button("Hit");
    private final Button stand = new Button("Stand");
    private final TextField messageField = new TextField("Choose a chip to select your bet...");
    private final Label newGameMessage = new Label("Press 'New Round' to start a new round...");
    private final HBox userHand = new HBox(10);
    private final HBox computerOneHand = new HBox(10);
    private final HBox computerTwoHand = new HBox(10);
    private final HBox dealerHand = new HBox(10);
    private final HBox hitAndStand = new HBox(10);
    private final Label result = new Label();
    private final VBox userVBox = new VBox(10);
    private final Label userTotal = new Label();
    private final Label computerOneTotal = new Label();
    private final Label computerTwoTotal = new Label();
    private final String userName;
    private boolean roundPlaying = false;
    private final VBox computerOneVBox = new VBox(10);
    private final VBox computerTwoVBox = new VBox(10);
    private final VBox dealerVBox = new VBox(10);
    private Label messageArea = new Label();
    private Label resultOne = new Label();
    private Label resultTwo = new Label();

    public BlackJackUI(String userName) {
        this.userName = userName;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1280, 920);

        // Create buttons for New Game, Save Game, View Scores and Go back to Main Menu
        Button newRound = new Button("New Round");
        Button saveGame = new Button("Save Game");
        Button backToMainMenu = new Button("Main Menu");

        // Set button styles
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newRound, saveGame, backToMainMenu);

        // Create VBox to hold the players' spots
        // userVBox
        Label userLabel = new Label(getUserName());
        Label userBet = new Label("Bet: $0");
        userTotal.setText("Balance: $1000");
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().addAll(userLabel, userBet, userTotal);

        // dealerVBox
        Label dealerLabel = new Label("Dealer");
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        Label computerOneLabel = new Label("Computer 1");
        Label computerOneBet = new Label("Bet: $0");
        computerOneTotal.setText("Balance: $1000");
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().addAll(computerOneLabel, computerOneBet, computerOneTotal);

        // computerTwoVBox
        Label computerTwoLabel = new Label("Computer 2");
        Label computerTwoBet = new Label("Bet: $0");
        computerTwoTotal.setText("Balance: $1000");
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().addAll(computerTwoLabel, computerTwoBet, computerTwoTotal);

        // Create deck and background images
        ImageView backImage = setImageView("back.png");
        ImageView backgroundImage = setImageView("background.png");

        // Set deck image size
        backImage.setFitHeight(120);
        backImage.setFitWidth(85);

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
        chip10.setFitHeight(60);
        chip20.setFitHeight(60);
        chip50.setFitHeight(60);
        chip100.setFitHeight(60);

        // Create an HBox for chips
        HBox chips = new HBox(chip10Box, chip20Box, chip50Box, chip100Box);
        chips.getStyleClass().add("chips");

        // Set the CSS file for the scene
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());

        AnchorPane.setTopAnchor(borderPane, 20.0);
        AnchorPane.setBottomAnchor(borderPane, 20.0);
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        AnchorPane.setRightAnchor(borderPane, 20.0);

        AnchorPane.setTopAnchor(chips, 10.0);
        AnchorPane.setBottomAnchor(chips, 10.0);
        AnchorPane.setRightAnchor(chips, -50.0);

        root.getChildren().addAll(borderPane, chips, buttons);

        // Initialize a new game
        newRound.setOnAction(e -> {

            if (roundPlaying) {
                showAlert("Warning", "Round is not over!", "The round is not over. Finish the round before starting a new one.");
                return;
            }

            // Remove the previous game UI elements
            userHand.getChildren().clear();
            computerOneHand.getChildren().clear();
            computerTwoHand.getChildren().clear();
            dealerHand.getChildren().clear();

            // Reset bets and balances
            userBet.setText("Bet: $0");
            computerOneBet.setText("Bet: $0");
            computerTwoBet.setText("Bet: $0");

            // Hide Hit and Stand buttons
            userVBox.getChildren().remove(hitAndStand);

            // Remove the result and new game message
            userVBox.getChildren().remove(newGameMessage);
            userVBox.getChildren().remove(result);
            computerOneVBox.getChildren().remove(resultOne);
            computerTwoVBox.getChildren().remove(resultTwo);

            // Show the chips and message field and start game button
            chips.setVisible(true);
            messageField.setVisible(true);

            // Show a message about selecting a bet
            messageField.setEditable(false);
            messageField.setId("custom-label");
            if (!root.getChildren().contains(messageField)) {
                root.getChildren().add(messageField);
            }

            AnchorPane.setBottomAnchor(messageField, 180.0);
            AnchorPane.setLeftAnchor(messageField, 60.0);
            AnchorPane.setRightAnchor(messageField, 60.0);

            // Reset the game
            blackJack.resetGame();

            addChipClickHandler(chip10, 10, messageField, userBet, root);
            addChipClickHandler(chip20, 20, messageField, userBet, root);
            addChipClickHandler(chip50, 50, messageField, userBet, root);
            addChipClickHandler(chip100, 100, messageField, userBet, root);
        });

        // Start Game button function
        startGame.setOnAction(e -> {

            roundPlaying = true;

            // Hide start game, message field and chips
            startGame.setVisible(false);
            messageField.setVisible(false);
            chips.setVisible(false);

            // Random bet for computers
            int betOne = blackJack.getComputerOne().randomBet();
            computerOneBet.setText("Bet: $" + betOne);
            blackJack.getComputerOne().setBet(betOne);

            int betTwo = blackJack.getComputerTwo().randomBet();
            computerTwoBet.setText("Bet: $" + betTwo);
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
            roundPlaying = true;
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
            roundPlaying = true;
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
    public ImageView setImageView(String imageName) {
        File file = new File("blackjack_images/" + imageName);
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);

        return imageView;
    }

    // Helper method to add chip click handlers
    private void addChipClickHandler(ImageView chip, int betAmount, TextField messageField, Label userBet, AnchorPane root) {
        chip.setOnMouseClicked(e -> {

            roundPlaying = true;

            // Show start game button
            startGame.setVisible(true);

            blackJack.getHuman().setBet(betAmount); // Set the bet
            userBet.setText("Bet: $" + betAmount);
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
        cardImageView.setFitHeight(120);  // Set size of the card image
        cardImageView.setFitWidth(80);
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

        // Start the computer and dealer turns in sequence
        playComputerTurn(blackJack.getComputerOne(), computerOneHand, computerOneVBox, () ->
                playComputerTurn(blackJack.getComputerTwo(), computerTwoHand, computerTwoVBox, this::dealerPlay));
    }

    // Helper method to handle computer turn and callback after completion
    private void playComputerTurn(Player player, HBox hand, VBox playerBox, Runnable callback) {
        Timeline playerTimeline = createPlayerTimeline(player, hand, playerBox);
        playerTimeline.setOnFinished(e -> {
            playerBox.setId(null);
            playerBox.getChildren().remove(messageArea);
            callback.run();  // Call the next player or dealer turn
        });
        playerTimeline.play();
    }

    // Create a timeline for a specific player's turn
    private Timeline createPlayerTimeline(Player player, HBox hand, VBox playerBox) {
        Timeline timeline = new Timeline();
        int cardDelay = 2000;  // Delay for card animations

        playerBox.setId("styled-vbox");
        player.play(blackJack.getDeck());

        if (player.getHand().size() == 2) {
            // Player stands immediately if they have Blackjack or >= 16
            if (player.calculateTotal() == 21) {
                messageArea = new Label(player.getName() + " has Blackjack!");
            } else if (player.calculateTotal() >= 16) {
                messageArea = new Label(player.getName() + " stands!");
            }
            messageArea.setId("custom-label");
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay),
                    event -> playerBox.getChildren().add(messageArea)
            ));
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay * 2), // Delay to allow the message to be visible
                    event -> playerBox.getChildren().remove(messageArea)
            ));
        } else {
            // Handle "Hit" animations
            for (int i = 2; i < player.getHand().size(); i++) {
                int index = i;
                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * (index - 1) + 300),
                        event -> {
                            hand.getChildren().add(createCardImage(player.getHand().get(index)));
                            messageArea = new Label(player.getName() + " hits!");
                            messageArea.setId("custom-label");
                            playerBox.getChildren().add(messageArea);
                        }
                ));

                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * index),
                        event -> playerBox.getChildren().remove(messageArea)
                ));
            }
            showStandMessage(player, playerBox, timeline, cardDelay);
        }
        return timeline;
    }

    // Show the "Stand" or "Busted" message after a player's turn
    private void showStandMessage(Player player, VBox playerVBox, Timeline timeline, int cardDelay) {
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(cardDelay * (player.getHand().size() - 1) + 500),
                event -> {
                    String message = player.calculateTotal() > 21 ? player.getName() + " busted!" : player.getName() + " stands!";
                    messageArea = new Label(message);
                    messageArea.setId("custom-label");
                    playerVBox.getChildren().add(messageArea);
                }
        ));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(cardDelay * (player.getHand().size() + 1)),
                event -> playerVBox.getChildren().remove(messageArea)
        ));
    }

    // Dealer's turn logic
    private void dealerPlay() {
        if (allPlayersBusted()) {
            revealDealerCard();
            showResult();
            return;
        }

        Timeline dealerTimeline = new Timeline();
        dealerVBox.setId("styled-vbox");

        blackJack.getDealer().play(blackJack.getDeck());
        revealDealerCard();
        int cardDelay = 2000;

        if (blackJack.getDealer().getHand().size() == 2) {
            // Dealer stands immediately if they only have 2 cards
            if (blackJack.getDealer().calculateTotal() == 21) {
                messageArea = new Label(blackJack.getDealer().getName() + " has Blackjack!");
            } else if (blackJack.getDealer().calculateTotal() >= 17) {
                messageArea = new Label(blackJack.getDealer().getName() + " stands!");
            }
            messageArea.setId("custom-label");
            dealerTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay),
                    event -> dealerVBox.getChildren().add(messageArea)
            ));
            dealerTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay * 2), // Delay to allow the message to be visible
                    event -> dealerVBox.getChildren().remove(messageArea)
            ));
        } else {
            for (int i = 2; i < blackJack.getDealer().getHand().size(); i++) {
                int index = i;
                dealerTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * (index - 1) + 300),
                        event -> {
                            dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(index)));
                            messageArea = new Label(blackJack.getDealer().getName() + " hits!");
                            messageArea.setId("custom-label");
                            dealerVBox.getChildren().add(messageArea);
                        }
                ));

                dealerTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * index),
                        event -> dealerVBox.getChildren().remove(messageArea)
                ));
            }
            showStandMessage(blackJack.getDealer(), dealerVBox, dealerTimeline, cardDelay);
        }
        dealerTimeline.setOnFinished(e -> {
                showResult();
                dealerVBox.setId(null);
                });
        dealerTimeline.play();
    }

    // Check if all players are busted
    private boolean allPlayersBusted() {
        return blackJack.getHuman().getTotal() > 21 &&
                blackJack.getComputerOne().getTotal() > 21 &&
                blackJack.getComputerTwo().getTotal() > 21;
    }

    // Helper method to show the result of the game
    private void showResult() {
        customResult(blackJack.getComputerOne(), resultOne, computerOneTotal, computerOneVBox);
        customResult(blackJack.getComputerTwo(), resultTwo, computerTwoTotal, computerTwoVBox);
        customResult(blackJack.getHuman(), result, userTotal, userVBox);
        newGameMessage.setId("custom-label");
        userVBox.getChildren().add(newGameMessage);
        roundPlaying = false;
    }

    // Helper method to custom the result label of the game
    private void customResult(Player player, Label resultLabel, Label totalLabel, VBox playerVBox) {
        resultLabel.setText(blackJack.determineWinner(player));
        resultLabel.setId("custom-label");
        totalLabel.setText("Balance: $" + player.getMoney());
        playerVBox.getChildren().add(resultLabel);
    }
}


