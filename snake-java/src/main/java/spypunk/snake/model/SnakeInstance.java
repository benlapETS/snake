package spypunk.snake.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import spypunk.snake.constants.SnakeConstants;

/**
 * Created by gabar on 2017-07-20.
 */

public abstract class SnakeInstance<T> {
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

    public SnakeInstance() {
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

    public abstract List<T> getSnakeParts();

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

    public abstract void produceNewFood(List<T> foodPossibleLocations);

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

    public abstract T getSnakeHeadPartNextLocation();

    public abstract boolean canMove();

    public void updateStatistics() {
        final Integer foodTypeCount = statistics.get(food.getType());
        statistics.put(food.getType(), foodTypeCount + 1);
    }

    public abstract void move(List<T> gridLocations);
}
