package com.test;

import org.junit.jupiter.api.BeforeEach;

import com.game.SnakeGame.Food;
import com.game.SnakeGame.Snake;
import com.game.SnakeGame.SnakeGame;

public class SnakeGameTest {

    private SnakeGame snakeGame;

    @BeforeEach
    public void setUp() {
        snakeGame = new SnakeGame();
    }
}

