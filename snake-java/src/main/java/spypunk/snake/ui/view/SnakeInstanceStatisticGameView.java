/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.ui.view;

import static spypunk.snake.ui.constants.SnakeUIConstants.CELL_SIZE;
import static spypunk.snake.ui.constants.SnakeUIConstants.DEFAULT_FONT_COLOR;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import spypunk.snake.model.SnakeDirection;
import spypunk.snake.model.SnakeImpl;
import spypunk.snake.model.SnakeInstance;
import spypunk.snake.model.Type;
import spypunk.snake.ui.cache.SnakeImageCache;
import spypunk.snake.ui.font.FontType;
import spypunk.snake.ui.font.cache.SnakeFontCache;
import spypunk.snake.ui.util.SwingUtils;

public class SnakeInstanceStatisticGameView extends AbstractSnakeInstanceGameView {

    private static final long serialVersionUID = 3093168306699870331L;

    private final Rectangle scoreRectangle = new Rectangle(CELL_SIZE, 0, 4 * CELL_SIZE, CELL_SIZE);

    private final Rectangle foodRectangle = new Rectangle(0, 0, CELL_SIZE, CELL_SIZE);

    private final Font defaultFont;

    private final SnakeImpl snake;

    private final Type foodType;

    private final Image foodImage;

    public SnakeInstanceStatisticGameView(final SnakeFontCache fontCache, final SnakeImageCache snakeImageCache,
                                          final SnakeImpl snake, final Type foodType) {
        this.snake = snake;
        this.foodType = foodType;
        foodImage = snakeImageCache.getFoodImage(foodType);

        defaultFont = fontCache.getFont(FontType.DEFAULT);

        image = new BufferedImage(foodRectangle.width + scoreRectangle.width,
                CELL_SIZE,
                BufferedImage.TYPE_INT_ARGB);

        setBackground(Color.BLUE);
        setIcon(new ImageIcon(image));
        setIgnoreRepaint(true);
    }

    @Override
    public void update() {
        SwingUtils.doInGraphics(image, this::renderStatistic);
        repaint();
    }

    private void renderStatistic(final Graphics2D graphics) {
        final SnakeInstance<Point, SnakeDirection> snakeInstance = snake.getSnakeInstance();

        final String count = snakeInstance == null ? "0" : String.valueOf(snakeInstance.getStatistics().get(foodType));

        SwingUtils.renderCenteredText(graphics, count, scoreRectangle, defaultFont, DEFAULT_FONT_COLOR);

        SwingUtils.drawImage(graphics, foodImage, foodRectangle);
    }
}
