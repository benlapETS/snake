/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.service;

import spypunk.snake.model.SnakeDirection;
import spypunk.snake.model.Snake;
import spypunk.snake.model.SnakeInstanceImpl;

public interface SnakeInstanceService {

    void create(Snake snake);

    void update(SnakeInstanceImpl snakeInstance);

    void updateDirection(SnakeInstanceImpl snakeInstance, SnakeDirection direction);

    void pause(SnakeInstanceImpl snakeInstance);
}
