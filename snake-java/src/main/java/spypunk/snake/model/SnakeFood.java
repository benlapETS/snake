/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.model;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class SnakeFood implements Food<Point>{
  private static final int BONUS_FOOD_RANDOM = 10;

    private Point location;

    private Type type;

    public SnakeFood(List<Point> foodPossibleLocations) {
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
