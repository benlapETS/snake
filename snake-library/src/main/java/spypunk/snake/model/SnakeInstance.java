package spypunk.snake.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import spypunk.snake.model.Direction;

/**
 * Created by gabar on 2017-07-20.
 */

public interface SnakeInstance<T, D extends Direction> {


    int getScore();

    void updateScore();

    int getSpeed();

    State getState();

    void setState(final State state);

    List<SnakeEvent> getSnakeEvents();

    int getCurrentMovementFrame();

    List<T> getSnakeParts();

    D getSnakeDirection();

    void setSnakeDirection(final D snakeDirection);

    Optional<? extends D> getNewSnakeDirection();

    void setNewSnakeDirection(final Optional<? extends D> newSnakeDirection);

    int getFramesSinceLastFood();

    Food getFood();

    Map<Type, Integer> getStatistics();

    void clearSnakeEvents();

    void updateFrames();

    void togglePause();

    void produceNewFood(List<T> foodPossibleLocations);

    void addSnakeEvents(SnakeEvent evt);

    void resetCurrentMovementFrame();

    boolean isAt(State state);

    boolean canHandleMovement();

    T getSnakeHeadPartNextLocation();

    boolean canMove();

    void updateStatistics();

    void move(List<T> gridLocations);
}
