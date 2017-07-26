/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.factory;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.model.SnakeDirection;
import spypunk.snake.model.SnakeImpl;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.SnakeInstanceImpl;
import spypunk.snake.model.State;
import spypunk.snake.service.AWTSnakeInstanceService;
import spypunk.snake.sound.SoundImpl;
import spypunk.snake.sound.service.SoundService;
import spypunk.snake.ui.controller.AWTSnakeController;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;

@Singleton
public class SnakeControllerCommandFactoryImpl extends SnakeControllerCommandFactory {

    private final AWTSnakeInstanceService snakeInstanceService;

    private final SoundService soundService;

    private final AWTSnakeController snakeController;

    @Inject
    public SnakeControllerCommandFactoryImpl(final AWTSnakeInstanceService snakeInstanceService,
                                             final SoundService soundService,
                                             final AWTSnakeController snakeController) {
        this.snakeInstanceService = snakeInstanceService;
        this.soundService = soundService;
        this.snakeController = snakeController;
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createNewGameSnakeControllerCommand() {
        return snake -> {
            snakeInstanceService.create(snake);

            soundService.playMusic(SoundImpl.BACKGROUND);
        };
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createPauseSnakeControllerCommand() {
        return snake -> {
            final SnakeInstanceImpl snakeInstance = snake.getSnakeInstance();

            if (snakeInstance != null) {
                snakeInstanceService.pause(snakeInstance);

                final State state = snakeInstance.getState();

                if (State.RUNNING.equals(state) || State.PAUSED.equals(state)) {
                    soundService.pauseMusic();
                }
            }
        };
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createDirectionSnakeControllerCommand(final SnakeDirection direction) {
        return snake -> {
            final SnakeInstance snakeInstance = snake.getSnakeInstance();

            if (snakeInstance != null) {
                snakeInstanceService.updateDirection(snake.getSnakeInstance(), direction);
            }
        };
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createMuteSnakeControllerCommand() {
        return snake -> {
            soundService.mute();
            snakeController.getSnakeView().setMute(soundService.isMute());
        };
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createIncreaseVolumeSnakeControllerCommand() {
        return snake -> soundService.increaseVolume();
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createDecreaseVolumeSnakeControllerCommand() {
        return snake -> soundService.decreaseVolume();
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createGameOverSnakeControllerCommand() {
        return snake -> soundService.playMusic(SoundImpl.GAME_OVER);
    }

    @Override
    public SnakeControllerCommand<SnakeImpl> createFoodEatenSnakeControllerCommand() {
        return snake -> soundService.playSound(SoundImpl.FOOD_EATEN);
    }
}
