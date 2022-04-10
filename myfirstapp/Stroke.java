package com.example.myfirstapp;

import android.graphics.Color;
import android.graphics.Point;
import java.util.ArrayList;

class Stroke {
    ArrayList<Point> mPoints = new ArrayList<Point>();
    int mWidth = 0;
    int mColor = Color.BLACK;

    public Stroke() {}

    public void addPoint(Point p) {
        mPoints.add(p);
    }

    public void clearPoints() {
        mPoints.clear();
    }

    public int pointCount() {
        return mPoints.size();
    }

    public Point getPoint(int index) {
        return mPoints.get(index);
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}