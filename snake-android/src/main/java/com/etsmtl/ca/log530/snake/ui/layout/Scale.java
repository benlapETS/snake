package com.etsmtl.ca.log530.snake.ui.layout;

/**
 * Created by gabar on 2017-07-24.
 * Taken from http://www.quadra-tec.net/~floppie/blag/2013/01/scalinglinearlayout-auto-scaling-layouts-in-android/
 */

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class Scale {
    public static void scaleContents(View rootView, View container) {
        Scale.scaleContents(rootView, container, rootView.getWidth(), rootView.getHeight());
    }

    // Scales the contents of the given view so that it completely fills the given
    // container on one axis (that is, we're scaling isotropically).
    public static void scaleContents(View rootView, View container, int width, int height) {
        Log.d("notcloud.scale", "Scale::scaleContents: container: " + container.getWidth() + "x" + container.getHeight() + ".");

        // Compute the scaling ratio
        float xScale = (float)container.getWidth() / width;
        float yScale = (float)container.getHeight() / height;
        float scale = Math.min(xScale, yScale);

        // Scale our contents
        Log.d("notcloud.scale", "Scale::scaleContents: scale=" + scale + ", width=" + width + ", height=" + height + ".");
        scaleViewAndChildren(rootView, scale, 0);
    }

    // Scale the given View, its contents, and all of its children by the given factor.
    public static void scaleViewAndChildren(View root, float scale, int canary) {
        // Retrieve the view's layout information
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();

        // Scale the GameView itself
        if(layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.width *= scale;
        }
        if(layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height *= scale;
        }

        // If the View has margins, scale those too
        if(layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)layoutParams;
            marginParams.leftMargin *= scale;
            marginParams.topMargin *= scale;
            marginParams.rightMargin *= scale;
            marginParams.bottomMargin *= scale;
        }
        root.setLayoutParams(layoutParams);

        // Same treatment for padding
        root.setPadding(
                (int)(root.getPaddingLeft() * scale),
                (int)(root.getPaddingTop() * scale),
                (int)(root.getPaddingRight() * scale),
                (int)(root.getPaddingBottom() * scale)
        );

        // If it's a TextView, scale the font size
		/*
		if(root instanceof TextView) {
			TextView tv = (TextView)root;
			tv.setTextSize(tv.getTextSize() * scale); //< We do NOT want to do this.
		}
		*/

        // If it's a ViewGroup, recurse!
        if(root instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)root;
            for(int i = 0; i < vg.getChildCount(); i++) {
                scaleViewAndChildren(vg.getChildAt(i), scale, canary + 1);
            }
        }
    }
}