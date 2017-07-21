package spypunk.snake.ui.font.cache;

import spypunk.snake.ui.font.FontType;

/**
 * Created by gabar on 2017-07-20.
 */

public interface FontCache<T> {
   T getFont(FontType fontType);
}
