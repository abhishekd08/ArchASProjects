package com.example.abhishek.uitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
    }

    public void evenClick(View view) {
        textView.setText("event clicked recently");
    }

    public void oddClick(View view) {
        textView.setText("last click by odd");
    }
}
