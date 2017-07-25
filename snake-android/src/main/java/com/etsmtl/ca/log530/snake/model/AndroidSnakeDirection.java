package com.etsmtl.ca.log530.snake.model;

/**
 * Created by gabar on 2017-07-24.
 */

import spypunk.snake.model.Direction;
import android.graphics.Point;

public enum  AndroidSnakeDirection implements Direction<Point, AndroidSnakeDirection> {

    LEFT {
        @Override
        public Point applyPoint(final Point location) {
            return new Point(location.x - 1, location.y);
        }

        @Override
        public AndroidSnakeDirection applyDirection(final AndroidSnakeDirection direction) {
            if (AndroidSnakeDirection.RIGHT.equals(direction)) {
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
        public AndroidSnakeDirection applyDirection(final AndroidSnakeDirection direction) {
            if (AndroidSnakeDirection.LEFT.equals(direction)) {
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
        public AndroidSnakeDirection applyDirection(final AndroidSnakeDirection direction) {
            if (AndroidSnakeDirection.UP.equals(direction)) {
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
        public AndroidSnakeDirection applyDirection(final AndroidSnakeDirection direction) {
            if (AndroidSnakeDirection.DOWN.equals(direction)) {
                return this;
            }

            return super.applyDirection(direction);
        }
    };

    public abstract Point applyPoint(Point location);

    public AndroidSnakeDirection applyDirection(final AndroidSnakeDirection direction) {
        return direction;
    }
}
