package com.test.SnakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {
    private Block head;  // Head of the snake (first block)
    private Block tail;  // Tail of the snake (last block)
    private int directionX, directionY;
    public static final int TILE_SIZE = 20;

    public Snake(int startX, int startY) {
        head = new Block(startX, startY);
        tail = head;  // Initially, the head and tail are the same
        directionX = 1;     // Initial direction: moving right
        directionY = 0;
    }

    public void move() {
        int prevX = head.getX();
        int prevY = head.getY();

        head.setPosition(head.getX() + directionX * TILE_SIZE, head.getY() + directionY * TILE_SIZE);

        Block current = head.getNext();
        while (current != null) {
            int tempX = current.getX();
            int tempY = current.getY();
            current.setPosition(prevX, prevY);
            prevX = tempX;
            prevY = tempY;
            current = current.getNext();
        }
    }

    public void grow() {
        // Determine the position for the new segment based on the tail's current
        // position and direction
        int newX = tail.getX() - directionX * TILE_SIZE;
        int newY = tail.getY() - directionY * TILE_SIZE;

        // Create a new segment at the calculated position
        Block newSegment = new Block(newX, newY);

        // Link the new segment to the current tail
        tail.setNext(newSegment);

        // Update the tail reference to the new segment
        tail = newSegment;
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
    
    public Block getHead() {
        return head;
    }

    public Block getTail() {
        return tail;
    }

    // TODO: Fix bug with food here
    public boolean checkCollisionWithSelf() {
        Block current = head.getNext();  // Start from the second block
        while (current != null) {
            if (head.getX() == current.getX() && head.getY() == current.getY()) {
                return true;  // Collision with self
            }
            current = current.getNext();
        }
        return false;
    }

    // Draw snake on canvas
    public void render(GraphicsContext gc) {
        Block current = head;
        while (current != null) {
            if (current == head) {  // Head is rendered in lime green
                gc.setFill(Color.LIME);
            } else {
                gc.setFill(Color.WHITE);  // Body is white
            }
            current.render(gc);
            current = current.getNext();  // Move to the next block
        }
    }
}
