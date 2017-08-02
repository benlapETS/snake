package com.etsmtl.ca.log530.snake.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Typeface;

import com.etsmtl.ca.log530.snake.R;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeImpl;
import com.etsmtl.ca.log530.snake.model.AndroidSnakeInstanceImpl;
import com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants;

import spypunk.snake.constants.SnakeConstants;
import spypunk.snake.model.Type;

import static com.etsmtl.ca.log530.snake.ui.constants.AndroidSnakeUIConstants.DEFAULT_FONT_COLOR;

/**
 * Created by gabar on 2017-08-01.
 */

public class AndroidSnakeInstanceHudView extends SurfaceView implements SurfaceHolder.Callback{
    private static final String EMPTY_STRING = "";
    private AndroidSnakeImpl snake;
    private Bitmap normalFoodImage, bonusFoodImage;
    private Type normalFood = Type.NORMAL, bonusFood = Type.BONUS;
    private String normalFoodCount = "", bonusFoodCount = "";
    private float fontSize;
    private int imageSize = AndroidSnakeUIConstants.CELL_SIZE;
    private float scoreFontSize;
    private RectF normalRectangle = new RectF(), bonusRectangle = new RectF();
    private Paint statPaint = new Paint(), scorePaint = new Paint();
    private Typeface statFont;
    private RectF statRectangle;

    public AndroidSnakeInstanceHudView(Context context, AttributeSet attrs){
        super(context, attrs);
        statFont = Typeface.createFromAsset(context.getAssets(),"fonts/neutronium.ttf");
        statPaint.setTypeface(statFont);
        statPaint.setColor(DEFAULT_FONT_COLOR);
        scorePaint.setTypeface(statFont);
        scorePaint.setColor(DEFAULT_FONT_COLOR);
        statRectangle = new RectF();
        init();
    }

    public void init(){
        Bitmap bmp;
        //we draw hud using default cell size
        for(Type foodType : Type.values()){
            switch(foodType){
                case NORMAL:
                    bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_normal);
                    normalFoodImage = ThumbnailUtils.extractThumbnail(bmp, imageSize, imageSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                    break;
                case BONUS:
                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.food_bonus);
                    bonusFoodImage = ThumbnailUtils.extractThumbnail(bmp, imageSize, imageSize, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                    break;
            }
        }
        statPaint.setTextSize(fontSize);
        scorePaint.setTextSize(scoreFontSize);
        //luckily our txt has same char width for all characters
        /*
                               ^
                            imageSize/2
                               |
                         _____________ _______________
                        |             |               |
                        |             |               |
         <imagesize/2 - |__ImageSize__|__maxTextSize__|
         */
        statRectangle.set(imageSize/2,imageSize/2,imageSize/2+imageSize+ statPaint.measureText(String.valueOf(SnakeConstants.WIDTH*SnakeConstants.HEIGHT)), imageSize/2+imageSize*2);
    }
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        imageSize = h/3;
        fontSize = h/3f;
        scoreFontSize = (h*2)/3f;
        //calculate max text score size
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        imageSize = measuredHeight/3;
        fontSize = measuredHeight/3f;
        scoreFontSize = (measuredHeight*2)/3f;

        setMeasuredDimension(measuredWidth, measuredHeight);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected  void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
        Canvas surfaceCanvas = this.getHolder().lockCanvas();
        renderView(surfaceCanvas, snake.getSnakeInstance());
        this.getHolder().unlockCanvasAndPost(surfaceCanvas);    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Canvas surfaceCanvas = this.getHolder().lockCanvas();
        renderView(surfaceCanvas, snake.getSnakeInstance());
        this.getHolder().unlockCanvasAndPost(surfaceCanvas);
    }

    private void renderView(Canvas canvas, AndroidSnakeInstanceImpl snakeInstance) {
        canvas.drawColor(Color.BLACK);
        renderFoodStats(canvas, snake.getSnakeInstance());
        renderScore(canvas);
    }

    private void renderFoodStats(Canvas surfaceCanvas, AndroidSnakeInstanceImpl snakeInstance) {
        //draw stats at 1/6 total height + 1/3 height
        surfaceCanvas.drawBitmap(normalFoodImage,imageSize/2, imageSize/2,statPaint);
        surfaceCanvas.drawText(normalFoodCount,imageSize/2,imageSize/2+imageSize,statPaint);
        surfaceCanvas.drawBitmap(bonusFoodImage,imageSize/2, imageSize/2+imageSize,statPaint);
        surfaceCanvas.drawText(bonusFoodCount,imageSize/2,imageSize/2+imageSize*2,statPaint);
    }

    private void renderScore(Canvas surfaceCanvas) {
        surfaceCanvas.drawText(snake.getSnakeInstance() == null ? EMPTY_STRING : String.valueOf(snake.getSnakeInstance().getScore()), (surfaceCanvas.getWidth()-statRectangle.width())/2+statRectangle.width(),scoreFontSize/2,scorePaint);
    }

    /**
     * Set the current snake
     * @param snake the snake graphically handled by this view
     */
    public void setSnake(final AndroidSnakeImpl snake){
        this.snake = snake;
    }

    public void update(){
        normalFoodCount = snake.getSnakeInstance() == null ? "0" : String.valueOf(snake.getSnakeInstance().getStatistics().get(normalFood));
        bonusFoodCount = snake.getSnakeInstance() == null ? "0" : String.valueOf(snake.getSnakeInstance().getStatistics().get(normalFood));
        postInvalidate();
    }
}
