package com.game.blackjack;

import com.game.HighScoresManager;
import com.game.ToolbarManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;
import com.game.MainMenu;

public class BlackjackMainMenu {
    private String userName;
    private MainMenu menu;
    private HighScoresManager highScoresManager;
    private ToolbarManager toolbarManager = new ToolbarManager();

    public BlackjackMainMenu(String userName, MainMenu menu, HighScoresManager highScoresManager) {
        this.userName = userName;
        this.menu = menu;
        this.highScoresManager = highScoresManager;
    }

    // Getters
    public String getName() {
        return userName;
    }

    public MainMenu getMenu() {
        return menu;
    }

    public HighScoresManager getHighScoresManager(){
        return this.highScoresManager;
    }

    public void start(Stage primaryStage) {
        // Create BlackJackUI instance
        BlackJackUI blackJackUI = new BlackJackUI(this.userName, menu, this);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        // Create background image for main menu
        ImageView backgroundImage = blackJackUI.setImageView("main_background.jpeg");
        backgroundImage.setFitWidth(650);
        backgroundImage.setFitHeight(1050);
        root.getChildren().add(backgroundImage);

        // Add label for main menu
        Label title = new Label("Welcome to Blackjack!");
        title.setId("title");
        VBox holder = new VBox(10);
        holder.setAlignment(Pos.CENTER);
        holder.getChildren().add(title);

        // Create the buttons for the main menu
        Button newGame = new Button("New Game");
        Button loadGame = new Button("Load Game");
        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(newGame, loadGame);
        buttons.setAlignment(Pos.CENTER);
        holder.getChildren().add(buttons);
        newGame.setId("main-menu-button");
        loadGame.setId("main-menu-button");

        root.setCenter(holder);

        // Create the toolbar
        root.setTop(toolbarManager.createToolbar(primaryStage, root, menu));

        // Set the action for the New Game button
        newGame.setOnAction(e -> {
            // Call start method from BlackJackUI to start a new game
            blackJackUI.start(primaryStage);
            primaryStage.centerOnScreen();
        });

        // Set the action for the Load Game button
        loadGame.setOnAction(e -> {
            // Load the game and update the existing scene, instead of creating a new one
            blackJackUI.loadGame(primaryStage); // This method now works as intended
            primaryStage.centerOnScreen();
        });

        // Show the Blackjack main menu
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Game");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
