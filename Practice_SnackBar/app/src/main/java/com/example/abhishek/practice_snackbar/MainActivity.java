package com.example.abhishek.practice_snackbar;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void showSnackBar(View view) {
        Snackbar.make(findViewById(R.id.layout),"this is snackbar",Snackbar.LENGTH_LONG).show();
    }
}
