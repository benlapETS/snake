package com.etsmtl.ca.log530.snake.ui.controller;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameLoopUpdateListener;

import java.util.Optional;

import spypunk.snake.ui.controller.input.SnakeController;

/**
 * A controller for android snake games. It offers methods
 * Created by gabar on 2017-07-25.
 */

public abstract class AndroidSnakeController implements SnakeController {

    Optional<OnGameLoopUpdateListener> updateListener = Optional.empty();
    public final void setOnGameLoopUpdateListener(OnGameLoopUpdateListener listener){
        this.updateListener = Optional.ofNullable(listener);
    }

    public final Optional<OnGameLoopUpdateListener> getUpdateListener() {
        return updateListener;
    }

    public abstract AndroidSnakeImpl getSnake();

    public abstract void stop();
}

