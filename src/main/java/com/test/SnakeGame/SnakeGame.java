package com.test.SnakeGame;

import com.test.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private StackPane root;
    private boolean gameOver = false;
    private int score = 0;
    private boolean isPaused = false;
    private Canvas canvas;
    private GraphicsContext gc;
    private Label scoreLabel;  // Label for displaying the score
    private long lastUpdate = 0;    //tracks time since last movement
    private int speed = 200_000_000;    // initial speed in nanosecs
    private String userName;
    private MainMenu menu;

    public SnakeGame(String userName, MainMenu menu) {
        this.userName = userName;
        this.menu = menu;
    }

    public void start(Stage primaryStage) {
        root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        
        // Initialize canvas
        canvas = new Canvas(600, 400);  // Set the size of the canvas
        gc = canvas.getGraphicsContext2D();     // Get the drawing context
        root.getChildren().add(canvas);     // Add the canvas to the root

        // Add the score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7);");
        StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
        scoreLabel.setTranslateX(10);  // Add padding from the left
        scoreLabel.setTranslateY(10);  // Add padding from the top
        root.getChildren().add(scoreLabel);

        // Add pause instruction
        Label pauseInstruction = new Label("Pause: Press ESC");
        pauseInstruction.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7);");
        StackPane.setAlignment(pauseInstruction, Pos.TOP_RIGHT);
        pauseInstruction.setTranslateX(-10);  // Add padding from the right
        pauseInstruction.setTranslateY(10);   // Add padding from the top
        root.getChildren().add(pauseInstruction);

        drawGrid();

        // Initialize snake and food 
        snake = new Snake(300, 200);

        food = new Food(root, snake);

        // Handle keyboard input for snake direction
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ESCAPE) {
                togglePause(primaryStage);
            } else if (!isPaused) {
                if (code == KeyCode.UP && snake.getDirectionY() != 1) {
                    snake.setDirection(0, -1);
                } else if (code == KeyCode.DOWN && snake.getDirectionY() != -1) {
                    snake.setDirection(0, 1);
                } else if (code == KeyCode.LEFT && snake.getDirectionX() != 1) {
                    snake.setDirection(-1, 0);
                } else if (code == KeyCode.RIGHT && snake.getDirectionX() != -1) {
                    snake.setDirection(1, 0);
                }
            }
        });

        // Set up the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) {
                    stop();
                    renderGameOverMessage(primaryStage);
                } else if (!isPaused) {
                    if (now - lastUpdate >= speed) {
                        snake.move();
                        checkCollisions();
                        render(gc);
                        lastUpdate = now;
                    }
                }
            }
        };
        gameLoop.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }

    private void togglePause(Stage primaryStage) {

        if (gameOver) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {
            VBox pauseMenu = new VBox(20);
            pauseMenu.setAlignment(Pos.CENTER);

            // Dim background
            Canvas overlay = new Canvas(600, 400);
            GraphicsContext overlayGc = overlay.getGraphicsContext2D();
            overlayGc.setFill(Color.rgb(0, 0, 0, 0.7));
            overlayGc.fillRect(0, 0, overlay.getWidth(), overlay.getHeight());
            root.getChildren().add(overlay);

            // Add "Paused" label
            javafx.scene.control.Label pauseLabel = new javafx.scene.control.Label("Game Paused");
            pauseLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");

            // Add Resume button
            Button resumeButton = new Button("Resume");
            resumeButton.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-padding: 10 20 10 20; " +
                "-fx-background-color: #00ff00; " +
                "-fx-text-fill: black;" 
            );

            resumeButton.setOnAction(e -> {
                isPaused = false;
                root.getChildren().remove(overlay);
                root.getChildren().remove(pauseMenu);
            });

            pauseMenu.getChildren().addAll(pauseLabel, resumeButton);
            root.getChildren().add(pauseMenu);
        }
    }

    private void checkCollisions() {
        // Check if snake eats food
        Block head = snake.getHead();

        if (head.getX() == food.getFoodBlock().getX() && head.getY() == food.getFoodBlock().getY()) {
            snake.grow();   //increase the length
            food.reposition((StackPane)gc.getCanvas().getParent());  // reposition the food
            score += 10;    // Increment score

            scoreLabel.setText("Score: " + score);

            // Increase speed after every 5 pts
            if (score % 50 == 0 && speed > 50_000_000) {   // Minimum speed limit
                speed -= 10_000_000;   // increase speed by reducing the delay
            }
        }

        // Check if the snake collides with itself
        if (snake.checkCollisionWithSelf()) {
            gameOver = true;
            return;
        }

        // Check for boundary collisions
        if (head.getX() < 0 || head.getY() < 0 || head.getX() >= 600 || head.getY() >= 400) {
            gameOver = true;
            return;
        }
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Clear previous frame

        // Draw the grid backgrond
        drawGrid();
        snake.render(gc);  // Render the snake
        food.render(gc);  // Render the food
        
    }

    private void renderGameOverMessage(Stage primaryStage){
        Stage gameOverStage = new Stage();
        StackPane gameOverRoot = new StackPane();
        Scene gameOverScene = new Scene(gameOverRoot, 300, 200);

        javafx.scene.control.Label gameOverLabel = new javafx.scene.control.Label("Game Over! Score: " + score);
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        
        javafx.scene.control.Button playAgainButton = new javafx.scene.control.Button("Play Again");
        playAgainButton.setStyle(
            "-fx-font-size: 24px; " + 
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #00ff00; " +
            "-fx-text-fill: black;"
        );
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

    // Draw the grid background
    private void drawGrid() {
        gc.setFill(Color.BLACK);  // Fill the background with black
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Fill the entire canvas

        gc.setStroke(Color.GREEN);
        gc.setLineWidth(0.5);

        for (int x = 0; x < canvas.getWidth(); x += 20) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }

        for (int y = 0; y < canvas.getHeight(); y += 20) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }
}
