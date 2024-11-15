package com.test.SnakeGame;

import java.util.Random;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
    private static final int TILE_SIZE = 20;
    private Rectangle food;

    public Food(StackPane root) {
        spawnFood(root);
    }

    public void spawnFood(StackPane root) {
        Random random = new Random();
        int x = random.nextInt(30) * TILE_SIZE;
        int y = random.nextInt(20) * TILE_SIZE;

        food = new Rectangle(TILE_SIZE, TILE_SIZE, Color.RED);
        food.setX(x);
        food.setY(y);

        root.getChildren().add(food);
    }

    public Rectangle getFood() {
        return food;
    }

    public void reposition(StackPane root) {
        spawnFood(root);  // Reposition food at a new random location
    }
}
