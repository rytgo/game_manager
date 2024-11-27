package com.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private LoginManager loginManager;

    @Override
    public void start(Stage stage) {
        loginManager = new LoginManager("user_accounts.txt");
        Scene scene = new Scene(loginManager.getLoginScreen(stage), 400, 300); // Initialize scene using LoginManager's method
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Load CSS
        stage.setScene(scene);
        stage.setTitle("CS151 Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
