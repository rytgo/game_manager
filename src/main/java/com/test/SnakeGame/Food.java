package com.test.SnakeGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
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
        this.snake = snake;
        spawnFood(root);
    }

    public void spawnFood(StackPane root) {
        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            int x = random.nextInt((600 / TILE_SIZE)) * TILE_SIZE;
            int y = random.nextInt((400 / TILE_SIZE)) * TILE_SIZE;

            // Check if the position overlaps with the snake
            validPosition = snake.getBody().stream()
                .noneMatch(segment -> segment.getX() == x && segment.getY() == y);
            
                if (validPosition) {
                    food = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
                    food.setX(x);
                    food.setY(y);
                }
        } 
    }

    public Rectangle getFood() {
        return food;
    }

    public void reposition(StackPane root) {
        spawnFood(root);  // Reposition food at a new random location
    }

    // Draw the food
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight());
    }
}
