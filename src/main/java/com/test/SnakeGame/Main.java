package com.test.SnakeGame;

import com.test.App;
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
