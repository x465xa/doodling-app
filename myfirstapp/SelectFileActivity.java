package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SelectFileActivity extends AppCompatActivity {

    public static final String EXTRA_FILENAME = "com.example.myfirstapp.FILENAME";
    public static final String EXTRA_IS_LOAD = "com.example.myfirstapp.isload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
        Intent intent = getIntent();
        Boolean is_load = intent.getBooleanExtra(EXTRA_IS_LOAD, false);
        if (is_load) {
            Button okButton = (Button) findViewById(R.id.saveButton);
            okButton.setText("Load");
        }
    }

    public void onSaveButtonClicked (View view) {
        Intent intent = new Intent(this, SelectFileActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String fileName = editText.getText().toString();
        intent.putExtra(EXTRA_FILENAME, fileName);
        setResult(RESULT_OK, intent);
        finish();
    }
}