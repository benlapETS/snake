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

import spypunk.snake.model.Snake;
import spypunk.snake.ui.cache.SnakeImageCache;
import spypunk.snake.ui.controller.input.SnakeController;
import spypunk.snake.ui.font.cache.SnakeFontCache;
import spypunk.snake.ui.view.SnakeGameView;
import spypunk.snake.ui.view.SnakeGameViewImpl;

@Singleton
public class SnakeViewFactoryImpl implements SnakeViewFactory {

    private final SnakeController snakeController;

    private final SnakeFontCache fontCache;

    private final SnakeImageCache snakeImageCache;

    @Inject
    public SnakeViewFactoryImpl(final SnakeController snakeController, final SnakeFontCache fontCache,
            final SnakeImageCache snakeImageCache) {
        this.snakeController = snakeController;
        this.fontCache = fontCache;
        this.snakeImageCache = snakeImageCache;
    }

    @Override
    public SnakeGameView createsnakeView(final Snake snake) {
        return new SnakeGameViewImpl(snakeController, fontCache, snakeImageCache, snake);
    }
}
