package com.game.SnakeGame;

import com.game.HighScoresManager;
import com.game.MainMenu;
import com.game.ToolbarManager;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.Map;
import java.util.Objects;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private StackPane root;
    private boolean gameOver = false;
    private int score = 0;
    private boolean isPaused = false;
    private Canvas canvas = new Canvas(800, 600);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Label scoreLabel;  // Label for displaying the score
    private long lastUpdate = 0;    //tracks time since last movement
    private int speed = 200_000_000;    // initial speed in nanosecs
    private String userName;
    private MainMenu menu;
    private HighScoresManager highScoresManager;

    public SnakeGame(String userName, MainMenu menu, HighScoresManager highScoresManager) {
        this.userName = userName;
        this.menu = menu;
        this.highScoresManager = highScoresManager;
    }

    // Getter for isPaused
    public boolean isPaused() {
        return isPaused;
    }

    // Setter for isPaused
    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    // Getter for gameOver
    public boolean isGameOver() {
        return gameOver;
    }

    // Setter for gameOver
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }    

    public void start(Stage primaryStage) {
        // Create root layout
        StackPane root = new StackPane(); // Use StackPane for layering
        root.getChildren().add(canvas);
        VBox gameLayout = new VBox();    // VBox for toolbar and canvas
    
        gameLayout.setSpacing(0);
    
        // Create toolbar
        HBox toolbar = new HBox();
        toolbar.setId("toolbar");
    
        // Menu button
        Button menuButton = new Button("Main Menu");
        menuButton.setId("main-menu-button");
        menuButton.setOnAction(e -> {
            togglePause(root);
            Stage s = (Stage) root.getScene().getWindow();
            BorderPane rootLayout = new BorderPane();

            ToolbarManager toolbarManager = new ToolbarManager();
            rootLayout.setTop(toolbarManager.createToolbar(s, rootLayout, menu));

            VBox menuVBox = menu.launchMainMenu(s);
            rootLayout.setCenter(menuVBox);

            Scene menuScene = new Scene(rootLayout, 800, 600);
            menuScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("snake.css")).toExternalForm());
            s.setTitle("CS151 Game Manager");
            s.centerOnScreen();
            s.setResizable(false);
            s.setScene(menuScene);
        });
    
        // Pause instruction
        Label pauseInstruction = new Label("Pause: Press ESC");
        pauseInstruction.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
    
        // Score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
    
        // Right container for alignment
        HBox rightContainer = new HBox(20, pauseInstruction, scoreLabel);
        rightContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);
    
        // Add components to toolbar
        toolbar.getChildren().addAll(menuButton, rightContainer);
    
        // Add toolbar and canvas to game layout
        gameLayout.getChildren().addAll(toolbar, canvas);
    
        drawGrid();
    
        // Initialize snake and food
        snake = new Snake(300, 200);
        food = new Food(new StackPane(), snake);
    
        // Add the game layout to the root (StackPane)
        root.getChildren().add(gameLayout);
    
        // Create scene and handle key inputs
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("snake.css")).toExternalForm());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                togglePause(root); // Pass the root StackPane for overlaying
            } else if (!isPaused) {
                handleKeyInput(event.getCode());
            }
        });
    
        // Consume arrow key events for the menu button
        menuButton.setFocusTraversable(false);
    
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
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    // Handle keyboard input for snake direction
    private void handleKeyInput(KeyCode code) {
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

    public void incrementScore(int points) {
        score += points;
        if (scoreLabel != null) {  // Ensure the label is not null
            scoreLabel.setText("Score: " + score);
        }
    }    

    public void togglePause(StackPane root) {
        if (gameOver) {
            return;
        }
    
        isPaused = !isPaused;
    
        if (isPaused) {
            // Create pause menu
            VBox pauseMenu = new VBox(20);
            pauseMenu.setAlignment(Pos.CENTER);
    
            // Add "Game Paused" label
            Label pauseLabel = new Label("Game Paused");
            pauseLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");
    
            // Add "Press ESC to resume" label
            Label resumeInstruction = new Label("Press ESC to resume");
            resumeInstruction.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
    
            pauseMenu.getChildren().addAll(pauseLabel, resumeInstruction);
    
            // Dim background and overlay pause menu
            Canvas overlay = new Canvas(canvas.getWidth(), canvas.getHeight());
            GraphicsContext overlayGc = overlay.getGraphicsContext2D();
            overlayGc.setFill(Color.rgb(0, 0, 0, 0.7));
            overlayGc.fillRect(0, 0, overlay.getWidth(), overlay.getHeight());
    
            root.getChildren().addAll(overlay, pauseMenu);
        } else {
            // Resume the game by removing the pause menu and overlay
            root.getChildren().remove(root.getChildren().size() - 1); // Remove pause menu
            root.getChildren().remove(root.getChildren().size() - 1); // Remove overlay
        }
    }

    public void checkCollisions() {
        // Check if snake eats food
        Block head = snake.getHead();

        if (head.getX() == food.getFoodBlock().getX() && head.getY() == food.getFoodBlock().getY()) {
            snake.grow();   //increase the length
            food.reposition((VBox)gc.getCanvas().getParent());  // reposition the food
            incrementScore(100);   // Increment score

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
        if (head.getX() < 0 || head.getY() < 0 || head.getX() + 20 > canvas.getWidth() || head.getY() + 20 > canvas.getHeight()) {
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
        Map<String, Integer> userScores = highScoresManager.getUserScores(userName);
        if (userScores != null) {
            int currentHighScore = userScores.getOrDefault("Snake", 0);
            if (score > currentHighScore) {
                highScoresManager.updateScore(userName, "Snake", score);
        }
    }
    
        Stage gameOverStage = new Stage();
        StackPane gameOverRoot = new StackPane();
        Scene gameOverScene = new Scene(gameOverRoot, 300, 200);
        gameOverScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("snake.css")).toExternalForm());

        VBox layout = getvBox(primaryStage, gameOverRoot, gameOverStage);
        layout.setAlignment(Pos.CENTER);
        gameOverRoot.getChildren().add(layout);
        
        gameOverStage.setScene(gameOverScene);
        gameOverStage.setTitle("Game Over");
        gameOverStage.show();
    }

    private VBox getvBox(Stage primaryStage, StackPane gameOverRoot, Stage gameOverStage) {
        Label gameOverLabel = new Label("Game Over! Score: " + score);
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");

        Button playAgainButton = new Button("Play Again");

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
        return layout;
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
