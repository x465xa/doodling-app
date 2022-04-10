package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorView extends View {
    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    int mColor = Color.BLACK;

    int[] mColors = new int[] {
        0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF,
                0xFF00FF00, 0xFFFFFF00, 0xFFFF0000
    };

    private OnColorChangedListener mListener = null;

    public ColorView(Context context) {
        super(context);
    }

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnColorChangedListener(OnColorChangedListener listener) {
        mListener = listener;
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();

        if (mListener != null) {
            mListener.colorChanged(mColor);
        }
    }

    public int getColor() {
        return mColor;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2, getHeight()/2);

        Shader shader = new SweepGradient(0, 0, mColors, null);

        Paint gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gradientPaint.setShader(shader);
        gradientPaint.setStyle(Paint.Style.STROKE);
        gradientPaint.setStrokeWidth(128);

        float size = Math.min(getWidth(), getHeight());
        float r = size / 2 - 128;
        canvas.drawOval(new RectF(-r, -r, r, r), gradientPaint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(0, 0,
                200, paint);
    }

    private int ave(int s, int d, double p) {
        return s + (int)java.lang.Math.round(p * (d - s));
    }

    private int interpColor(int colors[], double unit) {
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        double p = unit * (colors.length - 1);
        int i = (int)p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index
        int c0 = colors[i];
        int c1 = colors[i+1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r = ave(Color.red(c0), Color.red(c1), p);
        int g = ave(Color.green(c0), Color.green(c1), p);
        int b = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_MOVE) {
            return true;
        }

        float x = event.getX() - (getWidth()/2);
        float y = event.getY() - (getHeight()/2);
        double angle = java.lang.Math.atan2(y, x);
        double ratio = angle/(2*Math.PI);

        setColor(interpColor(mColors, ratio));

        return true;
    }
}
