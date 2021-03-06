package com.etsmtl.ca.log530.snake.controller.gameloop;

import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by gabar on 2017-07-26.
 */
public class GameLoopThread extends Thread {
    private static final int SKIP_TICKS = 14;

    private volatile boolean running;

    private volatile ExecutorService executorService;

    public void setOnGameEventListener(OnGameEventListener onGameEventListener) {
        this.onGameEventListener = onGameEventListener;
    }

    private OnGameEventListener onGameEventListener;

    public GameLoopThread(OnGameEventListener onGameEventListener) {
        super();
        this.onGameEventListener = onGameEventListener;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void start() {
        running = true;
        executorService.execute(this);
    }

    public void terminate() {
        running = false;
        executorService.shutdown();
        try {
            executorService.awaitTermination(100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            //do nothing, hope for the best
        }
    }

    @Override
    public void run() {
        while (running) {

            try {
                Thread.sleep(SKIP_TICKS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onGameEventListener.onGameLoopUpdateEvent();
        }
    }
}
