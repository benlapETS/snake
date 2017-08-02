package com.etsmtl.ca.log530.snake.service;

import android.graphics.Point;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeFood;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Singleton;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.State;
import spypunk.snake.model.Type;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeInstanceServiceImpl extends AndroidSnakeInstanceService {
    private static final int BONUS_FOOD_FRAME_LIMIT = 120;

    private final List<Point> gridLocations = createGridLocations();

    @Override
    public void create(final AndroidSnakeImpl snake) {
        final AndroidSnakeInstanceImpl snakeInstance = new AndroidSnakeInstanceImpl();
        snakeInstance.produceNewFood(new ArrayList<>(gridLocations));
        snake.setSnakeInstance(snakeInstance);
    }

    @Override
    public void update(final AndroidSnakeInstanceImpl snakeInstance) {
        snakeInstance.clearSnakeEvents();
        if (!snakeInstance.isAt(State.RUNNING)) {
            return;
        }
        snakeInstance.updateFrames();
        handleMovement(snakeInstance);
        handleBonusFood(snakeInstance);
    }

    @Override
    public void pause(final AndroidSnakeInstanceImpl snakeInstance) {
        snakeInstance.togglePause();

    }

    @Override
    public void updateDirection(final AndroidSnakeInstanceImpl snakeInstance,
                                final AndroidSnakeDirection direction) {
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

    private void handleMovement(final AndroidSnakeInstanceImpl snakeInstance) {
        if (!snakeInstance.canHandleMovement()) {
            return;
        }

        handleDirection(snakeInstance);

        if (snakeInstance.canMove()) {
            snakeInstance.move(new ArrayList<>(gridLocations));
        } else {
            snakeInstance.setState(State.GAME_OVER);
            snakeInstance.addSnakeEvents(SnakeEvent.GAME_OVER);
        }

        snakeInstance.resetCurrentMovementFrame();
    }

    private void handleBonusFood(final AndroidSnakeInstanceImpl snakeInstance) {
        final AndroidSnakeFood food = snakeInstance.getFood();
        final Type foodType = food.getType();

        if (Type.BONUS.equals(foodType)
                && snakeInstance.getFramesSinceLastFood() == BONUS_FOOD_FRAME_LIMIT) {
            snakeInstance.produceNewFood(new ArrayList<>(gridLocations));
        }
    }

    private void handleDirection(final AndroidSnakeInstanceImpl snakeInstance) {
        final Optional<? extends AndroidSnakeDirection> newSnakeDirection = snakeInstance
                .getNewSnakeDirection();

        if (!newSnakeDirection.isPresent()) {
            return;
        }

        snakeInstance.setSnakeDirection(
                snakeInstance.getSnakeDirection().applyDirection(newSnakeDirection.get()));
        snakeInstance.setNewSnakeDirection(Optional.empty());
    }
}
