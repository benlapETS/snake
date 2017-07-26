package com.etsmtl.ca.log530.snake;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.etsmtl.ca.log530.snake.factory.AndroidSnakeFactory;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
import com.etsmtl.ca.log530.snake.service.AndroidSnakeInstanceService;
import com.etsmtl.ca.log530.snake.ui.AndroidSnakeInstanceGameView;
import com.etsmtl.ca.log530.snake.ui.controller.AndroidSnakeController;
import com.etsmtl.ca.log530.snake.ui.controller.event.AndroidSnakeControllerSnakeEventHandler;
import com.etsmtl.ca.log530.snake.ui.controller.listener.OnGameLoopUpdateListener;
import com.etsmtl.ca.log530.snake.ui.input.AndroidSnakeControllerInputHandler;
import com.etsmtl.ca.log530.snake.ui.input.listener.OnSwipeListener;
import com.etsmtl.ca.log530.snake.ui.input.listener.SwipeDirection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import spypunk.snake.controller.gameloop.SnakeControllerGameLoop;
import spypunk.snake.model.SnakeEvent;
import spypunk.snake.model.State;
import spypunk.snake.ui.controller.command.SnakeControllerCommand;
import spypunk.snake.ui.controller.input.SnakeController;
import spypunk.snake.ui.event.UIEvent;
/**
 * A full screen activity with swipe events and game control logic.
 */
public class SnakeGameActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = () -> {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    };
    private AndroidSnakeInstanceGameView gameView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            gameView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = this::hide;
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = (view, motionEvent) -> {
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS);
        }
        return false;
    };

    private GestureDetector gestureDetector;

    @Inject
    AndroidSnakeControllerInputHandler snakeControllerInputHandler;

    @Inject
    AndroidSnakeController snakeController;

    @Inject
    AndroidSnakeInstanceService snakeInstanceService;

    AndroidSnakeInstanceImpl snakeInstance;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        ((SnakeApplication)getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);



        mVisible = true;
        gameView = $(R.id.game_view);

        //TODO simplify this
        mContentView = gameView;
        button = findViewById(R.id.button_continue);
        button.setText(this.getResources().getString(R.string.button_start_text));
        //TODO : move this to class?
        gestureDetector = new GestureDetector(getApplicationContext(), new OnSwipeListener() {

            /**Handles swipe events
             *
             * @param direction the direction
             * @return whether the event was handled
             */
            @Override
            public boolean onSwipe(SwipeDirection direction){
                switch(direction){
                    case up:
                        snakeControllerInputHandler.onUIEvent(UIEvent.UP);
                        break;
                    case down:
                        snakeControllerInputHandler.onUIEvent(UIEvent.DOWN);
                        break;
                    case left:
                        snakeControllerInputHandler.onUIEvent(UIEvent.LEFT);
                        break;
                    case right:
                        snakeControllerInputHandler.onUIEvent(UIEvent.RIGHT);
                        break;
                    default:
                        //no event was handled
                        return false;
                }
                //event was handled
                return true;
            }
        });

        gameView.setSnake(snakeController.getSnake());


        //this is where game loop updates sync up to the view
        snakeController.setOnGameLoopUpdateListener(new OnGameLoopUpdateListener() {
            @Override
            public void onGameLoopUpdateEvent() {
                gameView.update();
            }
        });
        button.setOnClickListener(l -> {
            AndroidSnakeInstanceImpl instance = snakeController.getSnake().getSnakeInstance();
            if (instance != null) {
                switch(snakeController.getSnake().getSnakeInstance().getState()){
                    case RUNNING:
                        //button shouldn't be visible during running
                        break;
                    case PAUSED:
                        //unpause
                        snakeControllerInputHandler.onUIEvent(UIEvent.PAUSE_TRIGGER);
                        button.setVisibility(View.GONE);
                        break;
                    case GAME_OVER:
                        snakeControllerInputHandler.onUIEvent(UIEvent.NEW_GAME);
                        button.setVisibility(View.GONE);
                        break;
                }
            }else{
                button.setVisibility(View.GONE);

                snakeControllerInputHandler.onUIEvent(UIEvent.NEW_GAME);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //trigger pause event
        //TODO check if multiple pause/stops break it
        snakeControllerInputHandler.onUIEvent(UIEvent.PAUSE_TRIGGER);
    }

    @Override
    public void onStop(){
        super.onStop();
        //trigger pause event
        snakeControllerInputHandler.onUIEvent(UIEvent.PAUSE_TRIGGER);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        gameView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    //----- Utilities -----
    /**
     * Convenience method to cast views.
     * from https://stackoverflow.com/a/22806734
     * @param id the view id
     * @param <T> the targeted type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }
    //----- Gesture handling -----
    @Override // ------------------------------ Catch the Gesture Event by Overriding onTouch() method: -->
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


}
