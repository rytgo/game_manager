package com.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        HighScoresManager highScoresManager = new HighScoresManager("high_scores.txt");
        LoginManager loginManager = new LoginManager("user_accounts.txt", highScoresManager);
        ToolbarManager toolbarManager = new ToolbarManager();

        // Create MainMenu with placeholders (user will be set on login)
        MainMenu mainMenu = new MainMenu(highScoresManager, loginManager, null);

        Scene scene = new Scene(loginManager.getLoginScreen(stage), 640, 480);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        loginManager.setOnLoginSuccess(username -> {
            MainMenu updatedMainMenu = new MainMenu(highScoresManager, loginManager, username);

            // Create the layout for the main menu with the toolbar
            BorderPane rootLayout = new BorderPane();
            rootLayout.setTop(toolbarManager.createToolbar(stage, updatedMainMenu)); // Add toolbar at the top
            rootLayout.setCenter(updatedMainMenu.launchMainMenu(stage)); // Add main menu in the center

            scene.setRoot(rootLayout);
        });

        stage.setScene(scene);
        stage.setTitle("CS151 Game Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
