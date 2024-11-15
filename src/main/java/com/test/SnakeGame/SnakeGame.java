package com.test.SnakeGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private StackPane root;
    private boolean gameOver = false;
    private int score = 0;

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        
        // Initialize snake and food 
        snake = new Snake(300, 200);
        food = new Food(root);

        // Set up the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    snake.move();
                    checkCollisions();
                    updateGameBoard();
                }
            }
        };
        gameLoop.start();

        // Handle user input
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP && snake.getHead().getY() != snake.getBody().get(1).getY()) {
                snake.setDirection(0, -1);
            } else if (event.getCode() == KeyCode.DOWN && snake.getHead().getY() != snake.getBody().get(1).getY()) {
                snake.setDirection(0, 1);
            } else if (event.getCode() == KeyCode.LEFT && snake.getHead().getX() != snake.getBody().get(1).getX()) {
                snake.setDirection(-1, 0);
            } else if (event.getCode() == KeyCode.RIGHT && snake.getHead().getX() != snake.getBody().get(1).getX()) {
                snake.setDirection(1, 0);
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }

    private void checkCollisions() {
        // Check if snake eats food
    }

    private void updateGameBoard(){
        // Update the score if necessary
        // A score label maybe
    }
}
