/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.sound;

public enum Sound {

    BACKGROUND(SnakeFormat.MP3, true),
    GAME_OVER(SnakeFormat.MP3, true),
    FOOD_EATEN(SnakeFormat.MP3, false);

    private final String fileName;

    private final SnakeFormat format;

    private final boolean loop;

    Sound(final SnakeFormat format, final boolean loop) {
        fileName = name().toLowerCase() + "." + format.name().toLowerCase();
        this.format = format;
        this.loop = loop;
    }

    public String getFileName() {
        return fileName;
    }

    public SnakeFormat getFormat() {
        return format;
    }

    public boolean isLoop() {
        return loop;
    }
}
