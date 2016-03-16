package com.github.ws.linedemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 3/16 0016.
 */
public class Line extends View {

    private Paint linePaint;

    private float radius;  //外接圆半径

    private float centerX, centerY;

    public Line(Context context) {
        super(context);
        init(context);
    }


    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#f00000"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = widthSpecSize = getDefaultWidth();
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getDefaultWidth(), heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, getDefaultWidth());
        }

        Log.e("-------------", "----------------onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();//在线程中更新界面

        Log.e("-------------", "----------------onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    int currentRadius = 60;

    private double[] data = {1, 0.30, 0.6, 0.5, 0.8, 0.2};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path mPath = new Path();
        mPath.reset();
        for (int j = 1; j < 7; j++) {
            currentRadius = j*60;
            for (int i = 0; i < 7; i++) {
                if (i == 0) {
                    mPath.moveTo(centerX + currentRadius, centerY);
                } else {
                    float x = (float) (centerX + currentRadius * Math.cos(Math.toRadians(60 * i)));
                    float y = (float) (centerY + currentRadius * Math.sin(Math.toRadians(60 * i)));
                    mPath.lineTo(x, y);
                }
            }
        }

        mPath.close();
        canvas.drawPath(mPath, linePaint);

        //绘制轴线
        for (int i = 1; i < 7; i++) {
            mPath.reset();
            mPath.moveTo(centerX, centerY);
            float x = (float) (centerX + 360 * Math.cos(Math.toRadians(60 * i)));
            float y = (float) (centerY + 360 * Math.sin(Math.toRadians(60 * i)));
            mPath.lineTo(x, y);
            canvas.drawPath(mPath, linePaint);
        }



        Path path = new Path();
        linePaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 6; i++) {
            float x = (float) (centerX + 360 * Math.cos(Math.toRadians(60 * i))* data[i]);
            float y = (float) (centerY + 360 * Math.sin(Math.toRadians(60 * i)) * data[i]);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 5, linePaint);
        }
        path.close();
        linePaint.setAlpha(80);
        canvas.drawPath(path, linePaint);

        Log.e("-------------", "----------------onDraw");
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }
}
