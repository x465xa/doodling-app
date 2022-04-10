package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    TextView mInfoText;
    DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfoText = (TextView) findViewById(R.id.info);
        mDrawView = (DrawView) findViewById(R.id.DrawView);
    }

    private void setInfoText (String text) {
        mInfoText.setText(text);
    }

    public static final String DEBUG_TAG = "moo";

    public boolean onTouchEvent (MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        setInfoText("Action was DOWN");
                        mDrawView.startStroke();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        final int pointerIndex = MotionEventCompat.getActionIndex(event);
                        final float x = MotionEventCompat.getX(event, pointerIndex);
                        final float y = MotionEventCompat.getY(event, pointerIndex);

                        setInfoText("Action was MOVE (" + ((int)x) + "," + ((int)y) + ")");
                        mDrawView.addPoint(new Point((int)x, (int)y - 250));
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        setInfoText("Action was UP");
                        mDrawView.endStroke();
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        setInfoText("Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        setInfoText("Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default :
                        return super.onTouchEvent(event);
                }
    }

    public void onClearButtonClicked(View view) {
        mDrawView.clearStrokes();
    }

    static final int GET_SAVE_FILENAME = 1;
    static final int GET_LOAD_FILENAME = 2;
    static final int GET_STROKE_WIDTH = 3;
    static final int GET_STROKE_COLOR = 4;

    public void onSaveButtonClicked(View view) {
        Intent intent = new Intent(this, SelectFileActivity.class);

        startActivityForResult(intent, GET_SAVE_FILENAME);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == GET_SAVE_FILENAME) {
            if (resultCode == RESULT_OK) {
                String filename = intent.getStringExtra(SelectFileActivity.EXTRA_FILENAME);
                mDrawView.saveStrokes(this, filename);
            }
        } else if (requestCode == GET_LOAD_FILENAME) {
            if (resultCode == RESULT_OK) {
                String filename = intent.getStringExtra(SelectFileActivity.EXTRA_FILENAME);
                mDrawView.loadStrokes(this, filename);
            }
        } else if (requestCode == GET_STROKE_WIDTH) {
            if (resultCode == RESULT_OK) {
                int strokeWidth = intent.getIntExtra(SelectStrokeWidth.EXTRA_STROKE_WIDTH,
                        0);
                mDrawView.setStrokeWidth(strokeWidth);
            }
        } else if (requestCode == GET_STROKE_COLOR) {
            if (resultCode == RESULT_OK) {
                int strokeColor = intent.getIntExtra(SelectStrokeColor.EXTRA_STROKE_COLOR,
                        Color.BLACK);
                mDrawView.setStrokeColor(strokeColor);
            }
        }
    }

    public void onLoadButtonClicked(View view) {
        Intent intent = new Intent(this, SelectFileActivity.class);
        intent.putExtra(SelectFileActivity.EXTRA_IS_LOAD, true);
        startActivityForResult(intent, GET_LOAD_FILENAME);
    }

    public void onWidthButtonClicked(View view) {
        Intent intent = new Intent(this, SelectStrokeWidth.class);

        startActivityForResult(intent, GET_STROKE_WIDTH);
    }

    public void onColorButtonClicked(View view) {
        Intent intent = new Intent(this, SelectStrokeColor.class);

        startActivityForResult(intent, GET_STROKE_COLOR);
    }
}