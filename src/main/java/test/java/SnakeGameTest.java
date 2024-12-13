package test.java;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.test.SnakeGame.SnakeGame;
import com.test.SnakeGame.Snake;
import com.test.SnakeGame.Food;

public class SnakeGameTest {

    private SnakeGame snakeGame;

    @Before
    public void setUp() {
        snakeGame = new SnakeGame();
        snakeGame.initializeGame();
    }

    /**
     * Test for initializing the snake game.
     */
    @Test
    public void testGameInitialization() {
        assertNotNull("Snake should not be null after initialization", snakeGame.getSnake());
        assertNotNull("Food should not be null after initialization", snakeGame.getFood());
        assertFalse("Game should not be over after initialization", snakeGame.isGameOver());
    }

    /**
     * Test for snake movement.
     */
    @Test
    public void testSnakeMovement() {
        int initialX = snakeGame.getSnake().getHeadX();
        int initialY = snakeGame.getSnake().getHeadY();

        // Simulate moving right
        snakeGame.getSnake().moveRight();
        assertEquals("Snake's head X coordinate should increase", initialX + 1, snakeGame.getSnake().getHeadX());
        assertEquals("Snake's head Y coordinate should remain the same", initialY, snakeGame.getSnake().getHeadY());
    }

    /**
     * Test for collision detection with walls.
     */
    @Test
    public void testWallCollision() {
        // Move the snake outside the grid bounds
        snakeGame.getSnake().setHeadPosition(-1, -1);
        snakeGame.checkCollisions();

        assertTrue("Game should be over if the snake collides with a wall", snakeGame.isGameOver());
    }

    /**
     * Test for food consumption.
     */
    @Test
    public void testFoodConsumption() {
        // Place food directly in front of the snake's head
        int snakeX = snakeGame.getSnake().getHeadX();
        int snakeY = snakeGame.getSnake().getHeadY();
        snakeGame.getFood().setPosition(snakeX + 1, snakeY);

        // Simulate moving right to eat the food
        snakeGame.getSnake().moveRight();
        snakeGame.checkFoodConsumption();

        assertEquals("Score should increase after eating food", 10, snakeGame.getScore());
        assertNotNull("New food should be generated after eating", snakeGame.getFood());
    }

    /**
     * Test for snake collision with itself.
     */
    @Test
    public void testSnakeCollisionWithItself() {
        // Simulate snake growing and colliding with itself
        Snake snake = snakeGame.getSnake();
        snake.grow();
        snake.setHeadPosition(snake.getBody().get(0).getX(), snake.getBody().get(0).getY());

        snakeGame.checkCollisions();
        assertTrue("Game should be over if the snake collides with itself", snakeGame.isGameOver());
    }

    /**
     * Test for interactions between snake movement, food consumption, and scoring.
     */
    @Test
    public void testSnakeFoodScoreInteraction() {
        // Place food directly in front of the snake
        int snakeX = snakeGame.getSnake().getHeadX();
        int snakeY = snakeGame.getSnake().getHeadY();
        snakeGame.getFood().setPosition(snakeX + 1, snakeY);

        // Simulate eating food
        snakeGame.getSnake().moveRight();
        snakeGame.updateGame();

        assertEquals("Score should increase after eating food", 10, snakeGame.getScore());
        assertTrue("Snake should grow after eating food", snakeGame.getSnake().getLength() > 1);
    }
}

