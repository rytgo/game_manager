package com.game.SnakeGame;

import com.game.App;

import javafx.stage.Stage;

public class Main extends App{
    @Override
    public void start(Stage primaryStage) {
        SnakeGame game = new SnakeGame();
        game.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
