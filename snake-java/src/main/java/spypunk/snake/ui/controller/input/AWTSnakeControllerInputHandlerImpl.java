/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.controller.input;

import java.awt.event.KeyEvent;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.collections4.ListUtils;

import com.google.common.collect.Maps;

import spypunk.snake.model.SnakeDirection;
import spypunk.snake.model.SnakeImpl;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.event.UIEvent;
import spypunk.snake.ui.factory.SnakeControllerCommandFactory;

@Singleton
public class AWTSnakeControllerInputHandlerImpl extends AWTSnakeControllerInputHandler {

    private final BitSet pressedKeysBitSet = new BitSet();

    private final BitSet releasedKeysBitSet = new BitSet();

    private final Map<Integer, Supplier<SnakeControllerCommand<SnakeImpl>>> pressedKeyCodesHandlers = Maps.newHashMap();

    private final Map<Integer, Supplier<SnakeControllerCommand<SnakeImpl>>> releasedKeyCodesHandlers = Maps.newHashMap();

    @Inject
    public AWTSnakeControllerInputHandlerImpl(final SnakeControllerCommandFactory controllerCommandFactory) {
        pressedKeyCodesHandlers.put(KeyEvent.VK_LEFT,
            () -> controllerCommandFactory.createDirectionSnakeControllerCommand(SnakeDirection.LEFT));

        pressedKeyCodesHandlers.put(KeyEvent.VK_RIGHT,
            () -> controllerCommandFactory.createDirectionSnakeControllerCommand(SnakeDirection.RIGHT));

        pressedKeyCodesHandlers.put(KeyEvent.VK_DOWN,
            () -> controllerCommandFactory.createDirectionSnakeControllerCommand(SnakeDirection.DOWN));

        releasedKeyCodesHandlers.put(KeyEvent.VK_SPACE,
            controllerCommandFactory::createNewGameSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_P, controllerCommandFactory::createPauseSnakeControllerCommand);

        pressedKeyCodesHandlers.put(KeyEvent.VK_UP,
            () -> controllerCommandFactory.createDirectionSnakeControllerCommand(SnakeDirection.UP));

        releasedKeyCodesHandlers.put(KeyEvent.VK_M, controllerCommandFactory::createMuteSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_UP,
            controllerCommandFactory::createIncreaseVolumeSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_DOWN,
            controllerCommandFactory::createDecreaseVolumeSnakeControllerCommand);
    }

    @Override
    public void onUIEvent(UIEvent event) {
        //we respond only to keyevents
    }

    @Override
    public void onKeyPressed(final int keyCode) {
        pressedKeysBitSet.set(keyCode);
    }

    @Override
    public void onKeyReleased(final int keyCode) {
        releasedKeysBitSet.set(keyCode);
    }

    @Override
    public List<SnakeControllerCommand<SnakeImpl>> handleInputs() {
        return ListUtils.union(getCommandsFromKeys(pressedKeysBitSet, pressedKeyCodesHandlers),
            getCommandsFromKeys(releasedKeysBitSet, releasedKeyCodesHandlers));
    }

    @Override
    public void reset() {
        pressedKeysBitSet.clear();
        releasedKeysBitSet.clear();
    }

    private List<SnakeControllerCommand<SnakeImpl>> getCommandsFromKeys(final BitSet bitSet,
            final Map<Integer, Supplier<SnakeControllerCommand<SnakeImpl>>> keyCodesHandlers) {

        if (bitSet.isEmpty()) {
            return Collections.emptyList();
        }

        return keyCodesHandlers.keySet().stream().filter(keyCode -> isKeyTriggered(keyCode, bitSet))
                .map(keyCode -> getCommandFromKeyCode(keyCodesHandlers, keyCode)).collect(Collectors.toList());
    }

    private SnakeControllerCommand<SnakeImpl> getCommandFromKeyCode(
            final Map<Integer, Supplier<SnakeControllerCommand<SnakeImpl>>> keyCodesHandlers, final Integer keyCode) {
        return keyCodesHandlers.get(keyCode).get();
    }

    private boolean isKeyTriggered(final int keyCode, final BitSet bitSet) {
        return bitSet.get(keyCode);
    }
}
