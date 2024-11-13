package com.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) {
        VBox scene1 = new VBox();
        stage.setScene(new Scene(scene1, 400, 300));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}