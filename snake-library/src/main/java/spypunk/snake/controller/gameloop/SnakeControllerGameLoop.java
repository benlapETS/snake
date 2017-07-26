/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.controller.gameloop;

import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameLoopUpdateListener;

public interface SnakeControllerGameLoop {

    void setOnGameLoopUpdateListener(OnGameLoopUpdateListener listener);

    void start();

    void stop();

}
