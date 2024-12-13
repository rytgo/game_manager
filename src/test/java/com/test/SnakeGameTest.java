package com.test;

import com.game.SnakeGame.Food;
import com.game.SnakeGame.Snake;
import com.game.SnakeGame.SnakeGame;
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
        snakeGame = new SnakeGame("TestUser", null); // For testing, you can pass null for the menu
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

        // Reposition the food
        food.reposition(null);
        assertNotEquals(initialFoodX, food.getFoodBlock().getX());
        assertNotEquals(initialFoodY, food.getFoodBlock().getY());
    }

    @Test
    public void testCollisionWithBounds() {
        // Position the snake at the boundary and check if it triggers game over
        snake.getHead().setPosition(600 - Snake.TILE_SIZE, 400 - Snake.TILE_SIZE);  // Set the snake at bottom-right corner
        snake.move();
        snakeGame.checkCollisions();
        assertTrue(snakeGame.isGameOver());
    }

    @Test
    public void testCollisionWithSelf() {
        // Move the snake to make it collide with itself
        snake.grow();  // Grow the snake to have more than one segment
        snake.setDirection(1, 0);
        snake.move();  // Move the snake in the current direction
        snake.setDirection(0, 1);
        snake.move();  // Move the snake in a different direction to make it collide with itself

        assertTrue(snake.checkCollisionWithSelf());
    }

    @Test
    public void testGamePauseAndResume() {
        // Simulate pausing and resuming the game
        snakeGame.togglePause(null);  // Toggle pause state
        assertTrue(snakeGame.isPaused());

        snakeGame.togglePause(null);  // Toggle again to resume
        assertFalse(snakeGame.isPaused());
    }
}
