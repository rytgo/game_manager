package com.test.SnakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {
    private int x, y;  // Coordinates of the block
    private Block next;  // Pointer to the next block in the snake body
    private Rectangle rectangle;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(Snake.TILE_SIZE, Snake.TILE_SIZE, Color.WHITE);  // default color
        this.rectangle.setX(x);
        this.rectangle.setY(y);
        this.next = null;
    }

    // Getters and setters for the block's position and next block
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.rectangle.setX(x);
        this.rectangle.setY(y);
    }

    // Render the block on the canvas
    public void render(GraphicsContext gc) {
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
}
