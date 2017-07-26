/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.factory;

import spypunk.snake.model.Direction;
import spypunk.snake.model.Snake;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;

public interface ControllerCommandFactory<D extends Direction, S extends Snake> {

    SnakeControllerCommand<S> createNewGameSnakeControllerCommand();

    SnakeControllerCommand<S> createPauseSnakeControllerCommand();

    SnakeControllerCommand<S> createDirectionSnakeControllerCommand(D direction);

    SnakeControllerCommand<S> createMuteSnakeControllerCommand();

    SnakeControllerCommand<S> createIncreaseVolumeSnakeControllerCommand();

    SnakeControllerCommand<S> createDecreaseVolumeSnakeControllerCommand();

    SnakeControllerCommand<S> createGameOverSnakeControllerCommand();

    SnakeControllerCommand<S> createFoodEatenSnakeControllerCommand();
}
