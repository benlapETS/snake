package com.etsmtl.ca.log530.snake.model;

import android.graphics.Point;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.Position;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakePosition implements Position<Point> {
    private LinkedList<Point> snakeParts;
    private Point lastPartRemoved;

    /**
     *
     */
    public AndroidSnakePosition() {
        snakeParts = new LinkedList<>();
        final int x = SnakeConstants.WIDTH / 2;
        snakeParts.add(new Point(x, 2));
        snakeParts.add(new Point(x, 1));
        snakeParts.add(new Point(x, 0));
    }

    public LinkedList<Point> getParts() {
        return snakeParts;
    }

    public void updateTo(Point newLocation) {
        snakeParts.addFirst(newLocation);
        lastPartRemoved = snakeParts.removeLast();
    }

    public Point getHeadLocation() {
        return snakeParts.peek();
    }

    public void expand() {
        snakeParts.add(lastPartRemoved);
    }

    public boolean overlaps(Point p) {
        return snakeParts.contains(p);
    }

}
