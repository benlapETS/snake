/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.controller.gameloop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.ui.controller.input.SnakeController;

@Singleton
public final class SnakeControllerGameLoopImpl implements SnakeControllerGameLoop, Runnable {

    private static final int SKIP_TICKS = 14;

    private final ExecutorService executorService;

    private final SnakeController snakeController;

    private volatile boolean running;

    @Inject
    public SnakeControllerGameLoopImpl(final SnakeController snakeController) {
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
