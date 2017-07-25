package spypunk.snake.sound;

import javax.sound.sampled.spi.AudioFileReader;

/**
 * Created by gabar on 2017-07-20.
 */

public interface Format<R>{
    R getFileReader();
    String getName();
}
