package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import static android.graphics.Color.pack;

public class SelectStrokeColor extends AppCompatActivity {

    ColorView mColorView;
    int mColor = Color.BLACK;
    String colorValue = "";
    boolean mUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stroke_color);

        EditText color = (EditText) findViewById(R.id.color);
        color.addTextChangedListener(filterTextWatcher);

        SeekBar colorR = (SeekBar) findViewById(R.id.colorR);
        SeekBar colorG = (SeekBar) findViewById(R.id.colorG);
        SeekBar colorB = (SeekBar) findViewById(R.id.colorB);
        colorR.setOnSeekBarChangeListener(seekBarListener);
        colorG.setOnSeekBarChangeListener(seekBarListener);
        colorB.setOnSeekBarChangeListener(seekBarListener);

        mColorView = (ColorView) findViewById(R.id.ColorView);
        mColorView.setOnColorChangedListener(colorViewListener);
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onColorTextEditChanged();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            onColorSeekBarChanged();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private ColorView.OnColorChangedListener colorViewListener = new ColorView.OnColorChangedListener() {
        @Override
        public void colorChanged(int color) {
            onColorViewChanged();
        }
    };

    public static final String EXTRA_STROKE_COLOR = "com.example.myfirstapp.STROKECOLOR";

    public void onColorSeekBarChanged() {
        if (mUpdating)
            return;

        SeekBar colorR = (SeekBar) findViewById(R.id.colorR);
        SeekBar colorG = (SeekBar) findViewById(R.id.colorG);
        SeekBar colorB = (SeekBar) findViewById(R.id.colorB);

        int r = colorR.getProgress();
        int g = colorG.getProgress();
        int b = colorB.getProgress();

        mUpdating = true;
        mColor = Color.argb(0xFF, r, g, b);
        mColorView.setColor(mColor);

        colorValue = String.format("#%06X", (0xFFFFFF & mColor));
        Log.e("####", "onColorSeekBarChanged, colorValue=" + colorValue);
        EditText color = (EditText) findViewById(R.id.color);
        color.setText(colorValue);
        mUpdating = false;
    }

    public void onColorTextEditChanged() {
        if (mUpdating)
            return;

        EditText color = (EditText) findViewById(R.id.color);
        String colorValue = color.getText().toString();
        Log.e("####", "onColorTextEditChanged, colorValue=" + colorValue);

        if (colorValue.length() != 7) {
            mColor = Color.BLACK;
            return;
        }

        String rValue = colorValue.substring(1,3);
        String gValue = colorValue.substring(3,5);
        String bValue = colorValue.substring(5);

        int r = Integer.parseInt(rValue, 16);
        int g = Integer.parseInt(gValue, 16);
        int b = Integer.parseInt(bValue, 16);

        mColor = Color.argb(0xFF, r, g, b);
        mColorView.setColor(mColor);

        mUpdating = true;
        SeekBar colorR = (SeekBar) findViewById(R.id.colorR);
        colorR.setProgress(r);
        SeekBar colorG = (SeekBar) findViewById(R.id.colorG);
        colorG.setProgress(g);
        SeekBar colorB = (SeekBar) findViewById(R.id.colorB);
        colorB.setProgress(b);
        mUpdating = false;
    }

    public void onColorViewChanged() {
        if (mUpdating)
            return;

        mColor = mColorView.getColor();

        int r = Color.red(mColor);
        int g = Color.green(mColor);
        int b = Color.blue(mColor);

        mUpdating = true;

        SeekBar colorR = (SeekBar) findViewById(R.id.colorR);
        colorR.setProgress(r);
        SeekBar colorG = (SeekBar) findViewById(R.id.colorG);
        colorG.setProgress(g);
        SeekBar colorB = (SeekBar) findViewById(R.id.colorB);
        colorB.setProgress(b);

        colorValue = String.format("#%06X", (0xFFFFFF & mColor));
        Log.e("####", "onColorSeekBarChanged, colorValue=" + colorValue);
        EditText colorEdit = (EditText) findViewById(R.id.color);
        colorEdit.setText(colorValue);
        mUpdating = false;
    }

    public void onDoneButtonClicked(View view) {
        Intent intent = new Intent(this, SelectStrokeWidth.class);
        intent.putExtra(EXTRA_STROKE_COLOR, mColor);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelButtonClicked(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }
}
