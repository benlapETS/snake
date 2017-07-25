package com.etsmtl.ca.log530.snake.service;

import android.graphics.Point;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;

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
import spypunk.snake.model.Snake;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.State;
import spypunk.snake.model.Type;
import spypunk.snake.service.SnakeInstanceService;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeInstanceServiceImpl implements SnakeInstanceService {
    private static final int BONUS_FOOD_FRAME_LIMIT = 120;

    private final List<Point> gridLocations = createGridLocations();

    @Override
    public void create(final Snake snake) {
        final AndroidSnakeInstanceImpl snakeInstance = new AndroidSnakeInstanceImpl();
        snakeInstance.produceNewFood(new ArrayList<>(gridLocations));
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
        final Food<Point> food = snakeInstance.getFood();
        final Type foodType = food.getType();

        if (Type.BONUS.equals(foodType)
                && snakeInstance.getFramesSinceLastFood() == BONUS_FOOD_FRAME_LIMIT) {
            snakeInstance.produceNewFood(new ArrayList<Point>(gridLocations));
        }
    }

    private void handleDirection(final SnakeInstance snakeInstance) {
        final Optional<AndroidSnakeDirection> newSnakeDirection = snakeInstance
                .getNewSnakeDirection();

        if (!newSnakeDirection.isPresent()) {
            return;
        }

        snakeInstance.setSnakeDirection(
                snakeInstance.getSnakeDirection().applyDirection(newSnakeDirection.get()));
        snakeInstance.setNewSnakeDirection(Optional.empty());
    }
}
