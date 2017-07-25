package com.etsmtl.ca.log530.snake.sound;

import android.media.MediaPlayer;

import spypunk.snake.sound.Format;


/**
 * Created by gabar on 2017-07-24.
 */

public enum AndroidSnakeFormat implements Format<MediaPlayer>{
    MP3 {
        @Override
        public MediaPlayer getFileReader() {
            return new MediaPlayer();
        }
        @Override
        public String getName(){
            return this.name();
        }
    }
}