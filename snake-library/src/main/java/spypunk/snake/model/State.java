package spypunk.snake.model;

/**
 * Created by gabar on 2017-07-20.
 */
public enum State {
    RUNNING {
        @Override
        public State onPause() {
            return PAUSED;
        }
    },
    PAUSED {
        @Override
        public State onPause() {
            return RUNNING;
        }
    },
    GAME_OVER;

    public State onPause() {
        return this;
    }
}
