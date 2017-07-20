/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2, as published
 * by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Singleton;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.Direction;
import spypunk.snake.model.Food;
import spypunk.snake.model.Food.Type;
import spypunk.snake.model.Snake;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.SnakeInstance.State;

@Singleton
public class SnakeInstanceServiceImpl implements SnakeInstanceService {
  private static final int BONUS_FOOD_FRAME_LIMIT = 120;
  private final List<Point> gridLocations = createGridLocations();

  @Override
  public void create(final Snake snake) {
    final SnakeInstance snakeInstance = new SnakeInstance();
    snakeInstance.produceNewFood(new ArrayList<Point>(gridLocations));
    snake.setSnakeInstance(snakeInstance);
  }

  @Override
  public void update(final SnakeInstance snakeInstance) {
    snakeInstance.clearSnakeEvents();
    if (!snakeInstance.isAt(State.RUNNING)) {
      return;
    }
    snakeInstance.updateFrames();
    handleMovement(snakeInstance);
    handleBonusFood(snakeInstance);
  }

  @Override
  public void pause(final SnakeInstance snakeInstance) {
    snakeInstance.togglePause();
  }

  @Override
  public void updateDirection(final SnakeInstance snakeInstance,
      final Direction direction) {
    if (snakeInstance.isAt(State.RUNNING)) {
      snakeInstance.setNewSnakeDirection(Optional.of(direction));
    }
  }

  private static List<Point> createGridLocations() {
    return IntStream.range(0, SnakeConstants.WIDTH)
        .mapToObj(x -> IntStream.range(0, SnakeConstants.HEIGHT)
            .mapToObj(y -> new Point(x, y)).collect(Collectors.toList()))
        .flatMap(Collection::stream).collect(Collectors.toList());
  }

  private void handleMovement(final SnakeInstance snakeInstance) {
    if (!snakeInstance.canHandleMovement()) {
      return;
    }

    handleDirection(snakeInstance);

    if (snakeInstance.canMove()) {
      snakeInstance.move(new ArrayList<Point>(gridLocations));
    } else {
      snakeInstance.setState(State.GAME_OVER);
      snakeInstance.addSnakeEvents(SnakeEvent.GAME_OVER);
    }

    snakeInstance.resetCurrentMovementFrame();
  }

  private void handleBonusFood(final SnakeInstance snakeInstance) {
    final Food food = snakeInstance.getFood();
    final Type foodType = food.getType();

    if (Type.BONUS.equals(foodType)
        && snakeInstance.getFramesSinceLastFood() == BONUS_FOOD_FRAME_LIMIT) {
      snakeInstance.produceNewFood(new ArrayList<Point>(gridLocations));
    }
  }

  private void handleDirection(final SnakeInstance snakeInstance) {
    final Optional<Direction> newSnakeDirection = snakeInstance
        .getNewSnakeDirection();

    if (!newSnakeDirection.isPresent()) {
      return;
    }

    snakeInstance.setSnakeDirection(
        snakeInstance.getSnakeDirection().apply(newSnakeDirection.get()));
    snakeInstance.setNewSnakeDirection(Optional.empty());
  }
}
