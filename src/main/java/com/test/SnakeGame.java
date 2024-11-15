package com.test;

import java.util.ArrayList;

import javafx.scene.Scene;
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
}
