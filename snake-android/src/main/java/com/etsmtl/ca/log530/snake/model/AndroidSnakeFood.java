package com.etsmtl.ca.log530.snake.model;

import android.graphics.Point;

import java.util.List;
import java.util.Random;

import spypunk.snake.model.Food;
import spypunk.snake.model.Type;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakeFood implements Food<Point> {
    private static final int BONUS_FOOD_RANDOM = 10;

    private Point location;

    private Type type;

    public AndroidSnakeFood(List<Point> foodPossibleLocations) {
        Random random = new Random();
        final int foodIndex = random.nextInt(foodPossibleLocations.size());
        final Point location = foodPossibleLocations.get(foodIndex);
        final Type type = random.nextInt(BONUS_FOOD_RANDOM) == 0 ? Type.BONUS
                : Type.NORMAL;
        this.location = location;
        this.type = type;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public boolean isAt(Point otherLocation) {
        return location.equals(otherLocation);
    }

    public int getPoints() {
        return type.getPoints();
    }
}
