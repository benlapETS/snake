package com.etsmtl.ca.log530.snake.ui.controller;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameEventListener;

import java.util.Optional;

import spypunk.snake.ui.controller.input.SnakeController;

/**
 * A controller for android snake games. It offers methods
 * Created by gabar on 2017-07-25.
 */

public abstract class AndroidSnakeController implements SnakeController {

    private Optional<OnGameEventListener> updateListener = Optional.empty();
    public final void setOnGameLoopUpdateListener(OnGameEventListener listener){
        this.updateListener = Optional.ofNullable(listener);
    }

    public final Optional<OnGameEventListener> getUpdateListener() {
        return updateListener;
    }

    public abstract AndroidSnakeImpl getSnake();

    public abstract void stop();

    public abstract void onFoodEaten();

    public abstract void onGameOver();

    public abstract void onGameUnpaused();

    public abstract void onGamePaused();
}

