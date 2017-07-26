package spypunk.snake.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import spypunk.snake.model.Direction;

/**
 * Created by gabar on 2017-07-20.
 */

public abstract class SnakeInstance<T, D extends Direction> {

    private List<SnakeEvent> snakeEvents = Lists.newArrayList();

    private Optional<? extends D> newSnakeDirection = Optional.empty();

    public abstract int getScore();

    public abstract void updateScore();

    public abstract int getSpeed();

    public abstract State getState();

    public abstract void setState(final State state);

    public final List<SnakeEvent> getSnakeEvents() {
        return this.snakeEvents;
    }

    public abstract int getCurrentMovementFrame();

    public abstract List<T> getSnakeParts();

    public abstract D getSnakeDirection();

    public abstract void setSnakeDirection(final D snakeDirection);

    public final Optional<? extends D> getNewSnakeDirection(){
        return this.newSnakeDirection;
    }

    public final void setNewSnakeDirection(final Optional<? extends D> newSnakeDirection){
        this.newSnakeDirection = newSnakeDirection;
    }

    public abstract int getFramesSinceLastFood();

    public abstract Food getFood();

    public abstract Map<Type, Integer> getStatistics();

    public abstract void clearSnakeEvents();

    public abstract void updateFrames();

    public abstract void togglePause();

    public abstract void produceNewFood(List<T> foodPossibleLocations);

    public abstract void addSnakeEvents(SnakeEvent evt);

    public abstract void resetCurrentMovementFrame();

    public abstract boolean isAt(State state);

    public abstract boolean canHandleMovement();

    public abstract T getSnakeHeadPartNextLocation();

    public abstract boolean canMove();

    public abstract void updateStatistics();

    public abstract void move(List<T> gridLocations);
}
