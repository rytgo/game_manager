package com.test.SnakeGame;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private long lastUpdate = 0;    //tracks time since last movement
    private int speed = 200_000_000;    // initial speed in nanosecs

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        
        // Set the background image
        Image backgroundImage = new Image("snakebackground.jpg");
        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        root.setBackground(new Background(bgImage));

        // Initialize canvas
        canvas = new Canvas(600, 400);  // Set the size of the canvas
        gc = canvas.getGraphicsContext2D();     // Get the drawing context
        root.getChildren().add(canvas);     // Add the canvas to the root

        // Initialize snake and food 
        snake = new Snake(300, 200);

        food = new Food(root, snake);

        // Handle keyboard input for snake direction
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP && snake.getDirectionY() != 1) {
                snake.setDirection(0, -1);
            } else if (code == KeyCode.DOWN && snake.getDirectionY() != -1) {
                snake.setDirection(0, 1);
            } else if (code == KeyCode.LEFT && snake.getDirectionX() != 1) {
                snake.setDirection(-1, 0);
            } else if (code == KeyCode.RIGHT && snake.getDirectionX() != -1) {
                snake.setDirection(1, 0);
            }
        });

        // Set up the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    if (now - lastUpdate >= speed) {
                        snake.move();
                        checkCollisions();
                        render(gc);
                        lastUpdate = now;
                    }
                } else {
                    stop();
                    renderGameOverMessage(primaryStage);
                }
            }
        };
        gameLoop.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }

    private void checkCollisions() {
        // Check if snake eats food
        Rectangle head = snake.getHead();

        if (snake.getHead().getBoundsInParent().intersects(food.getFood().getBoundsInParent())) {
            snake.grow();   //increase the length
            food.reposition((StackPane)gc.getCanvas().getParent());  // reposition the food
            score++;    // Increment score

            // Increase speed after every 5 pts
            if (score % 5 == 0 && speed > 50_000_000) {   // Minimum speed limit
                speed -= 200_000_000;   // increase speed by reducing the delay
            }
        }

        // Check if the snake collides with itself
        // TODO: Check condition of function
        if (snake.checkCollisionWithSelf()) {
            gameOver = true;
        }

        // Check for boundary collisions
        if (head.getX() < 0 || head.getY() < 0 || head.getX() >= 600 || head.getY() >= 400) {
            gameOver = true;
        }
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Clear previous frame

        // Draw the snake
        for (Rectangle segment : snake.getBody()) {
            gc.setFill(Color.PURPLE);
            gc.fillOval(segment.getX(), segment.getY(), segment.getWidth(), segment.getHeight());
        }
    }

    private void renderGameOverMessage(Stage primaryStage){
        Stage gameOverStage = new Stage();
        StackPane gameOverRoot = new StackPane();
        Scene gameOverScene = new Scene(gameOverRoot, 300, 200);

        javafx.scene.control.Label gameOverLabel = new javafx.scene.control.Label("Game Over! Score: " + score);
        gameOverLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        
        javafx.scene.control.Button playAgainButton = new javafx.scene.control.Button("Play Again");
        playAgainButton.setOnAction(e -> {
            // Reset the game state
            gameOver = false;
            score = 0;
            snake = new Snake(300, 200);
            food = new Food(gameOverRoot, snake);
            gameOverStage.close();
            start(primaryStage);  // Restart the game
        });
        
        VBox layout = new VBox(10, gameOverLabel, playAgainButton);
        layout.setAlignment(Pos.CENTER);
        gameOverRoot.getChildren().add(layout);
        
        gameOverStage.setScene(gameOverScene);
        gameOverStage.setTitle("Game Over");
        gameOverStage.show();
    }
}
