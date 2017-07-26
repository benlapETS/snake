package com.etsmtl.ca.log530.snake.ui.controller.event;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.ui.factory.AndroidControllerCommandFactory;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.model.SnakeEvent;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;

/**
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeControllerSnakeEventHandlerImpl extends AndroidSnakeControllerSnakeEventHandler {

    private final Map<SnakeEvent, Supplier<SnakeControllerCommand<AndroidSnakeImpl>>> snakeControllerCommands = Maps
            .newHashMap();

    @Inject
    public AndroidSnakeControllerSnakeEventHandlerImpl(final AndroidControllerCommandFactory controllerCommandFactory) {
        snakeControllerCommands.put(SnakeEvent.GAME_OVER,
                controllerCommandFactory::createGameOverSnakeControllerCommand);

        snakeControllerCommands.put(SnakeEvent.FOOD_EATEN,
                controllerCommandFactory::createFoodEatenSnakeControllerCommand);
    }

    @Override
    public List<SnakeControllerCommand<AndroidSnakeImpl>> handleEvents(final List<SnakeEvent> snakeEvents) {
        if (CollectionUtils.isEmpty(snakeEvents)) {
            return Collections.emptyList();
        }

        return snakeEvents.stream().map(snakeControllerCommands::get).map(Supplier::get).collect(Collectors.toList());
    }

}
