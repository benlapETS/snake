package com.etsmtl.ca.log530.snake.ui.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by gabar on 2017-07-24.
 * Taken from http://www.quadra-tec.net/~floppie/blag/2013/01/scalinglinearlayout-auto-scaling-layouts-in-android/
 */
public class ScalingLinearLayout extends LinearLayout {
    int baseWidth;
    int baseHeight;
    boolean alreadyScaled;
    float scale;
    int expectedWidth;
    int expectedHeight;

    public ScalingLinearLayout(Context context) {
        super(context);

        Log.d("notcloud.view", "ScalingLinearLayout: width=" + this.getWidth() + ", height=" + this.getHeight());
        this.alreadyScaled = false;
    }

    public ScalingLinearLayout(Context context, AttributeSet attributes) {
        super(context, attributes);

        Log.d("notcloud.view", "ScalingLinearLayout: width=" + this.getWidth() + ", height=" + this.getHeight());
        this.alreadyScaled = false;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        Log.d("notcloud.view", "ScalingLinearLayout::onFinishInflate: 1 width=" + this.getWidth() + ", height=" + this.getHeight());

        // Do an initial measurement of this layout with no major restrictions on size.
        // This will allow us to figure out what the original desired width and height are.
        this.measure(1000, 1000); // Adjust this up if necessary.
        this.baseWidth = this.getMeasuredWidth();
        this.baseHeight = this.getMeasuredHeight();
        Log.d("notcloud.view", "ScalingLinearLayout::onFinishInflate: 2 width=" + this.getWidth() + ", height=" + this.getHeight());

        Log.d("notcloud.view", "ScalingLinearLayout::onFinishInflate: alreadyScaled=" + this.alreadyScaled);
        Log.d("notcloud.view", "ScalingLinearLayout::onFinishInflate: scale=" + this.scale);
        if(this.alreadyScaled) {
            Scale.scaleViewAndChildren(this, this.scale, 0);
        }
    }

    public void draw(Canvas canvas) {
        // Get the current width and height.
        int width = this.getWidth();
        int height = this.getHeight();

        // Figure out if we need to scale the layout.
        // We may need to scale if:
        //    1. We haven't scaled it before.
        //    2. The width has changed.
        //    3. The height has changed.
        if(!this.alreadyScaled || width != this.expectedWidth || height != this.expectedHeight) {
            // Figure out the x-scaling.
            float xScale = (float)width / this.baseWidth;
            if(this.alreadyScaled && width != this.expectedWidth) {
                xScale = (float)width / this.expectedWidth;
            }
            // Figure out the y-scaling.
            float yScale = (float)height / this.baseHeight;
            if(this.alreadyScaled && height != this.expectedHeight) {
                yScale = (float)height / this.expectedHeight;
            }

            // Scale the layout.
            this.scale = Math.min(xScale, yScale);
            Log.d("notcloud.view", "ScalingLinearLayout::onLayout: Scaling!");
            Scale.scaleViewAndChildren(this, this.scale, 0);

            // Mark that we've already scaled this layout, and what
            // the width and height were when we did so.
            this.alreadyScaled = true;
            this.expectedWidth = width;
            this.expectedHeight = height;

            // Finally, return.
            return;
        }

        super.draw(canvas);
    }
}
