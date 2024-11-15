package com.test.SnakeGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
    private static final int TILE_SIZE = 20;
    private Rectangle food;

    public Food(StackPane root, ArrayList<Rectangle> snakeBody ) {
        spawnFood(root, snakeBody);
    }

    public void spawnFood(StackPane root, ArrayList<Rectangle> snakeBody) {
        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            int x = random.nextInt(30) * TILE_SIZE;
            int y = random.nextInt(20) * TILE_SIZE;

            // Check if the position overlaps with the snake
            validPosition = true;
            for (Rectangle segment : snakeBody) {
                if (segment.getX() == x && segment.getY() == y) {
                    validPosition = false;
                    break;
                }
            }
            if (validPosition) {
                food.setX(x);
                food.setY(y);
            }
        }
    }

    public Rectangle getFood() {
        return food;
    }

    public void reposition(StackPane root, ArrayList<Rectangle> snakeBody) {
        spawnFood(root, snakeBody);  // Reposition food at a new random location
    }
}
