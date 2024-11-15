package com.test;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SnakeGame {
    private static final int TILE_SIZE = 30;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 30;

    private ArrayList<Rectangle> snake;
    private Rectangle food;
    private int directionX = 1, directionY = 0; // Initial direction (right)
    private boolean gameOver = false;
    private int score = 0;

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        
        // Set up the initial snake
        snake = new ArrayList<>();
        Rectangle head = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        head.setX(GRID_WIDTH / 2 * TILE_SIZE);
        head.setY(GRID_HEIGHT / 2 * TILE_SIZE);
        snake.add(head);
        
        root.getChildren().addAll(snake);
        
        // Set up the food
        spawnFood(root);

        // Set up the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    moveSnake();
                    checkCollisions();
                    updateGameBoard();
                }
            }
        };
        gameLoop.start();

        // Handle user input
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP && directionY == 0) {
                directionX = 0;
                directionY = -1;
            } else if (event.getCode() == KeyCode.DOWN && directionY == 0) {
                directionX = 0;
                directionY = 1;
            } else if (event.getCode() == KeyCode.LEFT && directionX == 0) {
                directionX = -1;
                directionY = 0;
            } else if (event.getCode() == KeyCode.RIGHT && directionX == 0) {
                directionX = 1;
                directionY = 0;
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }
}
