/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.font.cache;

import java.awt.Font;

import spypunk.snake.ui.font.FontType;

@FunctionalInterface
public interface SnakeFontCache extends FontCache<Font>{

    Font getFont(FontType fontType);
}
