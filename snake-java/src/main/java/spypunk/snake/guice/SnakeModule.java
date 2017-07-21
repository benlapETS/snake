/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.guice;

import com.google.inject.AbstractModule;

import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.controller.gameloop.SnakeControllerGameLoopImpl;
import spypunk.snake.factory.SnakeFactory;
import spypunk.snake.factory.SnakeFactoryImpl;
import spypunk.snake.service.SnakeInstanceService;
import spypunk.snake.service.SnakeInstanceServiceImpl;
import spypunk.snake.sound.cache.SoundClipCache;
import spypunk.snake.sound.cache.SoundClipCacheImpl;
import spypunk.snake.sound.service.SoundService;
import spypunk.snake.sound.service.SoundServiceImpl;
import spypunk.snake.ui.cache.SnakeImageCache;
import spypunk.snake.ui.cache.SnakeImageCacheImpl;
import spypunk.snake.ui.controller.SnakeController;
import spypunk.snake.ui.controller.SnakeControllerImpl;
import spypunk.snake.ui.controller.event.SnakeControllerSnakeEventHandler;
import spypunk.snake.ui.controller.event.SnakeControllerSnakeEventHandlerImpl;
import spypunk.snake.ui.controller.input.SnakeControllerInputHandler;
import spypunk.snake.ui.controller.input.SnakeControllerInputHandlerImpl;
import spypunk.snake.ui.factory.SnakeControllerCommandFactory;
import spypunk.snake.ui.factory.SnakeControllerCommandFactoryImpl;
import spypunk.snake.ui.factory.SnakeViewFactory;
import spypunk.snake.ui.factory.SnakeViewFactoryImpl;
import spypunk.snake.ui.font.cache.SnakeFontCache;
import spypunk.snake.ui.font.cache.SnakeFontCacheImpl;

public class SnakeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SnakeInstanceService.class).to(SnakeInstanceServiceImpl.class);
        bind(SnakeController.class).to(SnakeControllerImpl.class);
        bind(SnakeViewFactory.class).to(SnakeViewFactoryImpl.class);
        bind(SnakeImageCache.class).to(SnakeImageCacheImpl.class);
        bind(SnakeFactory.class).to(SnakeFactoryImpl.class);
        bind(SnakeFontCache.class).to(SnakeFontCacheImpl.class);
        bind(SnakeControllerCommandFactory.class).to(SnakeControllerCommandFactoryImpl.class);
        bind(SoundService.class).to(SoundServiceImpl.class);
        bind(SoundClipCache.class).to(SoundClipCacheImpl.class);
        bind(SnakeControllerInputHandler.class).to(SnakeControllerInputHandlerImpl.class);
        bind(SnakeControllerSnakeEventHandler.class).to(SnakeControllerSnakeEventHandlerImpl.class);
        bind(SnakeControllerGameLoop.class).to(SnakeControllerGameLoopImpl.class);
    }
}
