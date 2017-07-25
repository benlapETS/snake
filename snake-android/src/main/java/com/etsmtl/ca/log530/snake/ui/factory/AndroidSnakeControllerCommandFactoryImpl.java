package com.etsmtl.ca.log530.snake.ui.factory;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.model.Direction;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.State;
import spypunk.snake.service.SnakeInstanceService;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.controller.input.SnakeController;
import spypunk.snake.ui.factory.SnakeControllerCommandFactory;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeControllerCommandFactoryImpl implements SnakeControllerCommandFactory {

    private final SnakeInstanceService snakeInstanceService;

    private final SnakeController snakeController;
    //private final SoundPool pool;

    @Inject
    public AndroidSnakeControllerCommandFactoryImpl(final SnakeInstanceService snakeInstanceService,
                                             final SnakeController snakeController) {
        this.snakeInstanceService = snakeInstanceService;
        this.snakeController = snakeController;
        //this.pool = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(AudioAttributes.CONTENT_TYPE_MUSIC)
    }

    @Override
    public SnakeControllerCommand createNewGameSnakeControllerCommand() {
        //TODO start music
        //player.setDataSource(context.getResources().openRawResourceFd(R.raw.background));
        return snakeInstanceService::create;
    }

    @Override
    public SnakeControllerCommand createPauseSnakeControllerCommand() {
        return snake -> {
            final SnakeInstance snakeInstance = snake.getSnakeInstance();

            if (snakeInstance != null) {
                snakeInstanceService.pause(snakeInstance);

                final State state = snakeInstance.getState();

                if (State.RUNNING.equals(state) || State.PAUSED.equals(state)) {
                    //TODO pause music
                    //soundService.pauseMusic();
                }
            }
        };
    }

    @Override
    public SnakeControllerCommand createDirectionSnakeControllerCommand(final Direction direction) {
        return snake -> {
            final SnakeInstance snakeInstance = snake.getSnakeInstance();

            if (snakeInstance != null) {
                snakeInstanceService.updateDirection(snake.getSnakeInstance(), direction);
            }
        };
    }

    @Override
    public SnakeControllerCommand createMuteSnakeControllerCommand() {
        return snake -> {
            //TODO pause music
            //soundService.mute();
            //snakeController.getSnakeView().setMute(soundService.isMute());
        };
    }

    @Override
    public SnakeControllerCommand createIncreaseVolumeSnakeControllerCommand() {
        return snake -> {
            //TODO increase volume
            //soundService.increaseVolume()
        };
    }

    @Override
    public SnakeControllerCommand createDecreaseVolumeSnakeControllerCommand() {
        return snake -> {
            //TODO decrease volume
            //soundService.decreaseVolume()
        };
    }

    @Override
    public SnakeControllerCommand createGameOverSnakeControllerCommand() {
        return snake -> {
            //TODO play game over
            //soundService.playMusic(SoundImpl.GAME_OVER)
        };
    }

    @Override
    public SnakeControllerCommand createFoodEatenSnakeControllerCommand() {
        return snake -> {
            //TODO play food eaten
            //soundService.playSound(SoundImpl.FOOD_EATEN)
        };
    }
}
