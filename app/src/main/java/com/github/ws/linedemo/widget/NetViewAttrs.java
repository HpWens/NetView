package com.github.ws.linedemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.ws.linedemo.R;

/**
 * Created by Administrator on 3/16 0016.
 */
public class NetViewAttrs {
    private int netColor;
    private int textColor;
    private int overlayColor;
    private int overlayAlpha;
    private int textSize;
    private int lineWidth;


    public NetViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.netView, defStyleAttr, 0);
        netColor = ta.getColor(R.styleable.netView_netColor, context.getResources().getColor(R.color.colorBlue));
        textColor = ta.getColor(R.styleable.netView_textColor, context.getResources().getColor(R.color.colorRed));
        overlayColor = ta.getColor(R.styleable.netView_overlayColor, context.getResources().getColor(R.color.colorYellow));
        overlayAlpha = ta.getInteger(R.styleable.netView_overlayAlpha, 126);
        textSize = ta.getInteger(R.styleable.netView_textSize, 18);
        lineWidth = ta.getInteger(R.styleable.netView_lineWidth, 1);
        ta.recycle();
    }

    public int getNetColor() {
        return netColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getOverlayColor() {
        return overlayColor;
    }

    public int getOverlayAlpha() {
        return overlayAlpha;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getLineWidth() {
        return lineWidth;
    }
}
