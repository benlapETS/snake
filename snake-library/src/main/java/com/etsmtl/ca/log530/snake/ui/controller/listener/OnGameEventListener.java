package com.etsmtl.ca.log530.snake.ui.controller.listener;

import java.util.EventListener;

/**
 * A listener for game loop updates
 */
public abstract class OnGameEventListener implements EventListener {
    public abstract void onGameLoopUpdateEvent();
    public  void onGamePausedEvent(){

    }
    public  void onGameUnpausedEvent(){

    }
    public  void onGameOverEvent(){

    }
    public  void onFoodEatenEvent(){

    }
}
