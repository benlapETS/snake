package com.etsmtl.ca.log530.snake.controller.gameloop;

import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameEventListener;

import java.util.Optional;

import javax.inject.Singleton;

import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;


/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public final class AndroidSnakeControllerGameLoopImpl implements SnakeControllerGameLoop {

    private OnGameEventListener listener;

    private Optional<GameLoopThread> gameLoopThread = Optional.empty();

    @Override
    public void setOnGameLoopUpdateListener(OnGameEventListener listener) {
        this.listener = listener;
        gameLoopThread.ifPresent(thread->thread.setOnGameEventListener(listener));
    }

    @Override
    public void start() {
        if(!gameLoopThread.isPresent()){
            gameLoopThread = Optional.of(new GameLoopThread(listener));
            gameLoopThread.get().start();
        }
    }

    @Override
    public void stop() {
        gameLoopThread.get().terminate();
        gameLoopThread = null;
    }

}
