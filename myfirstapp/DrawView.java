package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DrawView extends View {
    Paint paint = new Paint();

    ArrayList<Stroke> strokes = new ArrayList<Stroke>();
    Stroke mStroke = new Stroke();
    int mStrokeWidth = 2;
    int mColor = Color.BLACK;

    private void init() {
        paint.setColor(mColor);
        paint.setStrokeWidth(mStrokeWidth);
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void startStroke() {
        mStroke = new Stroke();
        mStroke.setWidth(mStrokeWidth);
        mStroke.setColor(mColor);
    }

    public void endStroke() {
        strokes.add(mStroke);
    }

    public void addPoint(Point p) {
        mStroke.addPoint(p);
        invalidate();
    }

    private void drawStroke(Canvas canvas, Stroke stroke) {
        for (int i = 1; i < stroke.pointCount(); i++) {
            paint.setStrokeWidth(stroke.getWidth());
            paint.setColor(stroke.getColor());
            canvas.drawLine(stroke.getPoint(i - 1).x, stroke.getPoint(i - 1).y,
                    stroke.getPoint(i).x, stroke.getPoint(i).y, paint);
        }
    }

    public void clearStrokes() {
        strokes.clear();
        mStroke.clearPoints();
        invalidate();
    }

    public void saveStrokes(Context context, String filename) {
        try {
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(
                    context.openFileOutput(filename, Context.MODE_PRIVATE)));

            int kVersion = 3;
            outStream.writeInt(kVersion);
            outStream.writeInt(strokes.size());

            for (int i = 0; i < strokes.size(); i++) {
                Stroke aStroke = strokes.get(i);
                outStream.writeInt(aStroke.getWidth());
                outStream.writeInt(aStroke.getColor());
                outStream.writeInt(aStroke.pointCount());

                for (int j = 0; j < aStroke.pointCount(); j++) {
                    outStream.writeInt(aStroke.getPoint(j).x);
                    outStream.writeInt(aStroke.getPoint(j).y);
                }
            }

            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStrokes(Context context, String filename) {
        clearStrokes();

        try {
            DataInputStream inStream = new DataInputStream(
                    context.openFileInput(filename));

            int kVersion = 3;
            int version = inStream.readInt();
            if (kVersion != version) {
                return;
            }
            int numStrokes = inStream.readInt();

            for (int i = 0; i < numStrokes; i++) {
                Stroke aStroke = new Stroke();
                strokes.add(aStroke);

                aStroke.setWidth(inStream.readInt());
                aStroke.setColor(inStream.readInt());
                int numPoints = inStream.readInt();

                for (int j = 0; j < numPoints; j++) {
                    int x = inStream.readInt();
                    int y = inStream.readInt();

                    aStroke.addPoint(new Point(x, y));
                }
            }

            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStrokeWidth(int width) {
       mStrokeWidth = width;
    }

    public void setStrokeColor(int c) {
        mColor = c;
    }

    @Override
    public void onDraw(Canvas canvas) {
        // canvas.drawLine(0, 0, 100, 100, paint);

        for (int i = 0; i < strokes.size(); i++) {
            drawStroke(canvas, strokes.get(i));
        }

        drawStroke(canvas, mStroke);
    }
}