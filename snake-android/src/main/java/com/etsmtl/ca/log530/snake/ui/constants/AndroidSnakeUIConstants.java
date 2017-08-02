package com.etsmtl.ca.log530.snake.ui.constants;


import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by gabar on 2017-07-24.
 */

public final class AndroidSnakeUIConstants {

    public static final int CELL_SIZE = 20;
    @ColorInt
    public static final int DEFAULT_FONT_COLOR = Color.LTGRAY;
    @ColorInt
    public static final int DEFAULT_BORDER_COLOR = Color.GRAY;
    //TODO : move to string resources
    public static String TAP_ANYWHERE = "Tap anywhere to start";

    public static final String ZERO = "0";

    private AndroidSnakeUIConstants() {
        throw new IllegalAccessError();
    }
}

