package spypunk.snake.sound;

import javax.sound.sampled.spi.AudioFileReader;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**
 * Created by gabar on 2017-07-20.
 */
public enum SnakeFormat implements Format<AudioFileReader>{
    MP3 {
        @Override
        public AudioFileReader getFileReader() {
            return new MpegAudioFileReader();
        }
        @Override
        public String getName(){
            return this.name();
        }
    }
}
