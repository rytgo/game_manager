package com.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    private MainMenu updatedMainMenu;
    private HighScoresManager highScoresManager = new HighScoresManager("high_scores.txt");
    private LoginManager loginManager = new LoginManager("user_accounts.txt", highScoresManager);

    // Getter for updatedMainMenu
    public MainMenu getUpdatedMainMenu() {
        return new MainMenu(highScoresManager, loginManager, loginManager.getUser());
    }

    @Override
    public void start(Stage stage) {

        ToolbarManager toolbarManager = new ToolbarManager();

        BorderPane rootLayout = new BorderPane();
        Scene scene = new Scene(rootLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        rootLayout.setCenter(loginManager.getLoginScreen(stage));

        // Define the behavior for successful login
        loginManager.setOnLoginSuccess(username -> {
            updatedMainMenu = new MainMenu(highScoresManager, loginManager, username);

            // Add the toolbar (only once, after login)
            rootLayout.setTop(toolbarManager.createToolbar(stage, rootLayout, updatedMainMenu));
            rootLayout.setCenter(updatedMainMenu.launchMainMenu(stage));

            updatedMainMenu.setOnLogout(v -> {
                // Clear the toolbar
                rootLayout.setTop(null);
        
                // Reset to login screen
                rootLayout.setCenter(loginManager.getLoginScreen(stage));
            });
        });

        stage.setScene(scene);
        stage.setTitle("CS151 Game Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
