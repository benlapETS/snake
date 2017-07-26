package com.etsmtl.ca.log530.snake.ui.input;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import spypunk.snake.ui.event.UIEvent;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.ui.factory.AndroidControllerCommandFactory;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.ListUtils;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.ui.controller.command.SnakeControllerCommand;

/**
 * This class handles snake game inputs. It primarily processes touch events, and no guarantee is given that it will handle key events.
 *
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeControllerInputHandlerImpl extends AndroidSnakeControllerInputHandler {

    private final Set<UIEvent> uiEventBitSet = new HashSet<>();

    private final Map<UIEvent, Supplier<SnakeControllerCommand<AndroidSnakeImpl>>> pressedKeyCodesHandlers = Maps.newHashMap();

    @Inject
    public AndroidSnakeControllerInputHandlerImpl(final AndroidControllerCommandFactory controllerCommandFactory) {
        pressedKeyCodesHandlers.put(UIEvent.LEFT,
                () -> controllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.LEFT));

        pressedKeyCodesHandlers.put(UIEvent.RIGHT,
                () -> controllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.RIGHT));

        pressedKeyCodesHandlers.put(UIEvent.DOWN,
                () -> controllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.DOWN));
        //TODO handle pause and newgame elsewhere
        pressedKeyCodesHandlers.put(UIEvent.NEW_GAME,
                controllerCommandFactory::createNewGameSnakeControllerCommand);

        pressedKeyCodesHandlers.put(UIEvent.PAUSE_TRIGGER, controllerCommandFactory::createPauseSnakeControllerCommand);
        pressedKeyCodesHandlers.put(UIEvent.UP,
                () -> controllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.UP));

        /*releasedKeyCodesHandlers.put(KeyEvent.VK_M, controllerCommandFactory::createMuteSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_UP,
                controllerCommandFactory::createIncreaseVolumeSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_DOWN,
                controllerCommandFactory::createDecreaseVolumeSnakeControllerCommand);*/
    }

    @Override
    public void onKeyPressed(final int keyCode) {
    }

    @Override
    public void onKeyReleased(final int keyCode) {

    }


    @Override
    public void onUIEvent(UIEvent event) {
        uiEventBitSet.add(event);
    }

    @Override
    public List<SnakeControllerCommand<AndroidSnakeImpl>> handleInputs() {
        return getCommandsFromKeys(uiEventBitSet, pressedKeyCodesHandlers);
    }

    @Override
    public void reset() {
        uiEventBitSet.clear();
    }

    private List<SnakeControllerCommand<AndroidSnakeImpl>> getCommandsFromKeys(final Set<UIEvent> uiEventSet,
                                                             final Map<UIEvent, Supplier<SnakeControllerCommand<AndroidSnakeImpl>>> uiEventHandlers) {

        if (uiEventSet.isEmpty()) {
            return Collections.emptyList();
        }

        return uiEventHandlers.keySet().stream().filter(keyCode -> isKeyTriggered(keyCode, uiEventSet))
                .map(keyCode -> getCommandFromKeyCode(uiEventHandlers, keyCode)).collect(Collectors.toList());
    }

    private SnakeControllerCommand<AndroidSnakeImpl> getCommandFromKeyCode(
            final Map<UIEvent, Supplier<SnakeControllerCommand<AndroidSnakeImpl>>> keyCodesHandlers, final UIEvent keyCode) {
        return keyCodesHandlers.get(keyCode).get();
    }

    private boolean isKeyTriggered(final UIEvent keyCode, final Set<UIEvent> uiEventSet) {
        return uiEventSet.contains(keyCode);
    }

}
