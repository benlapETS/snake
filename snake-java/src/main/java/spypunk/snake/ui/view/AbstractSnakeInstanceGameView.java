/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.view;

import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import spypunk.snake.model.SnakeImpl;

public abstract class AbstractSnakeInstanceGameView extends JLabel implements GameView {

    private static final long serialVersionUID = -3254277544172916051L;

    protected transient BufferedImage image;

    protected SnakeImpl snake;
}
