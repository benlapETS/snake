/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.sound;

public enum SoundImpl implements Sound {

    BACKGROUND(SnakeFormat.MP3, true),
    GAME_OVER(SnakeFormat.MP3, true),
    FOOD_EATEN(SnakeFormat.MP3, false);

    private final String fileName;

    private final Format format;

    private final boolean loop;

    SoundImpl(final Format format, final boolean loop) {
        fileName = name().toLowerCase() + "." + format.getName().toLowerCase();
        this.format = format;
        this.loop = loop;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public boolean isLoop() {
        return loop;
    }
}
