package com.test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Rectangle> body;
    private int directionX, directionY;
    private static final int TILE_SIZE = 20;

    public Snake(int startX, int startY) {
        body = new ArrayList<>();
        directionX = 1;     // Initial direction: moving right
        directionY = 0;

        // Add the initial head of the snake
        Rectangle head = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        head.setX(startX);
        head.setY(startY);
        body.add(head);
    }


}
