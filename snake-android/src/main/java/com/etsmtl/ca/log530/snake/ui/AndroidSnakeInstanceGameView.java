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
    private final static Map<SnakePart, Bitmap> imageCache = Maps.newHashMap();
    private static final String EMPTY_STRING = "";
    private static final int FONT_SIZE = 48;
    private final RectF gameRectangle;
    private final RectF frozenGridRectangle;
    private final Typeface frozenFont;
    private final Map<Point, RectF> rectanglesCache = Maps.newHashMap();
    private float x;
    private float y;
    private int cellSize = CELL_SIZE;
    private int fontSize;
    private int imageSize = AndroidSnakeUIConstants.CELL_SIZE;
    private float scoreFontSize;
    private AndroidSnakeImpl snake;
    private Paint gamePaint, statPaint = new Paint(Paint.ANTI_ALIAS_FLAG), scorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static int MIN_PADDING = 20;
    private Typeface statFont;
    private RectF statRectangle;

    private Paint strokePaint;

    private Bitmap normalFood, bonusFood, normalFoodImage, bonusFoodImage;

    private State lastState;
    private int hudHeight;

    private String normalFoodCount = "", bonusFoodCount = "";

    public AndroidSnakeInstanceGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        frozenFont = Typeface.createFromAsset(context.getAssets(), "fonts/neutronium.ttf");
        fontSize = FONT_SIZE;
        statPaint.setTextSize(fontSize);
        hudHeight = (int)(-4*statPaint.ascent()+ 4*statPaint.descent());
        imageSize = hudHeight / 3;
        if(imageSize <=0){
            imageSize = CELL_SIZE;
        }
        scoreFontSize = FONT_SIZE;
        scorePaint.setTextSize(scoreFontSize);
        cellSize = Math.min((SnakeConstants.HEIGHT-hudHeight) / SnakeConstants.HEIGHT, SnakeConstants.WIDTH / SnakeConstants.WIDTH);
        //this was used to set game bounds originally, unnecessary here
        gameRectangle = new RectF(0, 0, SnakeConstants.WIDTH * cellSize + 1,
                SnakeConstants.HEIGHT * cellSize + 1);

        frozenGridRectangle = new RectF(gameRectangle.left + 1, gameRectangle.top + 1, gameRectangle.width() - 1,
                gameRectangle.height() - 1);

        statRectangle = new RectF(imageSize/2+gameRectangle.left,imageSize/2,imageSize/2+imageSize+ statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2);

        x = gameRectangle.left + 1;
        y = gameRectangle.top + 1;
        //image = Bitmap.createBitmap(gameRectangle.width() + 1,gameRectangle.height() + 1, conf);
        //this originally set the JLabel view to the specified size
        //setIcon(new ImageIcon(image));

        gamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        statFont = Typeface.createFromAsset(context.getAssets(), "fonts/neutronium.ttf");
        statPaint.setTypeface(statFont);
        statPaint.setColor(DEFAULT_FONT_COLOR);
        scorePaint.setTypeface(statFont);
        scorePaint.setColor(DEFAULT_FONT_COLOR);
        statRectangle = new RectF();
        init();
        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
     *
     * @param snake the snake graphically handled by this view
     */
    public void setSnake(final AndroidSnakeImpl snake) {
        this.snake = snake;
    }

    /**
     * Called once all dimensions have been updated to calculate all remaining assets and draw objects dependent on these values
     */
    private void init() {
        //cache all images
        Bitmap bmp;
        //cache image if they will be drawn aka cellSize > 0
        if(cellSize > 0) {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_bottom_left);
            imageCache.put(SnakePart.BOTTOM_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_bottom_right);
            imageCache.put(SnakePart.BOTTOM_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_bottom);
            imageCache.put(SnakePart.HEAD_BOTTOM, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_left);
            imageCache.put(SnakePart.HEAD_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_right);
            imageCache.put(SnakePart.HEAD_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_top);
            imageCache.put(SnakePart.HEAD_TOP, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_horizontal);
            imageCache.put(SnakePart.HORIZONTAL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_tail);
            imageCache.put(SnakePart.TAIL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_top_left);
            imageCache.put(SnakePart.TOP_LEFT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_top_right);
            imageCache.put(SnakePart.TOP_RIGHT, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.snake_vertical);
            imageCache.put(SnakePart.VERTICAL, ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
            //the in-game images
            for (Type foodType : Type.values()) {
                switch (foodType) {
                    case NORMAL:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_normal);
                        normalFood = ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                        break;
                    case BONUS:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_bonus);
                        bonusFood = ThumbnailUtils.extractThumbnail(bmp, cellSize, cellSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                        break;
                }
            }
        }

        //the hud images
        if(imageSize >0) {
            for (Type foodType : Type.values()) {
                switch (foodType) {
                    case NORMAL:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_normal);
                        normalFoodImage = ThumbnailUtils.extractThumbnail(bmp, imageSize, imageSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                        break;
                    case BONUS:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_bonus);
                        bonusFoodImage = ThumbnailUtils.extractThumbnail(bmp, imageSize, imageSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                        break;
                }
            }
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        //fill largest of width or height, since constants could be changed
        //default to cellsize if w or h = 0, we can't do better
        fontSize = FONT_SIZE;
        statPaint.setTextSize(fontSize);
        hudHeight = (int)(-4*statPaint.ascent()+ 4*statPaint.descent());
        imageSize = hudHeight / 3;
        if(imageSize <=0){
            imageSize = CELL_SIZE;
        }

        scoreFontSize = FONT_SIZE;
        scorePaint.setTextSize(scoreFontSize);
        statRectangle.set(imageSize/2,imageSize/2,imageSize/2+imageSize+ statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2);
        cellSize = Math.min((h-hudHeight-MIN_PADDING) / SnakeConstants.HEIGHT, (w-MIN_PADDING) / SnakeConstants.WIDTH);
        if (cellSize == 0) {
            cellSize = CELL_SIZE;
        }
        float width = SnakeConstants.WIDTH * cellSize + 1;
        float height = SnakeConstants.HEIGHT * cellSize + 1;
        float wPadding = (w - width) / 2;
        float hPadding = (h - width) / 2;
        gameRectangle.set(wPadding, hPadding+hudHeight, wPadding + width,
                hPadding +height + hudHeight);

        frozenGridRectangle.set(wPadding, hPadding+hudHeight, wPadding + width,
                hPadding + height + hudHeight);
        statRectangle.set(imageSize/2+gameRectangle.left,imageSize/2,imageSize/2+imageSize + MIN_PADDING+ gameRectangle.left+ statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2+MIN_PADDING);

        x = gameRectangle.left + 1;
        y = gameRectangle.top + 1;
        init();
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Canvas surfaceCanvas = this.getHolder().lockCanvas();
        renderSnake(surfaceCanvas, snake.getSnakeInstance());
        this.getHolder().unlockCanvasAndPost(surfaceCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        fontSize = FONT_SIZE;
        statPaint.setTextSize(fontSize);
        hudHeight = (int)(-4*statPaint.ascent()+ 4*statPaint.descent())+MIN_PADDING;
        imageSize = hudHeight / 3;

        if(imageSize <=0){
            imageSize = CELL_SIZE;
        }

        scoreFontSize = FONT_SIZE;
        scorePaint.setTextSize(scoreFontSize);

        statRectangle.set(imageSize/2,imageSize/2,imageSize/2+imageSize+ MIN_PADDING+statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2+MIN_PADDING);
        cellSize = Math.min((measuredHeight-hudHeight-MIN_PADDING) / SnakeConstants.HEIGHT, (measuredWidth-MIN_PADDING) / SnakeConstants.WIDTH);
        if (cellSize == 0) {
            //default to defined size
            cellSize = CELL_SIZE;
        }
        float width = SnakeConstants.WIDTH * cellSize + 1;
        float height = SnakeConstants.HEIGHT * cellSize + 1;
        float wPadding = (measuredWidth - width) / 2;
        float hPadding = (measuredHeight - height-hudHeight) / 2;
        gameRectangle.set(wPadding, hPadding+hudHeight, wPadding + width,
                hPadding +height + hudHeight);
        statRectangle.set(imageSize/2+gameRectangle.left,imageSize/2,imageSize/2+imageSize+gameRectangle.left+ statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2);

        x = gameRectangle.left + 1;
        y = gameRectangle.top + 1;
        frozenGridRectangle.set(wPadding, hPadding+hudHeight, wPadding + width,
                hPadding +height + hudHeight);

        setMeasuredDimension(measuredWidth, measuredHeight);
        init();
    }

    @Override
    public void update() {
        //SwingUtils.doInGraphics(image, this::renderSnake);
        normalFoodCount = snake.getSnakeInstance() == null ? "0" : String.valueOf(snake.getSnakeInstance().getStatistics().get(Type.NORMAL));
        bonusFoodCount = snake.getSnakeInstance() == null ? "0" : String.valueOf(snake.getSnakeInstance().getStatistics().get(Type.BONUS));
        postInvalidate();
    }

    private void renderSnake(final Canvas canvas, AndroidSnakeInstanceImpl snakeInstance) {
        canvas.drawColor(Color.BLACK);
        renderFoodStats(canvas,snakeInstance);
        renderScore(canvas);
        strokePaint.setColor(DEFAULT_BORDER_COLOR);
        strokePaint.setStrokeWidth(BORDER_WIDTH);
        //draw border
        canvas.drawRect(gameRectangle, strokePaint);
        if (snakeInstance == null) {
            //if we have time we can make a dialog
            renderSnakeNew(canvas);
            return;
        }
        //we could avoid instantiating list by using a simple boolean for first element
        synchronized (snakeInstance.getSnakeParts()) {
            for (int i = 0; i < snakeInstance.getSnakeParts().size(); ++i) {
                renderSnakePart(canvas, snakeInstance.getSnakeParts().get(i), getSnakePart(snakeInstance, i));
            }
        }
        renderFood(canvas, snakeInstance);

        lastState = snakeInstance.getState();

        if (!State.RUNNING.equals(lastState)) {
            renderSnakeFrozen(canvas, lastState);
        }
    }

    private SnakePart getSnakePart(final AndroidSnakeInstanceImpl snakeInstance, final int i) {

        if (i == 0) {
            return getSnakeHeadPart(snakeInstance.getSnakeDirection());
        }
        //TODO redo this to avoid as many instantiations as possible
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

    private void renderFood(final Canvas canvas, final AndroidSnakeInstanceImpl snakeInstance) {
        final AndroidSnakeFood food = snakeInstance.getFood();
        canvas.drawBitmap(getFoodImage(food.getType()), null, getRectangle(food.getLocation()), gamePaint);
    }

    private void renderSnakePart(final Canvas canvas, final Point location, final SnakePart snakePart) {
        if (location != null) {
            canvas.drawBitmap(getSnakeImage(snakePart), null, getRectangle(location), gamePaint);
        }
    }

    private void renderFoodStats(Canvas surfaceCanvas, AndroidSnakeInstanceImpl snakeInstance) {
        //draw with 20 padding between elements
        surfaceCanvas.drawBitmap(normalFoodImage, statRectangle.left, statRectangle.centerY()/2-imageSize / 2, statPaint);
        surfaceCanvas.drawText(normalFoodCount, statRectangle.left + imageSize+MIN_PADDING, statRectangle.centerY()/2+imageSize/4, statPaint);
        surfaceCanvas.drawBitmap(bonusFoodImage, statRectangle.left, statRectangle.centerY()/2+imageSize / 2+MIN_PADDING, statPaint);
        surfaceCanvas.drawText(bonusFoodCount, statRectangle.left + imageSize+MIN_PADDING, statRectangle.centerY()*3/2-imageSize/4+MIN_PADDING, statPaint);
    }

    private void renderScore(Canvas surfaceCanvas) {
        surfaceCanvas.drawText(snake.getSnakeInstance() == null ? EMPTY_STRING : String.valueOf(snake.getSnakeInstance().getScore()), (surfaceCanvas.getWidth() - statRectangle.width()) / 2 + statRectangle.width(), statRectangle.centerY(), scorePaint);
    }

    private RectF getRectangle(final Point location) {

        if (!rectanglesCache.containsKey(location)) {
            rectanglesCache.put(location, new RectF(x + location.x * cellSize, y + location.y * cellSize, x + location.x * cellSize + cellSize, y + location.y * cellSize + cellSize));
        }
        return rectanglesCache.get(location);
    }

    private void renderSnakeNew(final Canvas canvas) {
        gamePaint.setTextAlign(Paint.Align.CENTER);
        gamePaint.setTypeface(frozenFont);
        gamePaint.setTextSize(TEXT_SIZE);
        gamePaint.setColor(DEFAULT_FONT_COLOR);
        canvas.drawText(TAP_ANYWHERE, canvas.getWidth() / 2, (int) ((canvas.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)), gamePaint);
        gamePaint.reset();
    }

    private void renderSnakeFrozen(final Canvas canvas, final State state) {
        gamePaint.setColor(SNAKE_FROZEN_FG_COLOR);
        canvas.drawRect(frozenGridRectangle, gamePaint);
        gamePaint.setTextAlign(Paint.Align.CENTER);
        gamePaint.setTypeface(frozenFont);
        gamePaint.setTextSize(TEXT_SIZE);
        gamePaint.setColor(DEFAULT_FONT_COLOR);

        canvas.drawText(State.GAME_OVER.equals(state) ? GAME_OVER : PAUSE, canvas.getWidth() / 2, (int) ((canvas.getHeight() / 2) - ((gamePaint.descent() + gamePaint.ascent()) / 2)), gamePaint);
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
        switch (foodType) {
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
        if (this.getVisibility() != View.VISIBLE) {
            this.setVisibility(View.VISIBLE);
            invalidate();
        }
    }

    @Override
    public void setMute(boolean mute) {
        //this should not handle sound stuff
    }
}
