package deshpande.abhishek.wmc_exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView, nameTextview;
    private Button prevBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.secondactivity_textview);
        nameTextview = findViewById(R.id.secondactivity_nametextview);

        String name = getIntent().getStringExtra("name");
        nameTextview.setText("Name : " + name);

        prevBtn = findViewById(R.id.secondactivity_prev_button);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nextBtn = findViewById(R.id.secondactivity_next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
