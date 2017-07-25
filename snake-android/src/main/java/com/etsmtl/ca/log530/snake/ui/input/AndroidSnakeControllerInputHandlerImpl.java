package com.etsmtl.ca.log530.snake.ui.input;

import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import spypunk.snake.ui.event.UIEvent;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.ListUtils;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.factory.SnakeControllerCommandFactory;

/**
 * This class handles snake game inputs. It primarily processes touch events, and no guarantee is given that it will handle key events.
 *
 * Created by gabar on 2017-07-24.
 */

@Singleton
public class AndroidSnakeControllerInputHandlerImpl extends AndroidSnakeControllerInputHandler {

    private final BitSet pressedKeysBitSet = new BitSet();

    private final BitSet releasedKeysBitSet = new BitSet();

    private final Map<UIEvent, Supplier<SnakeControllerCommand>> pressedKeyCodesHandlers = Maps.newHashMap();

    private final Map<UIEvent, Supplier<SnakeControllerCommand>> releasedKeyCodesHandlers = Maps.newHashMap();

    @Inject
    public AndroidSnakeControllerInputHandlerImpl(final SnakeControllerCommandFactory snakeControllerCommandFactory) {
        pressedKeyCodesHandlers.put(UIEvent.LEFT,
                () -> snakeControllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.LEFT));

        pressedKeyCodesHandlers.put(UIEvent.RIGHT,
                () -> snakeControllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.RIGHT));

        pressedKeyCodesHandlers.put(UIEvent.DOWN,
                () -> snakeControllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.DOWN));
        //TODO handle pause and newgame elsewhere
        /*releasedKeyCodesHandlers.put(KeyEvent.VK_SPACE,
                snakeControllerCommandFactory::createNewGameSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_P, snakeControllerCommandFactory::createPauseSnakeControllerCommand);*/

        pressedKeyCodesHandlers.put(UIEvent.UP,
                () -> snakeControllerCommandFactory.createDirectionSnakeControllerCommand(AndroidSnakeDirection.UP));

        /*releasedKeyCodesHandlers.put(KeyEvent.VK_M, snakeControllerCommandFactory::createMuteSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_UP,
                snakeControllerCommandFactory::createIncreaseVolumeSnakeControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_DOWN,
                snakeControllerCommandFactory::createDecreaseVolumeSnakeControllerCommand);*/
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
    public void onUIEvent(UIEvent event) {
        releasedKeysBitSet.set(event.ordinal());
    }

    @Override
    public List<SnakeControllerCommand> handleInputs() {
        return ListUtils.union(getCommandsFromKeys(pressedKeysBitSet, pressedKeyCodesHandlers),
                getCommandsFromKeys(releasedKeysBitSet, releasedKeyCodesHandlers));
    }

    @Override
    public void reset() {
        pressedKeysBitSet.clear();
        releasedKeysBitSet.clear();
    }

    private List<SnakeControllerCommand> getCommandsFromKeys(final BitSet bitSet,
                                                             final Map<UIEvent, Supplier<SnakeControllerCommand>> keyCodesHandlers) {

        if (bitSet.isEmpty()) {
            return Collections.emptyList();
        }

        return keyCodesHandlers.keySet().stream().filter(keyCode -> isKeyTriggered(keyCode, bitSet))
                .map(keyCode -> getCommandFromKeyCode(keyCodesHandlers, keyCode)).collect(Collectors.toList());
    }

    private SnakeControllerCommand getCommandFromKeyCode(
            final Map<UIEvent, Supplier<SnakeControllerCommand>> keyCodesHandlers, final UIEvent keyCode) {
        return keyCodesHandlers.get(keyCode).get();
    }

    private boolean isKeyTriggered(final UIEvent keyCode, final BitSet bitSet) {
        return bitSet.get(keyCode.ordinal());
    }

}
