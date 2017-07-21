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
        public Point apply(final Point location) {
            return new Point(location.x - 1, location.y);
        }

        @Override
        public SnakeDirection apply(final SnakeDirection direction) {
            if (SnakeDirection.RIGHT.equals(direction)) {
                return this;
            }

            return super.apply(direction);
        }
    },
    RIGHT {
        @Override
        public Point apply(final Point location) {
            return new Point(location.x + 1, location.y);
        }

        @Override
        public SnakeDirection apply(final SnakeDirection direction) {
            if (SnakeDirection.LEFT.equals(direction)) {
                return this;
            }

            return super.apply(direction);
        }
    },
    DOWN {
        @Override
        public Point apply(final Point location) {
            return new Point(location.x, location.y + 1);
        }

        @Override
        public SnakeDirection apply(final SnakeDirection direction) {
            if (SnakeDirection.UP.equals(direction)) {
                return this;
            }

            return super.apply(direction);
        }
    },
    UP {
        @Override
        public Point apply(final Point location) {
            return new Point(location.x, location.y - 1);
        }

        @Override
        public SnakeDirection apply(final SnakeDirection direction) {
            if (SnakeDirection.DOWN.equals(direction)) {
                return this;
            }

            return super.apply(direction);
        }
    };

    public abstract Point apply(Point location);

    public SnakeDirection apply(final SnakeDirection direction) {
        return direction;
    }
}
