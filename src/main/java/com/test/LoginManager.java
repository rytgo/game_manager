package com.test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class LoginManager {
    private String fileName;
    private HashMap<String, String> users;
    private Consumer<String> onLoginSuccess; // Callback for successful login
    private HighScoresManager highScoresManager; // Manage high scores

    public LoginManager(String fileName, HighScoresManager highScoresManager) {
        this.fileName = fileName;
        this.users = new HashMap<>();
        this.highScoresManager = highScoresManager;
        loadUsers();
    }

    public void setOnLoginSuccess(Consumer<String> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess; // Assign the callback
    }

    private void loadUsers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0];
                    String decryptedPassword = Encryption.decrypt(parts[1]);
                    users.put(username, decryptedPassword);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public boolean createAccount(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return false;
        }
        users.put(username, password);
        saveUser(username, password);

        // Add default high scores for the new user
        highScoresManager.addUser(username);

        return true;
    }

    private void saveUser(String username, String password) {
        try {
            String encryptedPassword = Encryption.encrypt(password);
            String line = username + ":" + encryptedPassword + System.lineSeparator();
            Files.write(Paths.get(fileName), line.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getLoginScreen(Stage stage) {
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

            if (authenticate(username, password)) {
                System.out.println("Login successful!");

                // Initialize high scores for existing users if missing
                highScoresManager.addUser(username);

                if (onLoginSuccess != null) { // Ensure the callback is not null
                    onLoginSuccess.accept(username); // Call the callback with the username
                }
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        });

        createAccountButton.setOnAction(e -> {
            Scene scene = stage.getScene();
            scene.setRoot(getCreateAccountScreen(stage));
        });

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createAccountButton);
        return loginLayout;
    }

    public VBox getCreateAccountScreen(Stage stage) {
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

            if (createAccount(username, password)) {
                showAlert("Success", "Account created successfully!");
                //Scene scene = stage.getScene();
                //scene.setRoot(getLoginScreen(stage));
                App app = new App();
                app.start(stage);
            } else {
                showAlert("Error", "Failed to create account. Username might already exist.");
            }
        });

        backButton.setOnAction(e -> {
            //Scene scene = stage.getScene();
            //scene.setRoot(getLoginScreen(stage));
            //setOnLoginSuccess(this.onLoginSuccess);
            App app = new App();
            app.start(stage);
        });

        createAccountLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, createAccountButton, backButton);
        return createAccountLayout;
    }

    public String getUser() {
        return users.keySet().iterator().next();
    }
}

