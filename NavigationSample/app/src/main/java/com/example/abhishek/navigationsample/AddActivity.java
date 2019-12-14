package com.example.abhishek.navigationsample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    int addition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        int a = intent.getIntExtra("a",0);
        int b = intent.getIntExtra("b",0);
        addition = a+b;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_black_24dp);
    }

    private int doAddition(int a,int b){
        return a+b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        intent.putExtra("addition", addition);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("text","hello this is from intent");
        intent.setType("text/plain");
        if (intent.resolveActivity(getPackageManager()) == null){
            Toast.makeText(this, "No activity to show this", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(intent);
        }
    }
}
