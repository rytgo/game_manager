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

    public void move() {
        // Move the snake in the current direction
        Rectangle newHead = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        newHead.setX(body.get(0).getX() + directionX * TILE_SIZE);
        newHead.setY(body.get(0).getY() + directionY * TILE_SIZE);

        body.add(0, newHead);  // Add the new head to the front of the snake
    }

    public void grow() {
        // Add a new segment to the snake (does not remove the tail)
        Rectangle newSegment = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        // Place new segment at the position of the last tail segment
        newSegment.setX(body.get(body.size() - 1).getX());
        newSegment.setY(body.get(body.size() - 1).getY());
        body.add(newSegment);
    }

    public void moveTail() {
        // Remove the last segment (tail) of the snake
        Rectangle tail = body.remove(body.size() - 1);
        // Update the graphical representation
        tail.setVisible(false);
    }

    public void setDirection(int x, int y) {
        // Set the direction of movement
        directionX = x;
        directionY = y;
    }

    public ArrayList<Rectangle> getBody() {
        return body;
    }

    public Rectangle getHead() {
        return body.get(0);
    }

    public boolean checkCollisionWithSelf() {
        // Check if the snake's head collides with any part of its body
        for (int i = 1; i < body.size(); i++) {
            if (getHead().getBoundsInParent().intersects(body.get(i).getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}
