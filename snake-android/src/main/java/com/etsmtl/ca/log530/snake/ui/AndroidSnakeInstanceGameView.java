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
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.etsmtl.ca.log530.snake.R;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeDirection;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeFood;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
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
import static com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants.DEFAULT_FONT_COLOR;

/**
 * Created by gabar on 2017-07-24.
 */

public class AndroidSnakeInstanceGameView extends SurfaceView implements SnakeGameView, ImageCache<Bitmap>, SurfaceHolder.Callback {

    private static final String PAUSE = "PAUSE";

    @ColorInt
    private static final int SNAKE_FROZEN_FG_COLOR = Color.argb(30, 30, 30, 200);

    private static final String GAME_OVER = "GAME OVER";
    private static final float TEXT_SIZE = 48f;
    private static final float BORDER_WIDTH = 2f;

    private final RectF gridRectangle;

    private final RectF frozenGridRectangle;

    private final Typeface frozenFont;

    private float x;

    private float y;

    private int cellSize = CELL_SIZE;


    private AndroidSnakeImpl snake;

    private final Map<Point, RectF> rectanglesCache = Maps.newHashMap();

    private final static Map<SnakePart, Bitmap> imageCache = Maps.newHashMap();

    private Paint gamePaint;

    private Paint strokePaint;

    private Bitmap normalFood, bonusFood;

    private State lastState;

    public AndroidSnakeInstanceGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

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
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        init();
        invalidate();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setWillNotDraw(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //what to do here?
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //what to do here?
    }
    /**
     * Set the current snake
     * @param snake the snake graphically handled by this view
     */
    public void setSnake(final AndroidSnakeImpl snake){
        this.snake = snake;
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
        //default to cellsize if w or h = 0, we can't do better
        cellSize = Math.min(h/SnakeConstants.HEIGHT,w/SnakeConstants.WIDTH);
        if(cellSize == 0){
            cellSize = CELL_SIZE;
        }
        float width = SnakeConstants.WIDTH * cellSize + 1;
        float height = SnakeConstants.HEIGHT * cellSize + 1;
        float wPadding = (w-width)/2;
        float hPadding = (h-width)/2;
        gridRectangle.set(wPadding, hPadding, wPadding+width,
                hPadding+height);

        frozenGridRectangle.set(wPadding, hPadding, wPadding+width,
                hPadding+height);
        x = gridRectangle.left + 1;
        y = gridRectangle.top + 1;
        init();
        invalidate();
    }

    @Override
    protected  void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
        Canvas surfaceCanvas = this.getHolder().lockCanvas();
        renderSnake(surfaceCanvas, snake.getSnakeInstance());
        this.getHolder().unlockCanvasAndPost(surfaceCanvas);    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Canvas surfaceCanvas = this.getHolder().lockCanvas();
        renderSnake(surfaceCanvas, snake.getSnakeInstance());
        this.getHolder().unlockCanvasAndPost(surfaceCanvas);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //nothing to really do
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        cellSize = Math.min(measuredHeight/SnakeConstants.HEIGHT,measuredWidth/SnakeConstants.WIDTH);
        if(cellSize == 0){
            //default to defined size
            cellSize = CELL_SIZE;
        }
        float width = SnakeConstants.WIDTH * cellSize + 1;
        float height = SnakeConstants.HEIGHT * cellSize + 1;
        float wPadding = (measuredWidth-width)/2;
        float hPadding = (measuredHeight-height)/2;
        gridRectangle.set(wPadding, hPadding, wPadding+width,
                hPadding+height);
        x = gridRectangle.left + 1;
        y = gridRectangle.top + 1;
        frozenGridRectangle.set(wPadding, hPadding, wPadding+width,
                hPadding+height);

        setMeasuredDimension(measuredWidth, measuredHeight);
        init();
    }

    @Override
    public void update() {
        //SwingUtils.doInGraphics(image, this::renderSnake);
        postInvalidate();
    }

    private void renderSnake(final Canvas graphics, AndroidSnakeInstanceImpl snakeInstance) {
        graphics.drawColor(Color.BLACK);
        strokePaint.setColor(DEFAULT_BORDER_COLOR);
        strokePaint.setStrokeWidth(BORDER_WIDTH);
        //draw border
        graphics.drawRect(gridRectangle,strokePaint);
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

        lastState = snakeInstance.getState();

        if (!State.RUNNING.equals(lastState)) {
            renderSnakeFrozen(graphics, lastState);
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

            rectangle = new RectF(x1, y1, x1+cellSize, y1+cellSize);

            rectanglesCache.put(location, rectangle);
        } else {
            rectangle = rectanglesCache.get(location);
        }

        return rectangle;
    }

    private void renderSnakeNew(final Canvas graphics) {
        gamePaint.setTextAlign(Paint.Align.CENTER);
        gamePaint.setTypeface(frozenFont);
        gamePaint.setTextSize(TEXT_SIZE);
        gamePaint.setColor(DEFAULT_FONT_COLOR);
        graphics.drawText(TAP_ANYWHERE, graphics.getWidth() / 2, (int) ((graphics.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)) , gamePaint);
        gamePaint.reset();
    }

    private void renderSnakeFrozen(final Canvas graphics, final State state) {
        gamePaint.setColor(SNAKE_FROZEN_FG_COLOR);
        graphics.drawRect(frozenGridRectangle, gamePaint);
        gamePaint.setTextAlign(Paint.Align.CENTER);
        gamePaint.setTypeface(frozenFont);
        gamePaint.setTextSize(TEXT_SIZE);
        gamePaint.setColor(DEFAULT_FONT_COLOR);

        graphics.drawText(State.GAME_OVER.equals(state) ? GAME_OVER : PAUSE, graphics.getWidth() / 2, (int) ((graphics.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)) , gamePaint);
        gamePaint.reset();
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
