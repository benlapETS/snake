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
import spypunk.snake.model.Food.Type;

public class SnakeInstance {

    private int score;

    private int speed;

    private State state;

    private int currentMovementFrame;

    private List<SnakeEvent> snakeEvents = Lists.newArrayList();

  private SnakePosition position;

    private Direction snakeDirection;

    private Optional<Direction> newSnakeDirection = Optional.empty();

    private Food food;

    private int framesSinceLastFood;

    private Map<Type, Integer> statistics;

    public enum State {
        RUNNING {
            @Override
            public State onPause() {
                return PAUSED;
            }
        },
        PAUSED {
            @Override
            public State onPause() {
                return RUNNING;
            }
        },
        GAME_OVER;

        public State onPause() {
            return this;
        }
    }

  public SnakeInstance() {
    this.speed = SnakeConstants.DEFAULT_SPEED;
    this.state = State.RUNNING;
    this.snakeDirection = Direction.DOWN;
    this.position = new SnakePosition();
    this.statistics = Arrays.stream(Type.values())
        .collect(Collectors.toMap(Function.identity(), ft -> 0));

  }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
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

    public void setSnakeEvents(final List<SnakeEvent> snakeEvents) {
        this.snakeEvents = snakeEvents;
    }

    public int getCurrentMovementFrame() {
        return currentMovementFrame;
    }

    public void setCurrentMovementFrame(final int currentMoveFrame) {
        currentMovementFrame = currentMoveFrame;
    }

  public List<Point> getSnakeParts() {
    return new ArrayList<Point>(position.getParts());
  }

  public void setPosition(final SnakePosition newPosition) {
        this.position = newPosition;
    }

    public Direction getSnakeDirection() {
        return snakeDirection;
    }

    public void setSnakeDirection(final Direction snakeDirection) {
        this.snakeDirection = snakeDirection;
    }

    public Optional<Direction> getNewSnakeDirection() {
        return newSnakeDirection;
    }

    public void setNewSnakeDirection(final Optional<Direction> newSnakeDirection) {
        this.newSnakeDirection = newSnakeDirection;
    }

    public int getFramesSinceLastFood() {
        return framesSinceLastFood;
    }

    public void setFramesSinceLastFood(final int framesSinceLastFood) {
        this.framesSinceLastFood = framesSinceLastFood;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(final Food food) {
        this.food = food;
    }

    public Map<Type, Integer> getStatistics() {
        return statistics;
    }

    public void setStatistics(final Map<Type, Integer> statistics) {
        this.statistics = statistics;
    }

  public void moveTo(Point newLocation) {
    position.updateTo(newLocation);
  }

  public boolean hasEatenFood() {
    return food.isAt(position.getHeadLocation());
  }

  public void grow() {
    position.expand();
  }

  public Point getHead() {
    return position.getHeadLocation();
  }
}
