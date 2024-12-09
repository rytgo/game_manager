package com.test.SnakeGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
    private static final int TILE_SIZE = 20;
    private ImageView foodImageView;
    private Rectangle food;
    private Snake snake;
    private Image foodImage;

    public Food(StackPane root, Snake snake) {
        foodImage = new Image(getClass().getResource("/apple.jpg").toExternalForm());
        this.snake = snake;
        spawnFood(root);
    }

    public void spawnFood(StackPane root) {
        Random random = new Random();
        boolean validPosition = false;

        if (foodImageView != null) {
            root.getChildren().remove(foodImageView);
        }

        while (!validPosition) {
            int x = random.nextInt((600 / TILE_SIZE)) * TILE_SIZE;
            int y = random.nextInt((400 / TILE_SIZE)) * TILE_SIZE;

            // Check if the position overlaps with the snake
            validPosition = snake.getBody().stream()
                .noneMatch(segment -> segment.getX() == x && segment.getY() == y);
            
            if (validPosition) {
                // Create a new ImageView for the food
                foodImageView = new ImageView(foodImage);
                foodImageView.setFitWidth(TILE_SIZE);
                foodImageView.setFitHeight(TILE_SIZE);

                // Position the food at the generated coordinates
                foodImageView.setTranslateX(x - 300);
                foodImageView.setTranslateY(y - 200);

                // Add the ImageView to the root
                root.getChildren().add(foodImageView);
            }
        } 
    }

    public ImageView getFood() {
        return foodImageView;
    }

    public void reposition(StackPane root) {
        spawnFood(root);  // Reposition food at a new random location
    }
}
