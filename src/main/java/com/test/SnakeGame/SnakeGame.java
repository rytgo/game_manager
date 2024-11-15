package com.test.SnakeGame;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private StackPane root;
    private boolean gameOver = false;
    private int score = 0;


    private Canvas canvas;
    private GraphicsContext gc;

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        
        // Initialize canvas
        canvas = new Canvas(600, 400);  // Set the size of the canvas
        gc = canvas.getGraphicsContext2D();     // Get the drawing context
        root.getChildren().add(canvas);     // Add the canvas to the root

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
                    render(gc);
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
        Rectangle head = snake.getHead();
        if (head.getBoundsInParent().intersects(food.getFood().getBoundsInParent())) {
            snake.grow();   //increase the length
            food.reposition(root);  // reposition the food
            score++;    // Increment score
        }

        // Check if the snake collides with itself
        if (snake.checkCollisionWithSelf()) {
            gameOver = true;
        }

        // Check for boundary collisions
        if (head.getX() < 0 || head.getY() < 0 || head.getX() >= 600 || head.getY() >= 400) {
            gameOver = true;
        }
    }

    private void updateGameBoard(){
        // Update the score if necessary
        // A score label maybe
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Clear previous frame

        // Draw the snake
        for (Rectangle segment : snake.getBody()) {
            gc.setFill(segment.getFill());
            gc.fillRect(segment.getX(), segment.getY(), segment.getWidth(), segment.getHeight());
        }

        // Draw the food
        gc.setFill(food.getFood().getFill());
        gc.fillRect(food.getFood().getX(), food.getFood().getY(), food.getFood().getWidth(), food.getFood().getHeight());
    }
}
