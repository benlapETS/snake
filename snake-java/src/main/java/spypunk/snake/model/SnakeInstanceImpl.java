/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import spypunk.snake.constants.SnakeConstants;

public class SnakeInstanceImpl extends SnakeInstance<Point>{
    private int score;

    private int speed;

    private State state;

    private int currentMovementFrame;

    private List<SnakeEvent> snakeEvents = Lists.newArrayList();

  private SnakePosition position;

    private SnakeDirection snakeDirection;

    private Optional<SnakeDirection> newSnakeDirection = Optional.empty();

    private SnakeFood food;

    private int framesSinceLastFood;

    private Map<Type, Integer> statistics;

    public SnakeInstanceImpl() {
    this.speed = SnakeConstants.DEFAULT_SPEED;
    this.state = State.RUNNING;
    this.snakeDirection = SnakeDirection.DOWN;
    this.position = new SnakePosition();
    this.statistics = Arrays.stream(Type.values())
        .collect(Collectors.toMap(Function.identity(), ft -> 0));
  }

    public int getScore() {
        return score;
    }

  public void updateScore() {
    this.score += food.getPoints();
  }

    public int getSpeed() {
        return speed;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public List<SnakeEvent> getSnakeEvents() {
        return snakeEvents;
    }

    public int getCurrentMovementFrame() {
        return currentMovementFrame;
    }

  public List<Point> getSnakeParts() {
    // TODO Propagate queue to system later
    return new ArrayList<>(position.getParts());
  }

    public SnakeDirection getSnakeDirection() {
        return snakeDirection;
    }

    public void setSnakeDirection(final SnakeDirection snakeDirection) {
        this.snakeDirection = snakeDirection;
    }

    public Optional<SnakeDirection> getNewSnakeDirection() {
        return newSnakeDirection;
    }

    public void setNewSnakeDirection(final Optional<SnakeDirection> newSnakeDirection) {
        this.newSnakeDirection = newSnakeDirection;
    }

    public int getFramesSinceLastFood() {
        return framesSinceLastFood;
    }

    public SnakeFood getFood() {
        return food;
    }

    public Map<Type, Integer> getStatistics() {
        return statistics;
    }

  public void clearSnakeEvents() {
    snakeEvents.clear();
  }

  public void updateFrames() {
    currentMovementFrame += 1;
    framesSinceLastFood += 1;
  }

  public void togglePause() {
    state = state.onPause();
  }

  public void produceNewFood(List<Point> foodPossibleLocations) {
    foodPossibleLocations.removeAll(position.getParts());
    food = new SnakeFood(foodPossibleLocations);
    framesSinceLastFood = 0;
  }

  public void addSnakeEvents(SnakeEvent evt) {
    snakeEvents.add(evt);
  }

  public void resetCurrentMovementFrame() {
    currentMovementFrame = 0;
  }

  public boolean isAt(State state) {
    return this.state.equals(state);
  }

  public boolean canHandleMovement() {
    return currentMovementFrame > speed;
  }

  public Point getSnakeHeadPartNextLocation() {
    return snakeDirection.apply(position.getHeadLocation());
  }

  public boolean canMove() {
    final Point newLocation = getSnakeHeadPartNextLocation();
    final boolean isLocationOutOfBounds = newLocation.x < 0
        || newLocation.x == SnakeConstants.WIDTH || newLocation.y < 0
        || newLocation.y == SnakeConstants.HEIGHT;
    return !position.overlaps(newLocation) && !isLocationOutOfBounds;
  }

  public void updateStatistics() {
    final Integer foodTypeCount = statistics.get(food.getType());
    statistics.put(food.getType(), foodTypeCount + 1);
  }

  public void move(List<Point> gridLocations) {
    final Point newLocation = getSnakeHeadPartNextLocation();
    position.updateTo(newLocation);

    if (food.isAt(position.getHeadLocation())) {
      position.expand();
      updateScore();
      updateStatistics();
      snakeEvents.add(SnakeEvent.FOOD_EATEN);
      produceNewFood(gridLocations);
    }
  }
}
