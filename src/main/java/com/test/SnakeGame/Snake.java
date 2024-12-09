package com.test.SnakeGame;

import javafx.scene.canvas.GraphicsContext;
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
        Rectangle head = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
        head.setX(startX);
        head.setY(startY);
        body.add(head);
    }

    public void move() {
        // Move the snake in the current direction
        Rectangle newHead = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
        newHead.setX(body.get(0).getX() + directionX * TILE_SIZE);
        newHead.setY(body.get(0).getY() + directionY * TILE_SIZE);

        body.add(0, newHead);  // Add the new head to the front of the snake
        if (body.size() > 1){
            body.remove(body.size() - 1);   //remove the last segment of the snake ot keep its length
        }
    }

    public void grow() {
        // Add a new segment to the snake (does not remove the tail)
        Rectangle newSegment = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
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

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public ArrayList<Rectangle> getBody() {
        return body;
    }

    public Rectangle getHead() {
        return body.get(0);
    }

    // TODO: Fix bug with food here
    public boolean checkCollisionWithSelf() {
        Rectangle head = getHead();
        // Check if the snake's head collides with any part of its body
        for (int i = 1; i < body.size(); i++) {
            Rectangle segment = body.get(i);
            if (head.getX() == segment.getX() && head.getY() == segment.getY()) {
                return true;
            }
        }
        return false;
    }

    // Draw snake on canvas
    public void render(GraphicsContext gc) {
        for (int i = 0; i < body.size(); i++) {
            Rectangle segment = body.get(i);
            if (i == 0) {  // Head is rendered in lime green
                gc.setFill(Color.LIME);
            } else {
                gc.setFill(Color.WHITE);  // Body is white
            }
            gc.fillRect(segment.getX(), segment.getY(), segment.getWidth(), segment.getHeight());
        }
    }
}
