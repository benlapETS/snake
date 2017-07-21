package spypunk.snake.ui.cache;

import spypunk.snake.model.Type;
import spypunk.snake.ui.icon.Icon;
import spypunk.snake.ui.snakepart.SnakePart;

/**
 * Created by gabar on 2017-07-20.
 */

public interface ImageCache<T> {
    T getIcon(Icon icon);

    T getSnakeImage(SnakePart snakePart);

    T getFoodImage(Type foodType);
}
