/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.snake.model;

import java.awt.Point;

public enum SnakeDirection implements Direction<Point, SnakeDirection>{

    LEFT {
        @Override
        public Point applyPoint(final Point location) {
            return new Point(location.x - 1, location.y);
        }

        @Override
        public SnakeDirection applyDirection(final SnakeDirection direction) {
            if (SnakeDirection.RIGHT.equals(direction)) {
                return this;
            }

            return super.applyDirection(direction);
        }
    },
    RIGHT {
        @Override
        public Point applyPoint(final Point location) {
            return new Point(location.x + 1, location.y);
        }

        @Override
        public SnakeDirection applyDirection(final SnakeDirection direction) {
            if (SnakeDirection.LEFT.equals(direction)) {
                return this;
            }

            return super.applyDirection(direction);
        }
    },
    DOWN {
        @Override
        public Point applyPoint(final Point location) {
            return new Point(location.x, location.y + 1);
        }

        @Override
        public SnakeDirection applyDirection(final SnakeDirection direction) {
            if (SnakeDirection.UP.equals(direction)) {
                return this;
            }

            return super.applyDirection(direction);
        }
    },
    UP {
        @Override
        public Point applyPoint(final Point location) {
            return new Point(location.x, location.y - 1);
        }

        @Override
        public SnakeDirection applyDirection(final SnakeDirection direction) {
            if (SnakeDirection.DOWN.equals(direction)) {
                return this;
            }

            return super.applyDirection(direction);
        }
    };

    public abstract Point applyPoint(Point location);

    public SnakeDirection applyDirection(final SnakeDirection direction) {
        return direction;
    }
}
