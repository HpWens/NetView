package com.github.ws.linedemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 3/16 0016.
 */
public class NetView extends View {

    private String[] textArr = new String[]{"攻击", "防御", "爆发力", "抗性", "输出力", "群攻", "单挑", "生存能力", "生存能力"};
    private float[] overlayPercentArr = new float[]{0.5f, 0.8f, 0.3f, 0.3f, 0.9f, 0.6f, 0.7f, 1.0f, 0.1f};

    private Paint netPaint;
    private Paint textPaint;
    private Paint overPaint;

    private int centerPointX;
    private int centerPointY;

    private float maxRadius;  //最大圆半径
    private int overlayAlpha = 126;  // 0 ~255
    private int arrCount;

    private int netColor = Color.parseColor("#3F51B5");
    private int textColor = Color.parseColor("#FF4081");
    private int overlayColor = Color.parseColor("#FFFF00");

    public float netLineWidth = 1f;

    private final static float RADIUS_SCALE = 0.7f;

    public NetView(Context context) {
        super(context);
        init(context);
    }

    public NetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        arrCount = Math.min(textArr.length, overlayPercentArr.length);

        netPaint = new Paint();
        netPaint.setAntiAlias(true);
        netPaint.setStyle(Paint.Style.STROKE);
        netPaint.setStrokeWidth(netLineWidth);
        netPaint.setColor(netColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(32);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);

        overPaint = new Paint();
        overPaint.setStyle(Paint.Style.FILL);
        overPaint.setAntiAlias(true);
        overPaint.setColor(overlayColor);

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        maxRadius = Math.min(w, h) / 2 * RADIUS_SCALE;
        centerPointX = w / 2;
        centerPointY = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNet(canvas);
        drawOverlay(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent; //文字的高度  baseline=0  所以 descent为正数  ascent为负数
        int angle = 360 / arrCount;
        for (int i = 0; i < arrCount; i++) {
            float textWidth = textPaint.measureText(textArr[i]);
            float x = (float) (centerPointX + (maxRadius + textHeight / 2) * Math.cos(Math.toRadians(angle * i)));
            float y = (float) (centerPointY + (maxRadius + textHeight / 2) * Math.sin(Math.toRadians(angle * i)));
            if (angle * i == 90 || angle * i == 270) { //90度 270度
                canvas.drawText(textArr[i], x - textWidth / 2, y + 8, textPaint);
            } else if (angle * i > 90 && angle * i < 270) {
                canvas.drawText(textArr[i], x - textWidth, y, textPaint);
            } else {
                canvas.drawText(textArr[i], x, y, textPaint);
            }
        }


    }

    private void drawOverlay(Canvas canvas) {
        Path path = new Path();
        int angle = 360 / arrCount;
        for (int i = 0; i < arrCount; i++) {
            float x = (float) (centerPointX + maxRadius * Math.cos(Math.toRadians(angle * i)) * overlayPercentArr[i]);
            float y = (float) (centerPointY + maxRadius * Math.sin(Math.toRadians(angle * i)) * overlayPercentArr[i]);
            if (i == 0) {
                path.moveTo(x, centerPointY);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 5, overPaint);
        }
        path.close();
        overPaint.setAlpha(overlayAlpha);
        canvas.drawPath(path, overPaint);

    }

    private void drawNet(Canvas canvas) {
        Path mPath = new Path();
        int radius = 0;
        int angle = 360 / arrCount;
        for (int j = 0; j < arrCount; j++) {
            mPath.reset();
            radius = (int) (maxRadius / arrCount) * (j + 1);
            for (int i = 0; i < arrCount + 1; i++) {
                if (i == 0) {
                    mPath.moveTo(centerPointX + radius, centerPointY);
                } else {
                    float x = (float) (centerPointX + radius * Math.cos(Math.toRadians(angle * i)));
                    float y = (float) (centerPointY + radius * Math.sin(Math.toRadians(angle * i)));
                    mPath.lineTo(x, y);
                }
            }
            mPath.close();
            canvas.drawPath(mPath, netPaint);
        }

        //绘制轴线
        for (int i = 1; i < arrCount + 1; i++) {
            mPath.reset();
            mPath.moveTo(centerPointX, centerPointY);
            float x = (float) (centerPointX + radius * Math.cos(Math.toRadians(angle * i)));
            float y = (float) (centerPointY + radius * Math.sin(Math.toRadians(angle * i)));
            mPath.lineTo(x, y);
            canvas.drawPath(mPath, netPaint);
        }

    }

    public void setTextArr(String... strings) {
        this.textArr = strings;
    }

    public void setOverlayPercent(float[] floats) {
        this.overlayPercentArr = floats;
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
