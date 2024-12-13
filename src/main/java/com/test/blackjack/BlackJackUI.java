package com.test.blackjack;

import com.test.ToolbarManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import com.test.MainMenu;

public class BlackJackUI {
    private final BlackJack blackJack = new BlackJack();
    private final Button startGame = new Button("Start Game");
    private final Button hit = new Button("Hit");
    private final Button stand = new Button("Stand");
    private final TextField messageField = new TextField("Choose a chip to select your bet");
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
    private final Label resultOne = new Label();
    private final Label resultTwo = new Label();
    private final Label userBet = new Label();
    private final Label computerOneBet = new Label();
    private final Label computerTwoBet = new Label();
    private final AnchorPane root = new AnchorPane();
    private Timeline timeline;
    private Timeline dealerTimeline;
    private Timeline playerTimeline;
    private BlackjackMainMenu main;
    private final MainMenu menu;
    private final HBox chips = new HBox();
    private final ImageView chip10 = setImageView("10-chip.png");
    private final ImageView chip20 = setImageView("20-chip.png");
    private final ImageView chip50 = setImageView("50-chip.png");
    private final ImageView chip100 = setImageView("100-chip.png");
    private final HBox buttons = new HBox();
    private final Button newRound = new Button("New Round");
    private final ImageView backImage = setImageView("back.png");
    private final ImageView backgroundImage = setImageView("background.png");

    public BlackJackUI(String userName, MainMenu menu) {
        this.userName = userName;
        this.menu = menu;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    public void start(Stage stage) {

        Scene scene = new Scene(root, 1280, 920);

        // Set the CSS file for the scene
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());

        // Initialize the game
        initializeGame();

        // Add New Round button to buttons HBox
        buttons.getChildren().addAll(newRound);

        // Function the New Round button
        setNewRound();

        // Display chips and message to tell user to select a chip
        displayChipsAndMessage();

        // Start Game button function
        startGame.setOnAction(e -> {

            blackJack.dealCard(); // Deal cards to all players

            // Set current turn to the user
            blackJack.setTurn(getUserName());

            // Highlight user is playing
            userVBox.setId("styled-vbox");

            startRound();

            dealerHand.getChildren().remove(1);  // Remove the second card from the dealer's hand
            dealerHand.getChildren().add(backImage);  // Add the back image to the dealer's hand
        });

        // Display Hit and Stand buttons
        setHit();
        setStand();

        // Set the stage
        stage.setTitle("Blackjack Game");
        stage.setScene(scene);
        stage.show();
    }

    // Load game function
    public void loadGame(Stage primaryStage) {
        // Create a label to instruct the user
        Label enterSaveStateLabel = new Label("Enter save state string here:");

        // Create a text area for the user to enter the save state
        TextArea saveStateArea = new TextArea();

        // Create a button to load the game
        Button loadButton = new Button("Load");

        // Set the action for the load button
        loadButton.setOnAction(e -> {
            String saveStateString = saveStateArea.getText();
            loadState(saveStateString);  // Load the game state

            updateUI(primaryStage); // Ensure UI reflects the loaded state

            ((Stage) loadButton.getScene().getWindow()).close();
        });

        // Create a VBox to hold the label, save state area, and load button
        VBox loadVBox = new VBox(10);
        loadVBox.getChildren().addAll(enterSaveStateLabel, saveStateArea, loadButton);
        loadVBox.setAlignment(Pos.CENTER);

        // Add a CSS class to apply the background color
        loadVBox.getStyleClass().add("load-vbox");

        // Create the scene and set the CSS file
        Scene loadScene = new Scene(loadVBox, 640, 480);
        loadScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());

        // Create a modal window for the save state input
        Stage loadStage = new Stage();
        loadStage.initModality(Modality.APPLICATION_MODAL);
        loadStage.initOwner(primaryStage);
        loadStage.setTitle("Load Game");
        loadStage.setScene(loadScene);
        loadStage.show();
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
            System.out.println("Chip clicked: " + betAmount);

            // Check if the user has enough money to bet
            if (!blackJack.getHuman().isValidBet(betAmount)) {
                showAlert("Invalid bet", "You do not have enough money to bet $" + betAmount + ".");
                return;
            }

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
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Make sure the alert blocks other windows
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    // Helper method to reveal the dealer's second card
    private void revealDealerCard() {
        dealerHand.getChildren().remove(1);  // Remove the back image
        dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(1)));  // Add the second card
    }

    // Helper method for computers and dealer to play and determine the winner
    private void notUserPlay() {
        userVBox.getChildren().remove(hitAndStand);

        // Un-highlight user is playing
        userVBox.setId(null);

        // Start the computer and dealer turns in sequence
        playComputerTurn(blackJack.getComputerOne(), computerOneHand, computerOneVBox, () ->
                playComputerTurn(blackJack.getComputerTwo(), computerTwoHand, computerTwoVBox, this::dealerPlay));
    }

    // Helper method to handle computer turn and callback after completion
    private void playComputerTurn(Player player, HBox hand, VBox playerBox, Runnable callback) {
        playerTimeline = createPlayerTimeline(player, hand, playerBox);
        playerTimeline.setOnFinished(e -> {
            playerBox.setId(null);
            playerBox.getChildren().remove(messageArea);
            callback.run();  // Call the next player or dealer turn
        });
        playerTimeline.play();
        blackJack.setTurn(player.getName());
    }

    // Helper method to create a timeline for a specific player's turn
    private Timeline createPlayerTimeline(Player player, HBox hand, VBox playerBox) {
        timeline = new Timeline();
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
                    e -> playerBox.getChildren().add(messageArea)
            ));
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay * 2), // Delay to allow the message to be visible
                    e -> playerBox.getChildren().remove(messageArea)
            ));
        } else {
            // Handle "Hit" animations
            for (int i = 2; i < player.getHand().size(); i++) {
                int index = i;
                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * (index - 1) + 300),
                        e -> {
                            hand.getChildren().add(createCardImage(player.getHand().get(index)));
                            messageArea = new Label(player.getName() + " hits!");
                            messageArea.setId("custom-label");
                            playerBox.getChildren().add(messageArea);
                        }
                ));

                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * index),
                        e -> playerBox.getChildren().remove(messageArea)
                ));
            }
            showStandMessage(player, playerBox, timeline, cardDelay);
        }
        return timeline;
    }

    // Helper method to show the "Stand" or "Busted" message after a player's turn
    private void showStandMessage(Player player, VBox playerVBox, Timeline timeline, int cardDelay) {
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(cardDelay * (player.getHand().size() - 1) + 500),
                e -> {
                    String message = player.calculateTotal() > 21 ? player.getName() + " is busted!" : player.getName() + " stands!";
                    messageArea = new Label(message);
                    messageArea.setId("custom-label");
                    playerVBox.getChildren().add(messageArea);
                }
        ));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(cardDelay * (player.getHand().size() + 1)),
                e -> playerVBox.getChildren().remove(messageArea)
        ));
    }

    // Dealer's turn logic
    private void dealerPlay() {
        blackJack.setTurn(blackJack.getDealer().getName());

        if (allPlayersBusted()) {
            revealDealerCard();
            showResult();
            return;
        }

        dealerTimeline = new Timeline();
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
                    e -> dealerVBox.getChildren().add(messageArea)
            ));
            dealerTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(cardDelay * 2), // Delay to allow the message to be visible
                    e -> dealerVBox.getChildren().remove(messageArea)
            ));
        } else {
            for (int i = 2; i < blackJack.getDealer().getHand().size(); i++) {
                int index = i;
                dealerTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * (index - 1) + 300),
                        e -> {
                            dealerHand.getChildren().add(createCardImage(blackJack.getDealer().getHand().get(index)));
                            messageArea = new Label(blackJack.getDealer().getName() + " hits!");
                            messageArea.setId("custom-label");
                            dealerVBox.getChildren().add(messageArea);
                        }
                ));

                dealerTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(cardDelay * index),
                        e -> dealerVBox.getChildren().remove(messageArea)
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
        blackJack.setTurn(null);

        customResult(blackJack.getComputerOne(), resultOne, computerOneTotal, computerOneVBox);
        customResult(blackJack.getComputerTwo(), resultTwo, computerTwoTotal, computerTwoVBox);
        customResult(blackJack.getHuman(), result, userTotal, userVBox);
        roundPlaying = false;

        if (blackJack.getHuman().getMoney() <= 0) {
            showResetWindow();
        }
    }

    // Helper method to custom the result label of the game
    private void customResult(Player player, Label resultLabel, Label totalLabel, VBox playerVBox) {
        resultLabel.setText(blackJack.determineWinner(player));
        resultLabel.setId("custom-label");
        totalLabel.setText("Balance: $" + player.getMoney());
        playerVBox.getChildren().add(resultLabel);
    }

    // Reset the game if user balance is insufficient
    public void showResetWindow() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You balance is insufficient to continue playing.");
            alert.setContentText("Would you like to start a new game?");

            // Make sure the alert blocks other windows
            alert.initModality(Modality.APPLICATION_MODAL);

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yes, no);

            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == yes) {
                blackJack.resetGame();
                userHand.getChildren().clear();
                computerOneHand.getChildren().clear();
                computerTwoHand.getChildren().clear();
                dealerHand.getChildren().clear();

                userTotal.setText("Balance: $1000");
                computerOneTotal.setText("Balance: $1000");
                computerTwoTotal.setText("Balance: $1000");

                userBet.setText("Bet: $0");
                computerOneBet.setText("Bet: $0");
                computerTwoBet.setText("Bet: $0");

                // Remove the result
                userVBox.getChildren().remove(result);
                computerOneVBox.getChildren().remove(resultOne);
                computerTwoVBox.getChildren().remove(resultTwo);
            }
        });
    }

    // Generate the save state string from the current game state
    public String saveState() {
        StringBuilder saveState = new StringBuilder();

        // Save each player's hand, balance, and bet
        saveState.append("User-name:").append(getUserName())
                .append(";Human-hand:").append(formatHand(blackJack.getHuman().getHand()))
                .append(";Balance:").append(blackJack.getHuman().getMoney())
                .append(";Bet:").append(blackJack.getHuman().getBet())
                .append("|");

        saveState.append("Computer-1-name:").append(blackJack.getComputerOne().getName())
                .append(";Computer-1-hand:").append(formatHand(blackJack.getComputerOne().getHand()))
                .append(";Balance:").append(blackJack.getComputerOne().getMoney())
                .append(";Bet:").append(blackJack.getComputerOne().getBet())
                .append("|");

        saveState.append("Computer-2-name:").append(blackJack.getComputerTwo().getName())
                .append(";Computer-2-hand:").append(formatHand(blackJack.getComputerTwo().getHand()))
                .append(";Balance:").append(blackJack.getComputerTwo().getMoney())
                .append(";Bet:").append(blackJack.getComputerTwo().getBet())
                .append("|");

        saveState.append("Dealer-name:").append(blackJack.getDealer().getName())
                .append(";Dealer-hand:").append(formatHand(blackJack.getDealer().getHand()))
                .append(";Balance:").append(blackJack.getDealer().getMoney())
                .append(";Bet:").append(blackJack.getDealer().getBet())
                .append("|");

        // Save whose turn it is
        saveState.append("Turn:").append(blackJack.getTurn());

        return saveState.toString();
    }

    // Helper method to format a player's hand as a string
    private String formatHand(List<Card> hand) {
        return hand.stream()
                .map(card -> card.getRank() + card.getSuit())
                .collect(Collectors.joining(","));
    }

    // Update the UI based on the loaded state
    private void loadState(String saveStateString) {
        String[] playerData = saveStateString.split("\\|");

        System.out.println(Arrays.toString(playerData));

        for (String data : playerData) {
            if (data.startsWith("User-name:")) {
                loadPlayerState(data, blackJack.getHuman());
            } else if (data.startsWith("Computer-1-name:")) {
                loadPlayerState(data, blackJack.getComputerOne());
            } else if (data.startsWith("Computer-2-name:")) {
                loadPlayerState(data, blackJack.getComputerTwo());
            } else if (data.startsWith("Dealer-name:")) {
                loadDealerState(data, blackJack.getDealer());
            } else if (data.startsWith("Turn:")) {
                String turn = data.split(":")[1];
                System.out.println(turn);
                blackJack.setTurn(turn);
            }
        }
    }

    // Helper method to update the UI based on the current game state
    private void updateUI(Stage stage) {
        root.getChildren().clear();
        Scene scene = new Scene(root, 1280, 920);

        // Set the CSS file for the scene
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());

        initializeGame();

        if (blackJack.getHuman().getMoney() <= 0) {
            showResetWindow();
            return;
        }

        if (roundPlaying) {
            showAlert("Round is not over", "The round is not over. Finish the round before starting a new one.");
            return;
        }

        // Display chips and message to tell user to select a chip
        displayChipsAndMessage();

        // Set current turn to the user
        blackJack.setTurn(blackJack.getTurn());

        // Highlight the current player whose turn it is
        String currentTurn = blackJack.getTurn(); // Get the name of the player whose turn it is

        for (Player player : blackJack.getPlayers()) {
            VBox correspondingVBox = getvBox(player);

            if (correspondingVBox != null) {
                // Set the ID for the player's VBox if it's their turn
                if (player.getName().equals(currentTurn)) {
                    correspondingVBox.setId("styled-vbox"); // Highlight the VBox
                } else {
                    correspondingVBox.setId(null); // Reset styles for other VBoxes
                }
            }
        }

        startRound();

        // Initialize a new round
        setNewRound();

        // Display Hit and Stand buttons
        setHit();
        setStand();

        // Set the stage
        stage.setScene(scene);
        stage.show();
        resumeTimelines();
    }

    private VBox getvBox(Player player) {
        VBox correspondingVBox = null;

        // Match the player to their VBox
        if (player.getName().equals(getUserName())) {
            correspondingVBox = userVBox;
        } else if (player.getName().equals("Computer 1")) {
            correspondingVBox = computerOneVBox;
        } else if (player.getName().equals("Computer 2")) {
            correspondingVBox = computerTwoVBox;
        } else if (player.getName().equals("Dealer")) {
            correspondingVBox = dealerVBox;
        }
        return correspondingVBox;
    }

    // Helper to load a player's state
    private void loadPlayerState(String data, Player player) {
        String[] parts = data.split(";");

        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid data format for player state: " + data);
        }

        try {
            // Parse name
            String name = extractValue(parts[0]);
            player.setName(name);

            // Parse hand
            String handString = extractValue(parts[1]); // Correctly extract hand
            List<Card> hand = parseHand(handString);
            player.setHand(hand);

            // Parse balance
            int balance = Integer.parseInt(extractValue(parts[2])); // Correctly extract balance
            player.setMoney(balance);

            // Parse bet
            int bet = Integer.parseInt(extractValue(parts[3])); // Correctly extract bet
            player.setBet(bet);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing player data: " + data, e);
        }
    }

    // Helper method to extract the value from a key-value pair
    private String extractValue(String part) {
        return part.split(":")[1].trim();
    }

    // Load dealer state
    private void loadDealerState(String section, Dealer dealer) {
        String[] attributes = section.split(";");
        for (String attribute : attributes) {
            if (attribute.startsWith("Dealer-name:")) {
                String name = attribute.split(":")[1];
                dealer.setName(name);
            } else if (attribute.startsWith("Dealer-hand:")) {
                String handString = attribute.split(":")[1];
                List<Card> hand = parseHand(handString);
                dealer.setHand(hand);
            }
        }
    }

    // Helper to parse a hand from a string
    private List<Card> parseHand(String handString) {
        return Arrays.stream(handString.split(","))
                .map(cardStr -> {
                    // Check if the rank is a two-character number (like "10")
                    String rank;
                    String suit;

                    // Check for two-character rank (i.e., "10" to "10")
                    if (cardStr.length() > 2 && Character.isDigit(cardStr.charAt(1))) {
                        // The rank is the first two characters (e.g., "10")
                        rank = cardStr.substring(0, 2);
                        // The suit is the rest of the string (after "10")
                        suit = cardStr.substring(2);
                    } else {
                        // The rank is the first character (e.g., "a", "k")
                        rank = cardStr.substring(0, 1);
                        // The suit is the rest of the string
                        suit = cardStr.substring(1);
                    }

                    int value = cardValue(rank); // Get the numeric value of the card
                    return new Card(suit, rank, value); // Create a new Card object with rank, suit, and value
                })
                .collect(Collectors.toList());
    }

    // Help method to get card value
    private int cardValue(String rank) {
        rank = rank.toLowerCase(); // Normalize to lowercase for consistency
        return switch (rank) {
            case "a" -> 11; // Ace
            case "j", "q", "k" -> 10; // Face cards
            default -> {
                // Validate and parse numeric ranks
                try {
                    int value = Integer.parseInt(rank);
                    if (value >= 2 && value <= 10) {
                        yield value; // Valid numeric rank
                    } else {
                        throw new IllegalArgumentException("Invalid rank value: " + rank);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid rank format: " + rank, e);
                }
            }
        };
    }

    private void pauseTimelines() {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
        }

        if (dealerTimeline != null && dealerTimeline.getStatus() == Animation.Status.RUNNING) {
            dealerTimeline.pause();
        }

        if (playerTimeline != null && playerTimeline.getStatus() == Animation.Status.RUNNING) {
            playerTimeline.pause();
        }
    }

    private void resumeTimelines() {
        Platform.runLater(() -> {
            if (timeline != null && timeline.getStatus() == Animation.Status.PAUSED) {
                timeline.play();
            }

            if (dealerTimeline != null && dealerTimeline.getStatus() == Animation.Status.PAUSED) {
                dealerTimeline.play();
            }

            if (playerTimeline != null && playerTimeline.getStatus() == Animation.Status.PAUSED) {
                playerTimeline.play();
            }
        });
    }

    // Helper method to initialize the game
    public void initializeGame() {
        // Create buttons for New Game, Save Game, View Scores and Go back to Main Menu
        Button saveGame = new Button("Save Game");
        Button backToMainMenu = new Button("Main Menu");

        buttons.getChildren().addAll(backToMainMenu, saveGame);

        // Create VBox to hold the players' spots
        // userVBox
        Label userLabel = new Label(getUserName());
        userBet.setText("Bet: $" + blackJack.getHuman().getBet());
        userTotal.setText("Balance: $1000");
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().addAll(userLabel, userBet, userTotal);

        // dealerVBox
        Label dealerLabel = new Label("Dealer");
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        Label computerOneLabel = new Label("Computer 1");
        computerOneBet.setText("Bet: $" + blackJack.getComputerOne().getBet());
        computerOneTotal.setText("Balance: $1000");
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().addAll(computerOneLabel, computerOneBet, computerOneTotal);

        // computerTwoVBox
        Label computerTwoLabel = new Label("Computer 2");
        computerTwoBet.setText("Bet: $" + blackJack.getComputerTwo().getBet());
        computerTwoTotal.setText("Balance: $1000");
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().addAll(computerTwoLabel, computerTwoBet, computerTwoTotal);

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
        Label chip10Label = new Label("10");
        chip10Box.getChildren().addAll(chip10, chip10Label);
        chip10Box.setAlignment(Pos.CENTER);

        VBox chip20Box = new VBox();
        Label chip20Label = new Label("20");
        chip20Box.getChildren().addAll(chip20, chip20Label);
        chip20Box.setAlignment(Pos.CENTER);

        VBox chip50Box = new VBox();
        Label chip50Label = new Label("50");
        chip50Box.getChildren().addAll(chip50, chip50Label);
        chip50Box.setAlignment(Pos.CENTER);

        VBox chip100Box = new VBox();
        Label chip100Label = new Label("100");
        chip100Box.getChildren().addAll(chip100, chip100Label);
        chip100Box.setAlignment(Pos.CENTER);

        // Set chip image size
        chip10.setFitHeight(60);
        chip20.setFitHeight(60);
        chip50.setFitHeight(60);
        chip100.setFitHeight(60);

        // Create an HBox for chips
        chips.getChildren().addAll(chip10Box, chip20Box, chip50Box, chip100Box);
        chips.getStyleClass().add("chips");

        AnchorPane.setTopAnchor(borderPane, 20.0);
        AnchorPane.setBottomAnchor(borderPane, 20.0);
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        AnchorPane.setRightAnchor(borderPane, 20.0);

        AnchorPane.setTopAnchor(chips, 10.0);
        AnchorPane.setBottomAnchor(chips, 10.0);
        AnchorPane.setRightAnchor(chips, -50.0);

        root.getChildren().addAll(borderPane, chips, buttons);

        // Set the action for the Save Game button
        saveGame.setOnAction(e -> {
            String saveState = saveState();

            // Create an Alert of type INFORMATION
            pauseTimelines();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Game");
            alert.setHeaderText("Copy the save state string below to save your game state");

            // Make sure the alert blocks other windows
            alert.initModality(Modality.APPLICATION_MODAL);

            // Create a TextArea for the save state and make it non-editable
            TextArea saveStateArea = new TextArea(saveState);
            saveStateArea.setEditable(false);
            saveStateArea.setWrapText(true);

            // Set a preferred size for the TextArea
            saveStateArea.setPrefWidth(400);
            saveStateArea.setPrefHeight(200);

            // Add the TextArea to the alert's content
            alert.getDialogPane().setContent(saveStateArea);

            // Show the alert and wait for the user's interaction
            alert.showAndWait();

            Stage mainMenuStage = (Stage) root.getScene().getWindow();
            mainMenuStage.close();
            main = new BlackjackMainMenu(main.userName(), main.menu());
            main.start(new Stage());
        });

        backToMainMenu.setOnAction(e -> {
            Stage s = (Stage) root.getScene().getWindow();
            BorderPane rootLayout = new BorderPane();

            ToolbarManager toolbarManager = new ToolbarManager();
            rootLayout.setTop(toolbarManager.createToolbar(s, rootLayout, menu));

            VBox menuVBox = menu.launchMainMenu(s);
            rootLayout.setCenter(menuVBox);

            Scene menuScene = new Scene(rootLayout, 800, 600);
            menuScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("blackjack.css")).toExternalForm());
            s.setScene(menuScene);
        });
    }

    // Helper method New Round Button
    private void setNewRound() {
        newRound.setOnAction(e -> {
            if (blackJack.getHuman().getMoney() <= 0) {
                showResetWindow();
                return;
            }

            if (roundPlaying) {
                showAlert("Round is not over", "The round is not over. Finish the round before starting a new one.");
                return;
            }

            // Remove the previous game UI elements
            userHand.getChildren().clear();
            computerOneHand.getChildren().clear();
            computerTwoHand.getChildren().clear();
            dealerHand.getChildren().clear();
            startGame.setVisible(false);

            // Reset bets and balances
            userBet.setText("Bet: $0");
            computerOneBet.setText("Bet: $0");
            computerTwoBet.setText("Bet: $0");

            // Remove the result
            userVBox.getChildren().remove(result);
            computerOneVBox.getChildren().remove(resultOne);
            computerTwoVBox.getChildren().remove(resultTwo);

            // Reset the round
            blackJack.resetRound();

            // Display chips and message to tell user to select a chip
            displayChipsAndMessage();
        });
    }

    // Helper method to start the round
    public void startRound() {
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

            handUI.getChildren().clear();

            // Add each card in the player's hand to their UI HBox
            for (Card card : player.getHand()) {
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
    }

    // Helper method for Hit button
    public void setHit() {
        hit.setOnAction(e -> {
            roundPlaying = true;
            if (blackJack.getHuman().getTotal() == 21) {
                showAlert("You have 21 or Blackjack", "You have 21 or Blackjack! Stand to finish your turn.");
            } else {
                blackJack.getHuman().play(blackJack.getDeck());
                userHand.getChildren().add(createCardImage(blackJack.getHuman().getHand().getLast()));

                // If the user busts, auto lose
                if (blackJack.getHuman().getTotal() > 21) {
                    notUserPlay();
                }
            }
        });
    }

    // Helper method for Stand button
    public void setStand() {
        stand.setOnAction(e -> {
            roundPlaying = true;
            if (blackJack.getHuman().getTotal() < 16) {
                showAlert("You must hit", "You must hit! Your total is less than 16.");
            } else {
                notUserPlay();
            }
        });
    }

    // Helper method to display chips and tell user to select a bet
    private void displayChipsAndMessage() {
        userVBox.getChildren().remove(hitAndStand);

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

        addChipClickHandler(chip10, 10, messageField, userBet, root);
        addChipClickHandler(chip20, 20, messageField, userBet, root);
        addChipClickHandler(chip50, 50, messageField, userBet, root);
        addChipClickHandler(chip100, 100, messageField, userBet, root);
    }
}

