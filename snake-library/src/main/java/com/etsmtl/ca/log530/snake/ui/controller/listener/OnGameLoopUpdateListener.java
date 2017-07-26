package com.etsmtl.ca.log530.snake.ui.controller.listener;

import java.util.EventListener;

/**
 * A listener for game loop updates
 */
public abstract class OnGameLoopUpdateListener implements EventListener {
    public abstract void onGameLoopUpdateEvent();
}
