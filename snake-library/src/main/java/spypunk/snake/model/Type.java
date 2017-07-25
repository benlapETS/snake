package spypunk.snake.model;

/**
 * Created by gabar on 2017-07-20.
 */
public enum Type {
    NORMAL {
        @Override
        public int getPoints() {
            return 10;
        }
    },
    BONUS {
        @Override
        public int getPoints() {
            return 50;
        }
    };

    public abstract int getPoints();
}
