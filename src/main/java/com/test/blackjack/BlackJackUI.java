package com.test.blackjack;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class BlackJackUI extends Application {
    private BlackJack blackJack = new BlackJack();

    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);

        // Create a VBox to hold the players' spots
        VBox userVBox = new VBox();
        userVBox.getChildren().add(new Button(blackJack.getHuman().getName()));

        VBox dealerVBox = new VBox();
        dealerVBox.getChildren().add(new Button(blackJack.getDealer().getName()));

        VBox computerOneVBox = new VBox();
        computerOneVBox.getChildren().add(new Button(blackJack.getComputerOne().getName()));

        VBox computerTwoVBox = new VBox();
        computerTwoVBox.getChildren().add(new Button(blackJack.getComputerTwo().getName()));

        // Create images from setImageView method
        ImageView deckImage = BlackJackUI.setImageView("blackjack_images", "deck.png", "fileOne");
        ImageView backgroundImage = BlackJackUI.setImageView("blackjack_images", "background.png", "fileTwo");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(deckImage);
        borderPane.setTop(userVBox);
        borderPane.setLeft(dealerVBox);
        borderPane.setRight(computerOneVBox);
        borderPane.setBottom(computerTwoVBox);


        backgroundImage.setFitHeight(1911);
        backgroundImage.setFitWidth(1940);

        root.getChildren().addAll(backgroundImage, borderPane);
        borderPane.getBottom();

        // Set the players' spots

        stage.setScene(scene);
        stage.show();

    }

    // Set ImageView met
    public static ImageView setImageView(String folderName, String imageName, String fileName) {
        File file = new File(folderName + "/" + imageName);
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);

        return imageView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
