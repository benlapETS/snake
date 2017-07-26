/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.controller;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.collections4.CollectionUtils;

import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.factory.AWTSnakeFactory;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.SnakeImpl;
import spypunk.snake.model.SnakeInstanceImpl;
import spypunk.snake.service.AWTSnakeInstanceService;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.controller.event.AWTSnakeControllerSnakeEventHandler;
import spypunk.snake.ui.controller.input.AWTSnakeControllerInputHandler;
import spypunk.snake.ui.factory.SnakeViewFactory;
import spypunk.snake.ui.util.SwingUtils;
import spypunk.snake.ui.view.SnakeGameView;

@Singleton
public class AWTSnakeControllerImpl extends AWTSnakeController {

    private final AWTSnakeInstanceService snakeInstanceService;

    private final SnakeGameView snakeView;

    private final SnakeImpl snake;

    private final SnakeControllerGameLoop snakeControllerGameLoop;

    private final AWTSnakeControllerInputHandler snakeControllerInputHandler;

    private final AWTSnakeControllerSnakeEventHandler snakeControllerSnakeEventHandler;

    @Inject
    public AWTSnakeControllerImpl(final AWTSnakeFactory snakeFactory, final SnakeViewFactory snakeViewFactory,
                                  final SnakeControllerGameLoop snakeControllerGameLoop, final AWTSnakeInstanceService snakeInstanceService,
                                  final AWTSnakeControllerInputHandler snakeControllerInputHandler,
                                  final AWTSnakeControllerSnakeEventHandler snakeControllerSnakeEventHandler) {
        // TODO Is this why he inject all those, because he needed here ?
        this.snakeInstanceService = snakeInstanceService;
        this.snakeControllerInputHandler = snakeControllerInputHandler;
        this.snakeControllerSnakeEventHandler = snakeControllerSnakeEventHandler;
        this.snakeControllerGameLoop = snakeControllerGameLoop;
        // TODO Why aren't they in the constructor parameters too ?
        snake = snakeFactory.createSnake();
        snakeView = snakeViewFactory.createSnakeView(snake);
    }

    @Override
    public void start() {
        snakeView.show();

        snakeControllerGameLoop.start();
    }

    @Override
    public void onWindowClosed() {
        snakeControllerGameLoop.stop();
    }

    @Override
    public void onURLOpen() {
        SwingUtils.openURI(snake.getProjectURI());
    }

    @Override
    public void onGameLoopUpdate() {
        executeSnakeControllerCommands(snakeControllerInputHandler.handleInputs());

        snakeControllerInputHandler.reset();

        final SnakeInstanceImpl snakeInstance = snake.getSnakeInstance();

        if (snakeInstance != null) {
            snakeInstanceService.update(snakeInstance);

            final List<SnakeEvent> snakeEvents = snakeInstance.getSnakeEvents();

            executeSnakeControllerCommands(
                snakeControllerSnakeEventHandler.handleEvents(snakeEvents));
        }

        snakeView.update();
    }

    @Override
    public void onKeyPressed(final int keyCode) {
        snakeControllerInputHandler.onKeyPressed(keyCode);
    }

    @Override
    public void onKeyReleased(final int keyCode) {
        snakeControllerInputHandler.onKeyReleased(keyCode);
    }

    @Override
    public SnakeGameView getSnakeView() {
        return snakeView;
    }

    private void executeSnakeControllerCommands(final Collection<SnakeControllerCommand<SnakeImpl>> snakeControllerCommands) {
        if (CollectionUtils.isEmpty(snakeControllerCommands)) {
            return;
        }

        snakeControllerCommands.forEach(snakeControllerCommand -> snakeControllerCommand.execute(snake));
    }
}
