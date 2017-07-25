package spypunk.snake.sound;

/**
 * Created by gabar on 2017-07-24.
 */

public interface Sound {
    String getFileName();

    Format getFormat();

    boolean isLoop();
}
