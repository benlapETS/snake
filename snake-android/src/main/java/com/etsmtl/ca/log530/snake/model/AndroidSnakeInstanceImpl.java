package com.etsmtl.ca.log530.snake.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.State;
import spypunk.snake.model.Type;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakeInstanceImpl extends SnakeInstance<Point, AndroidSnakeDirection> {
    private int score;

    private int speed;

    private State state;

    private int currentMovementFrame;

    private AndroidSnakePosition position;

    private AndroidSnakeDirection snakeDirection;

    private AndroidSnakeFood food;

    private int framesSinceLastFood;

    private Map<Type, Integer> statistics;

    public AndroidSnakeInstanceImpl() {
        this.speed = SnakeConstants.DEFAULT_SPEED;
        this.state = State.RUNNING;
        this.snakeDirection = AndroidSnakeDirection.DOWN;
        this.position = new AndroidSnakePosition();
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

    public int getCurrentMovementFrame() {
        return currentMovementFrame;
    }

    public LinkedList<Point> getSnakeParts() {
        // TODO Propagate queue to system later
        return position.getParts();
    }

    public AndroidSnakeDirection getSnakeDirection() {
        return snakeDirection;
    }

    @Override
    public void setSnakeDirection(AndroidSnakeDirection snakeDirection) {
        this.snakeDirection = snakeDirection;

    }

    public int getFramesSinceLastFood() {
        return framesSinceLastFood;
    }

    public AndroidSnakeFood getFood() {
        return food;
    }

    public Map<Type, Integer> getStatistics() {
        return statistics;
    }

    public void clearSnakeEvents() {
        getSnakeEvents().clear();
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
        food = new AndroidSnakeFood(foodPossibleLocations);
        framesSinceLastFood = 0;
    }

    public void addSnakeEvents(SnakeEvent evt) {
        getSnakeEvents().add(evt);
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
        return snakeDirection.applyPoint(position.getHeadLocation());
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
            getSnakeEvents().add(SnakeEvent.FOOD_EATEN);
            produceNewFood(gridLocations);
        }
    }
}
