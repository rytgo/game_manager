package com.test;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class SnakeGame {
    private static final int TILE_SIZE = 30;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 30;

    private ArrayList<Rectangle> snake;
    private Rectangle food;
    private int directionX = 1, directionY = 0; // Initial direction (right)
    private boolean gameOver = false;
    private int score = 0;
}
