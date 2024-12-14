package com.game.SnakeGame;

import java.util.Random;

import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Food {
    private static final int TILE_SIZE = 20;
    private Block foodBlock;
    private Snake snake;
    private int attempts = 0;

    public Food(Parent root, Snake snake) {
        this.snake = snake;
        spawnFood(root);
    }

    public void spawnFood(Parent root) {
        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            int x = random.nextInt((600 / TILE_SIZE)) * TILE_SIZE;
            int y = random.nextInt((400 / TILE_SIZE)) * TILE_SIZE;

            // Check if the position overlaps with the snake's body (now using Block objects)
            validPosition = true;
            Block current = snake.getHead();  // Start checking from the head

            while (current != null) {
                if (current.getX() == x && current.getY() == y) {
                    validPosition = false;  // Food position overlaps with a body segment
                    break;
                }
                current = current.getNext();  // Move to the next segment in the snake's body
            }

            // If position is valid, create the food block
            if (validPosition) {
                foodBlock = new Block(x, y);  // Food as a block instead of a rectangle
            }
        } 
    }

    public Block getFoodBlock() {
        return foodBlock;
    }

    public void reposition(Parent root) {
        spawnFood(root);  // Reposition food at a new random location
    }

    // Draw the food
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(foodBlock.getX(), foodBlock.getY(), TILE_SIZE, TILE_SIZE);
    }
}
