package spypunk.snake.ui.controller;

import spypunk.snake.ui.controller.input.SnakeController;
import spypunk.snake.ui.view.SnakeGameView;

/**
 * A controller for awt snake game views.
 * Created by gabar on 2017-07-25.
 */

public abstract class AWTSnakeController implements SnakeController {
    public abstract SnakeGameView getSnakeView();
}
