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


    public NetViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.netView, defStyleAttr, 0);
        netColor=ta.getColor(R.styleable.netView_netColor, Color.parseColor("#3F51B5"));
    }

}
