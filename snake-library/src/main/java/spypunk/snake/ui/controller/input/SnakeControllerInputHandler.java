/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.controller.input;

import java.util.List;

import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.event.UIEvent;

public interface SnakeControllerInputHandler<S> {

    void onUIEvent(UIEvent event);

    enum InputType {
        MOVEMENT,
        PAUSE,
        NEW_GAME,
        MUTE,
        INCREASE_VOLUME,
        DECREASE_VOLUME
    }

    void onKeyPressed(int keyCode);

    void onKeyReleased(int keyCode);

    List<SnakeControllerCommand<S>> handleInputs();

    void reset();
}
