package com.test.SnakeGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
    private static final int TILE_SIZE = 20;
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

        while (!validPosition) {
            int x = random.nextInt(30) * TILE_SIZE;
            int y = random.nextInt(20) * TILE_SIZE;

            // Check if the position overlaps with the snake
            validPosition = snake.getBody().stream()
                .noneMatch(segment -> segment.getX() == x && segment.getY() == y);
            
            if (validPosition) {
                food = new Rectangle(TILE_SIZE, TILE_SIZE, Color.RED);
                food.setTranslateX(x);
                food.setTranslateY(y);
                
                root.getChildren().add(food);
            }
        } 
    }

    public Rectangle getFood() {
        return food;
    }

    public Image getFoodImage() {
        return foodImage; // Expose the image to the rendering logic
    }

    public void reposition(StackPane root) {
        root.getChildren().remove(food);    // Remove the old food if it exists
        spawnFood(root);  // Reposition food at a new random location
    }
}
