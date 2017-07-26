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
import spypunk.snake.service.AWTSnakeInstanceServiceImpl;
import spypunk.snake.sound.cache.SoundClipCache;
import spypunk.snake.sound.cache.SoundClipCacheImpl;
import spypunk.snake.sound.service.SoundService;
import spypunk.snake.sound.service.SoundServiceImpl;
import spypunk.snake.ui.cache.SnakeImageCache;
import spypunk.snake.ui.cache.SnakeImageCacheImpl;
import spypunk.snake.ui.controller.input.SnakeController;
import spypunk.snake.ui.controller.AWTSnakeControllerImpl;
import spypunk.snake.ui.controller.event.SnakeControllerSnakeEventHandler;
import spypunk.snake.ui.controller.event.AWTSnakeControllerSnakeEventHandlerImpl;
import spypunk.snake.ui.controller.input.SnakeControllerInputHandler;
import spypunk.snake.ui.controller.input.AWTSnakeControllerInputHandlerImpl;
import spypunk.snake.ui.factory.ControllerCommandFactory;
import spypunk.snake.ui.factory.SnakeControllerCommandFactoryImpl;
import spypunk.snake.ui.factory.SnakeViewFactory;
import spypunk.snake.ui.factory.SnakeViewFactoryImpl;
import spypunk.snake.ui.font.cache.SnakeFontCache;
import spypunk.snake.ui.font.cache.SnakeFontCacheImpl;

public class SnakeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SnakeInstanceService.class).to(AWTSnakeInstanceServiceImpl.class);
        bind(SnakeController.class).to(AWTSnakeControllerImpl.class);
        bind(SnakeViewFactory.class).to(SnakeViewFactoryImpl.class);
        bind(SnakeImageCache.class).to(SnakeImageCacheImpl.class);
        bind(SnakeFactory.class).to(SnakeFactoryImpl.class);
        bind(SnakeFontCache.class).to(SnakeFontCacheImpl.class);
        bind(ControllerCommandFactory.class).to(SnakeControllerCommandFactoryImpl.class);
        bind(SoundService.class).to(SoundServiceImpl.class);
        bind(SoundClipCache.class).to(SoundClipCacheImpl.class);
        bind(SnakeControllerInputHandler.class).to(AWTSnakeControllerInputHandlerImpl.class);
        bind(SnakeControllerSnakeEventHandler.class).to(AWTSnakeControllerSnakeEventHandlerImpl.class);
        bind(SnakeControllerGameLoop.class).to(SnakeControllerGameLoopImpl.class);
    }
}
