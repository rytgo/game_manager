package com.test;

import com.game.SnakeGame.Block;
import com.game.SnakeGame.Food;
import com.game.SnakeGame.Snake;
import com.game.SnakeGame.SnakeGame;

import javafx.scene.layout.VBox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeGameTest {

    private SnakeGame snakeGame;
    private Snake snake;
    private Food food;

    @BeforeEach
    public void setUp() {
        // Here, you might need to initialize necessary game objects for testing
        snake = new Snake(300, 200);
        food = new Food(null, snake);  // For testing purposes, you can pass null for the root
        snakeGame = new SnakeGame("TestUser", null, null); // For testing, you can pass null for the menu
    }

    @Test
    public void testSnakeMovement() {
        int initialX = snake.getHead().getX();
        int initialY = snake.getHead().getY();

        // Move the snake right
        snake.setDirection(1, 0);
        snake.move();
        assertEquals(initialX + Snake.TILE_SIZE, snake.getHead().getX());
        assertEquals(initialY, snake.getHead().getY());

        // Move the snake down
        snake.setDirection(0, 1);
        snake.move();
        assertEquals(initialX + Snake.TILE_SIZE, snake.getHead().getX());
        assertEquals(initialY + Snake.TILE_SIZE, snake.getHead().getY());
    }

    @Test
    public void testFoodRepositioning() {
        // Ensure that after the snake eats the food, the food is repositioned
        int initialFoodX = food.getFoodBlock().getX();
        int initialFoodY = food.getFoodBlock().getY();

        // Create a mock or a valid parent container for repositioning
        VBox mockParent = new VBox();  // Use a valid container instead of null
        
        // Reposition the food
        food.reposition(mockParent);  // Reposition food with a valid parent

        // Assert that the food's position has changed
        assertNotEquals(initialFoodX, food.getFoodBlock().getX(), "Food X position should change after repositioning.");
        assertNotEquals(initialFoodY, food.getFoodBlock().getY(), "Food Y position should change after repositioning.");
    }


    @Test
    public void testFoodDoesNotSpawnOnSnake() {
        boolean foodOverlapsSnake = false;

        for (int i = 0; i < 100; i++) { // Test multiple repositionings
            food.reposition(null);
            Block current = snake.getHead();
            while (current != null) {
                if (current.getX() == food.getFoodBlock().getX() && current.getY() == food.getFoodBlock().getY()) {
                    foodOverlapsSnake = true;
                    break;
                }
                current = current.getNext();
            }
            if (foodOverlapsSnake) break;
        }

        assertFalse(foodOverlapsSnake, "Food should not spawn on the snake!");
    }

    @Test
    public void testPauseToggle() {
        assertFalse(snakeGame.isPaused());

        snakeGame.setPaused(true);
        assertTrue(snakeGame.isPaused());

        snakeGame.setPaused(false);
        assertFalse(snakeGame.isPaused());
    }

    @Test
    public void testScoreIncrement() {
        int initialScore = snakeGame.getScore();

        // Simulate eating food
        food.getFoodBlock().setPosition(snake.getHead().getX() + Snake.TILE_SIZE, snake.getHead().getY());
        snake.setDirection(1, 0);
        snake.move();

        // Check collision and increment score
        if (snake.getHead().getX() == food.getFoodBlock().getX() && snake.getHead().getY() == food.getFoodBlock().getY()) {
            snakeGame.incrementScore(10);  // Assuming there's a method to handle score
        }

        assertEquals(initialScore + 10, snakeGame.getScore());
    }

}
