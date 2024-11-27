package com.test.blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.File;

public class BlackJackUI extends Application {
    private final BlackJack blackJack = new BlackJack();

    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1911, 1940);

        // Create a VBox to hold the players' spots

        // userVBox
        VBox userVBox = new VBox();
        Label userLabel = new Label(blackJack.getHuman().getName());
        setLabelStyle(userLabel);
        userVBox.setAlignment(Pos.CENTER);
        userVBox.getChildren().add(userLabel);

        // dealerVBox
        VBox dealerVBox = new VBox();
        Label dealerLabel = new Label(blackJack.getDealer().getName());
        setLabelStyle(dealerLabel);
        dealerVBox.setAlignment(Pos.CENTER);
        dealerVBox.getChildren().add(dealerLabel);

        // computerOneVBox
        VBox computerOneVBox = new VBox();
        Label computerOneLabel = new Label(blackJack.getComputerOne().getName());
        setLabelStyle(computerOneLabel);
        computerOneVBox.setAlignment(Pos.CENTER);
        computerOneVBox.getChildren().add(computerOneLabel);

        // computerTwoVBox
        VBox computerTwoVBox = new VBox();
        Label computerTwoLabel = new Label(blackJack.getComputerTwo().getName());
        setLabelStyle(computerTwoLabel);
        computerTwoVBox.setAlignment(Pos.CENTER);
        computerTwoVBox.getChildren().add(computerTwoLabel);

        // Create images from setImageView method
        ImageView deckImage = setImageView("blackjack_images", "deck.png");
        ImageView backgroundImage = setImageView("blackjack_images", "background.png");
        ImageView chip10 = setImageView("blackjack_images", "10-chip.png");
        ImageView chip20 = setImageView("blackjack_images", "20-chip.png");
        ImageView chip50 = setImageView("blackjack_images", "50-chip.png");
        ImageView chip100 = setImageView("blackjack_images", "100-chip.png");

        // Set deck image size
        deckImage.setFitHeight(100);
        deckImage.setFitWidth(100);

        // Create a BorderPane to hold the players' spots
        BorderPane borderPane = new BorderPane(deckImage, dealerVBox, computerOneVBox, userVBox, computerTwoVBox);

        // Set the background image size
        backgroundImage.setFitHeight(1911);
        backgroundImage.setFitWidth(1940);

        // Set chip image size
        chip10.setFitHeight(70);
        chip20.setFitHeight(70);
        chip50.setFitHeight(70);
        chip100.setFitHeight(70);

        // Create an HBox for chips
        HBox chipHBox = new HBox(chip10, chip20, chip50, chip100);
        chipHBox.setSpacing(10);
        chipHBox.setAlignment(Pos.BOTTOM_RIGHT);
        chipHBox.setPadding(new Insets(600, 120, 20, 60));

        root.getChildren().addAll(backgroundImage, borderPane, chipHBox);
        stage.setTitle("BlackJack Game");
        stage.setScene(scene);
        stage.show();

    }

    // Set ImageView for images
    public static ImageView setImageView(String folderName, String imageName) {
        File file = new File(folderName + "/" + imageName);
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);

        return imageView;
    }

    // Set font for labels
    public static void setLabelStyle(Label label) {
        Font font = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
