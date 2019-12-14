package com.example.abhishek.retailerapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.abhishek.retailerapp.Contract.SignUpContract;
import com.example.abhishek.retailerapp.R;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
