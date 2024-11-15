package com.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private LoginManager loginManager = new LoginManager("user_accounts.txt");
    private Scene scene; // Reuse the same scene

    @Override
    public void start(Stage stage) {
        VBox loginScreen = loginScreen(stage);
        scene = new Scene(loginScreen, 400, 300); // Initialize the scene with the login screen
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Load CSS
        stage.setScene(scene);
        stage.setTitle("CS151 Game");
        stage.show();
    }

    private VBox loginScreen(Stage stage) {
        VBox loginLayout = new VBox();
        loginLayout.getStyleClass().add("container");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field");

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        loginButton.getStyleClass().add("button");
        createAccountButton.getStyleClass().add("button");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (loginManager.authenticate(username, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid");
            }
        });

        createAccountButton.setOnAction(e -> scene.setRoot(createAccountScreen(stage))); // Reuse the scene

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createAccountButton);
        return loginLayout;
    }

    private VBox createAccountScreen(Stage stage) {
        VBox createAccountLayout = new VBox();
        createAccountLayout.getStyleClass().add("container");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field");

        Button createAccountButton = new Button("Create Account");
        Button backButton = new Button("Back to Login");

        createAccountButton.getStyleClass().add("button");
        backButton.getStyleClass().add("button");

        createAccountButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (loginManager.createAccount(username, password)) {
                System.out.println("Account created!");
                scene.setRoot(loginScreen(stage)); // Reuse the scene with login screen root
            } else {
                System.out.println("Invalid");
            }
        });

        backButton.setOnAction(e -> scene.setRoot(loginScreen(stage))); // Reuse the scene with login screen root
        createAccountLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, createAccountButton, backButton);
        return createAccountLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}