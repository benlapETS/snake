package com.etsmtl.ca.log530.snake.ui.factory;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceService;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceServiceImpl;
import com.etsmtl.ca.log530.snake.ui.controller.AndroidSnakeController;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.State;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.controller.input.SnakeController;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidControllerCommandFactoryImpl extends AndroidControllerCommandFactory {

    private final AndroidSnakeInstanceService snakeInstanceService;

    //private final SoundPool pool;

    @Inject
    public AndroidControllerCommandFactoryImpl(final AndroidSnakeInstanceService snakeInstanceService) {
        this.snakeInstanceService = snakeInstanceService;
        //this.pool = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(AudioAttributes.CONTENT_TYPE_MUSIC)
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createNewGameSnakeControllerCommand() {
        //TODO start music
        //player.setDataSource(context.getResources().openRawResourceFd(R.raw.background));
        return snakeInstanceService::create;
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createPauseSnakeControllerCommand() {
        return snake -> {
            final AndroidSnakeInstanceImpl snakeInstance = snake.getSnakeInstance();

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
    public SnakeControllerCommand<AndroidSnakeImpl> createDirectionSnakeControllerCommand(final AndroidSnakeDirection direction) {
        return snake -> {
            final SnakeInstance snakeInstance = snake.getSnakeInstance();

            if (snakeInstance != null) {
                snakeInstanceService.updateDirection(snake.getSnakeInstance(), direction);
            }
        };
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createMuteSnakeControllerCommand() {
        return snake -> {
            //TODO pause music
            //soundService.mute();
            //snakeController.getSnakeView().setMute(soundService.isMute());
        };
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createIncreaseVolumeSnakeControllerCommand() {
        return snake -> {
            //TODO increase volume
            //soundService.increaseVolume()
        };
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createDecreaseVolumeSnakeControllerCommand() {
        return snake -> {
            //TODO decrease volume
            //soundService.decreaseVolume()
        };
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createGameOverSnakeControllerCommand() {
        return snake -> {
            //TODO play game over
            //soundService.playMusic(SoundImpl.GAME_OVER)
        };
    }

    @Override
    public SnakeControllerCommand<AndroidSnakeImpl> createFoodEatenSnakeControllerCommand() {
        return snake -> {
            //TODO play food eaten
            //soundService.playSound(SoundImpl.FOOD_EATEN)
        };
    }
}
