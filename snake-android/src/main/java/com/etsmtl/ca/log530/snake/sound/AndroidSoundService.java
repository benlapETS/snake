package com.etsmtl.ca.log530.snake.sound;

import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

import javax.inject.Singleton;

/**
 * A service for playing sounds outside an application or activity context
 * Created by gabar on 2017-08-02.
 */
@Singleton
public class AndroidSoundService {

    private MediaPlayer backgroundMediaPlayer, gameOverMediaPlayer, foodEatenMediaPlayer;

    public  void onFoodEaten(){
        if(foodEatenMediaPlayer.isPlaying()){
            foodEatenMediaPlayer.stop();
            try {
                foodEatenMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        foodEatenMediaPlayer.start();
    }

    public  void onGameOver(){
        if(gameOverMediaPlayer.isPlaying()){
            gameOverMediaPlayer.stop();
            try {
                gameOverMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gameOverMediaPlayer.start();
    }

    public  void onGameUnpaused(){
        backgroundMediaPlayer.start();

    }

    public  void onGamePaused(){
        backgroundMediaPlayer.pause();
    }

    public void setBackgroundMediaPlayer(MediaPlayer backgroundMediaPlayer) {
        this.backgroundMediaPlayer = backgroundMediaPlayer;
        this.backgroundMediaPlayer.setLooping(true);
        this.backgroundMediaPlayer.setOnPreparedListener(mediaPlayer -> mediaPlayer.start());
    }

    public void setGameOverMediaPlayer(MediaPlayer gameOverMediaPlayer) {
        this.gameOverMediaPlayer = gameOverMediaPlayer;
        this.gameOverMediaPlayer.setLooping(false);
    }

    public void setFoodEatenMediaPlayer(MediaPlayer foodEatenMediaPlayer) {
        this.foodEatenMediaPlayer = foodEatenMediaPlayer;
        this.foodEatenMediaPlayer.setLooping(false);
    }
}
