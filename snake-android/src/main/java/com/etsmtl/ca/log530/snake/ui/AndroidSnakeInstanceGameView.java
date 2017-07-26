package com.etsmtl.ca.log530.snake.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.etsmtl.ca.log530.snake.R;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeFood;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
import com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.State;
import spypunk.snake.model.Type;
import spypunk.snake.ui.cache.ImageCache;
import spypunk.snake.ui.icon.Icon;
import spypunk.snake.ui.snakepart.SnakePart;
import spypunk.snake.ui.view.SnakeGameView;

import static com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants.*;
import static com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants.DEFAULT_BORDER_COLOR;
import static com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants.TAP_ANYWHERE;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakeInstanceGameView extends ViewGroup implements SnakeGameView, ImageCache<Bitmap> {

    private static final String PAUSE = "PAUSE";

    @ColorInt
    private static final int SNAKE_FROZEN_FG_COLOR = Color.argb(30, 30, 30, 200);

    private static final String GAME_OVER = "GAME OVER";

    private final RectF gridRectangle;

    private final RectF frozenGridRectangle;

    private final Typeface frozenFont;

    private final float x;

    private final float y;

    private int cellSize = CELL_SIZE;


    private AndroidSnakeImpl snake;

    private final Map<Point, RectF> rectanglesCache = Maps.newHashMap();

    private final static Map<SnakePart, Bitmap> imageCache = Maps.newHashMap();

    private AndroidSnakeInstanceImpl snakeInstance;

    private Paint gamePaint;

    private Bitmap normalFood, bonusFood;

    private State lastState;

    public AndroidSnakeInstanceGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        frozenFont = Typeface.createFromAsset(context.getAssets(),"fonts/neutronium.ttf");

        //this was used to set game bounds originally, unnecessary here
        gridRectangle = new RectF(0, 0, SnakeConstants.WIDTH * cellSize + 1,
                SnakeConstants.HEIGHT * cellSize + 1);

        frozenGridRectangle = new RectF(gridRectangle.left + 1, gridRectangle.top + 1, gridRectangle.width() - 1,
                gridRectangle.height() - 1);

        x = gridRectangle.left + 1;
        y = gridRectangle.top + 1;
        //image = Bitmap.createBitmap(gridRectangle.width() + 1,gridRectangle.height() + 1, conf);
        //this originally set the JLabel view to the specified size
        //setIcon(new ImageIcon(image));

        gamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        init();
        invalidate();
    }

    /**
     * Set the current snake
     * @param snake the snake graphically handled by this view
     */
    public void setSnake(final AndroidSnakeImpl snake){
        this.snake = snake;
        snakeInstance = this.snake.getSnakeInstance();
    }

    private void init(){
        //cache all images
        Bitmap bmp;
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_bottom_left);
        imageCache.put(SnakePart.BOTTOM_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_bottom_right);
        imageCache.put(SnakePart.BOTTOM_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_head_bottom);
        imageCache.put(SnakePart.HEAD_BOTTOM, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_head_left);
        imageCache.put(SnakePart.HEAD_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_head_right);
        imageCache.put(SnakePart.HEAD_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_head_top);
        imageCache.put(SnakePart.HEAD_TOP, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_horizontal);
        imageCache.put(SnakePart.HORIZONTAL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_tail);
        imageCache.put(SnakePart.TAIL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_top_left);
        imageCache.put(SnakePart.TOP_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_top_right);
        imageCache.put(SnakePart.TOP_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.snake_vertical);
        imageCache.put(SnakePart.VERTICAL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));

        for(Type foodType : Type.values()){
            switch(foodType){
                case NORMAL:
                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.food_normal);
                    normalFood = ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                    break;
                case BONUS:
                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.food_bonus);
                    bonusFood = ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                    break;
            }
        }

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        //fill largest of width or height, since constants could be changed
        //default to cellsize if w or h = 0
        if(w==0 || h == 0){
            cellSize = CELL_SIZE;
        }
        cellSize = Math.min(h/SnakeConstants.HEIGHT,w/SnakeConstants.WIDTH);
        init();
        invalidate();
    }

    @Override
    protected  void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
        renderSnake(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        renderSnake(canvas);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //nothing to really do
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        cellSize = Math.min(height/SnakeConstants.HEIGHT,width/SnakeConstants.WIDTH);

        setMeasuredDimension(width, height);
    }

    @Override
    public void update() {
        //SwingUtils.doInGraphics(image, this::renderSnake);
        postInvalidate();
    }

    private void renderSnake(final Canvas graphics) {
        gamePaint.setColor(DEFAULT_BORDER_COLOR);
        //draw border
        graphics.drawRect(gridRectangle,gamePaint);

        if (snakeInstance == null) {
            //if we have time we can make a dialog
            renderSnakeNew(graphics);
            return;
        }

        final List<Point> snakeParts = snakeInstance.getSnakeParts();
        //we could avoid instantiating list by using a simple boolean for first element
        for (int i = 0; i < snakeParts.size(); ++i) {
            final SnakePart snakePart = getSnakePart(snakeInstance, i);
            renderSnakePart(graphics, snakeParts.get(i), snakePart);
        }

        renderFood(graphics, snakeInstance);

        final State state = snakeInstance.getState();

        if (!State.RUNNING.equals(state)) {
            lastState = state;
            renderSnakeFrozen(graphics, state);
        }else if(State.PAUSED.equals(lastState)){
            graphics.restore();
        }else if(lastState == null || State.GAME_OVER.equals(lastState)){
            //clear view after restarting a game or starting a new one
            graphics.drawColor(Color.BLACK);
        }
    }

    private SnakePart getSnakePart(final AndroidSnakeInstanceImpl snakeInstance, final int i) {
        final AndroidSnakeDirection snakeDirection = snakeInstance.getSnakeDirection();

        if (i == 0) {
            return getSnakeHeadPart(snakeDirection);
        }

        final List<Point> snakeParts = snakeInstance.getSnakeParts();
        final Point nextSnakePartLocation = i < snakeParts.size() - 1 ? snakeParts.get(i + 1) : null;

        if (nextSnakePartLocation == null) {
            return SnakePart.TAIL;
        }

        final Point snakePartLocation = snakeParts.get(i);
        final Point previousSnakePartLocation = snakeParts.get(i - 1);

        if (previousSnakePartLocation.x == nextSnakePartLocation.x) {
            return SnakePart.VERTICAL;
        }

        if (previousSnakePartLocation.y == nextSnakePartLocation.y) {
            return SnakePart.HORIZONTAL;
        }

        return getSnakeCornerPart(snakePartLocation, nextSnakePartLocation, previousSnakePartLocation);
    }

    private SnakePart getSnakeCornerPart(final Point snakePartLocation, final Point nextSnakePartLocation,
                                         final Point previousSnakePartLocation) {
        final boolean previousXLesser = previousSnakePartLocation.x < snakePartLocation.x;
        final boolean nextYLesser = nextSnakePartLocation.y < snakePartLocation.y;
        final boolean nextXLesser = nextSnakePartLocation.x < snakePartLocation.x;
        final boolean previousYLesser = previousSnakePartLocation.y < snakePartLocation.y;
        final boolean nextYGreater = nextSnakePartLocation.y > snakePartLocation.y;
        final boolean previousYGreater = previousSnakePartLocation.y > snakePartLocation.y;
        final boolean nextXGreater = nextSnakePartLocation.x > snakePartLocation.x;
        final boolean previousXGreater = previousSnakePartLocation.x > snakePartLocation.x;

        if (previousXLesser && nextYLesser
                || nextXLesser && previousYLesser) {
            return SnakePart.BOTTOM_RIGHT;
        }

        if (previousXLesser && nextYGreater
                || nextXLesser && previousYGreater) {
            return SnakePart.TOP_RIGHT;
        }

        if (previousYLesser && nextXGreater
                || nextYLesser && previousXGreater) {
            return SnakePart.BOTTOM_LEFT;
        }

        return SnakePart.TOP_LEFT;
    }

    private SnakePart getSnakeHeadPart(final AndroidSnakeDirection snakeDirection) {
        switch (snakeDirection) {
            case DOWN:
                return SnakePart.HEAD_BOTTOM;

            case UP:
                return SnakePart.HEAD_TOP;

            case LEFT:
                return SnakePart.HEAD_LEFT;

            default:
                return SnakePart.HEAD_RIGHT;
        }
    }

    private void renderFood(final Canvas graphics, final AndroidSnakeInstanceImpl snakeInstance) {
        final AndroidSnakeFood food = snakeInstance.getFood();
        graphics.drawBitmap(getFoodImage(food.getType()),null, getRectangle(food.getLocation()),gamePaint);
    }

    private void renderSnakePart(final Canvas graphics, final Point location, final SnakePart snakePart) {
        graphics.drawBitmap(getSnakeImage(snakePart),null,getRectangle(location),gamePaint);
    }

    private RectF getRectangle(final Point location) {
        RectF rectangle;

        if (!rectanglesCache.containsKey(location)) {
            final float x1 = x + location.x * cellSize;
            final float y1 = y + location.y * cellSize;

            rectangle = new RectF(x1, y1, cellSize, cellSize);

            rectanglesCache.put(location, rectangle);
        } else {
            rectangle = rectanglesCache.get(location);
        }

        return rectangle;
    }

    private void renderSnakeNew(final Canvas graphics) {
        gamePaint.setTextAlign(Paint.Align.CENTER);
        gamePaint.setTypeface(frozenFont);
        graphics.drawText(TAP_ANYWHERE, graphics.getWidth() / 2, (int) ((graphics.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)) , gamePaint);
    }

    private void renderSnakeFrozen(final Canvas graphics, final State state) {
        graphics.save();
        gamePaint.setColor(SNAKE_FROZEN_FG_COLOR);
        graphics.drawRect(frozenGridRectangle, gamePaint);

        graphics.drawText(State.GAME_OVER.equals(state) ? GAME_OVER : PAUSE, graphics.getWidth() / 2, (int) ((graphics.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)) , gamePaint);
    }
    //this is not used
    @Override
    public Bitmap getIcon(Icon icon) {
        return null;
    }

    @Override
    public Bitmap getSnakeImage(SnakePart snakePart) {
        //is this cached anyway? should we cache it here?
        return imageCache.get(snakePart);
    }

    @Override
    public Bitmap getFoodImage(Type foodType) {
        switch(foodType){
            case NORMAL:
                return normalFood;
            case BONUS:
                return bonusFood;
        }
        //default to normal when missing case statements
        return normalFood;
    }

    @Override
    public void show() {
        if(this.getVisibility() != View.VISIBLE){
            this.setVisibility(View.VISIBLE);
            invalidate();
        }
    }

    @Override
    public void setMute(boolean mute) {
        //this should not handle sound stuff
    }
}
