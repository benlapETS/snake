package spypunk.snake.sound;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Format<R>{
    R getFileReader();
    String getName();
}
