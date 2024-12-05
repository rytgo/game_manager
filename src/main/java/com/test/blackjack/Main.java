package com.test.blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BlackJackUI blackJackUI = new BlackJackUI("userName");

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(getClass().getResource("blackjack.css").toExternalForm());

        // Create background image for main menu
        ImageView backgroundImage = blackJackUI.setImageView("main_background.jpeg");
        backgroundImage.setFitWidth(650);
        backgroundImage.setFitHeight(1050);
        root.getChildren().add(backgroundImage);

        // Add label for main menu
        Label title = new Label("Main Menu");
        StackPane.setAlignment(title, Pos.CENTER);
        title.getStyleClass().add("title");
        VBox holder = new VBox(10);
        holder.setAlignment(Pos.CENTER);
        holder.getChildren().add(title);
        root.getChildren().add(holder);

        // Create the buttons for main menu
        Button newGame = new Button("New Game");
        Button loadGame = new Button("Load Game");
        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(newGame, loadGame);
        buttons.setAlignment(Pos.CENTER);
        holder.getChildren().add(buttons);


        // Set the action for the New Game button
        newGame.setOnAction(e -> {
            blackJackUI.start(primaryStage);
        });


        // Show the Blackjack main menu
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Game");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
