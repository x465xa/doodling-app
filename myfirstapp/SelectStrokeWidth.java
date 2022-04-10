package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SelectStrokeWidth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stroke_width);
    }

    int mStrokeWidth = 0;
    public static final String EXTRA_STROKE_WIDTH = "com.example.myfirstapp.STROKEWIDTH";

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.strokeWidth1:
                if(checked) {
                    mStrokeWidth = 1;
                }
                break;
            case R.id.strokeWidth2:
                if(checked) {
                    mStrokeWidth = 2;
                }
                break;
            case R.id.strokeWidth4:
                if(checked) {
                    mStrokeWidth = 4;
                }
                break;
            case R.id.strokeWidth8:
                if(checked) {
                    mStrokeWidth = 8;
                }
                break;
        }
    }

    public void onDoneButtonClicked(View view) {
        Intent intent = new Intent(this, SelectStrokeWidth.class);
        intent.putExtra(EXTRA_STROKE_WIDTH, mStrokeWidth);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelButtonClicked(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }
}