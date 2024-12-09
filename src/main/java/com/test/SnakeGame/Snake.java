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
        // Move the body: each block follows the one before it
        Block current = head;
        while (current != null) {
            current.move();  // Move the block to the position of the previous one
            current = current.getNext();
        }

        // Move the head: update the position of the head (moving it in the current direction)
        head.setPosition(head.getX() + directionX, head.getY() + directionY);
    
    }

    public void grow() {
        // Add a new segment to the snake (does not remove the tail)
        Block newSegment = new Block(tail.getX(), tail.getY());  // Position at the last tail
        tail.setNext(newSegment);  // Link the current tail to the new segment
        tail = newSegment;  // Update the tail to the new segment
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
