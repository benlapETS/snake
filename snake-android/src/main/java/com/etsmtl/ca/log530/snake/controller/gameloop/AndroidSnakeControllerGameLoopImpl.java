package com.etsmtl.ca.log530.snake.controller.gameloop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.ui.controller.input.SnakeController;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public final class AndroidSnakeControllerGameLoopImpl implements SnakeControllerGameLoop, Runnable {

    private static final int SKIP_TICKS = 14;

    private final ExecutorService executorService;

    private final SnakeController snakeController;

    private volatile boolean running;

    @Inject
    public AndroidSnakeControllerGameLoopImpl(final SnakeController snakeController) {
        executorService = Executors.newSingleThreadExecutor();
        this.snakeController = snakeController;
    }

    @Override
    public void start() {
        running = true;
        executorService.execute(this);
    }

    @Override
    public void stop() {
        running = false;
        executorService.shutdown();
    }

    @Override
    public void run() {
        while (running) {

            try {
                Thread.sleep(SKIP_TICKS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            snakeController.onGameLoopUpdate();
        }
    }
}
