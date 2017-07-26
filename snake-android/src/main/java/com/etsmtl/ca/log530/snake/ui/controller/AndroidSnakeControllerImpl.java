package com.etsmtl.ca.log530.snake.ui.controller;

import com.etsmtl.ca.log530.snake.factory.AndroidSnakeFactory;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceService;
import com.etsmtl.ca.log530.snake.ui.controller.event.AndroidSnakeControllerSnakeEventHandler;
import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameLoopUpdateListener;
import com.etsmtl.ca.log530.snake.ui.input.AndroidSnakeControllerInputHandler;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;

/**
 * Created by gabar on 2017-07-25.
 */
@Singleton
public class AndroidSnakeControllerImpl extends AndroidSnakeController {

    private AndroidSnakeInstanceService snakeInstanceService;

    private AndroidSnakeImpl snake;

    private SnakeControllerGameLoop snakeControllerGameLoop;

    private AndroidSnakeControllerInputHandler snakeControllerInputHandler;

    private AndroidSnakeControllerSnakeEventHandler snakeControllerSnakeEventHandler;

    @Inject
    public AndroidSnakeControllerImpl(AndroidSnakeInstanceService snakeInstanceService,
                                      AndroidSnakeFactory snakeFactory,
                                      AndroidSnakeControllerInputHandler snakeControllerInputHandler,
                                      AndroidSnakeControllerSnakeEventHandler snakeControllerSnakeEventHandler,
                                      SnakeControllerGameLoop snakeControllerGameLoop){

        this.snakeInstanceService = snakeInstanceService;
        this.snake = snakeFactory.createSnake();
        this.snakeControllerInputHandler = snakeControllerInputHandler;
        this.snakeControllerSnakeEventHandler = snakeControllerSnakeEventHandler;
        this.snakeControllerGameLoop = snakeControllerGameLoop;
        this.snakeControllerGameLoop.setOnGameLoopUpdateListener(new OnGameLoopUpdateListener() {
            @Override
            public void onGameLoopUpdateEvent() {
                onGameLoopUpdate();
            }
        });
    }

    @Override
    public void start() {
        //gameView.show();

        snakeControllerGameLoop.start();
    }

    @Override
    public void onWindowClosed() {
        //this will never be called
    }

    @Override
    public void onURLOpen() {
        //this will never be handled
    }

    @Override
    public void onKeyPressed(int keyCode) {
        //this will never be handled
    }

    @Override
    public void onKeyReleased(int keyCode) {
        //this will never be handled
    }

    @Override
    public void onGameLoopUpdate() {
        executeSnakeControllerCommands(snakeControllerInputHandler.handleInputs());

        snakeControllerInputHandler.reset();

        final AndroidSnakeInstanceImpl snakeInstance = snake.getSnakeInstance();

        if (snakeInstance != null) {
            snakeInstanceService.update(snakeInstance);

            final List<SnakeEvent> snakeEvents = snakeInstance.getSnakeEvents();

            executeSnakeControllerCommands(
                    snakeControllerSnakeEventHandler.handleEvents(snakeEvents));
        }

        this.getUpdateListener().ifPresent(OnGameLoopUpdateListener::onGameLoopUpdateEvent);
    }



    private void executeSnakeControllerCommands(final Collection<SnakeControllerCommand<AndroidSnakeImpl>> snakeControllerCommands) {
        if (CollectionUtils.isEmpty(snakeControllerCommands)) {
            return;
        }

        snakeControllerCommands.forEach(snakeControllerCommand -> snakeControllerCommand.execute(snake));
    }

    @Override
    public AndroidSnakeImpl getSnake() {
        return this.snake;
    }

    @Override
    public void stop() {
        snakeControllerGameLoop.stop();
    }
}
