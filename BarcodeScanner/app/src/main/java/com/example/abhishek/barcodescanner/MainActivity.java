package com.example.abhishek.barcodescanner;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final String TAG = "MainActivity";
    public static final int ALL_PERMISSION_CODE = 100;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Barcode Scanner By Abhishek D.");

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scannerView.setResultHandler(this);
        requestPermissions();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d(TAG, "handleResult: Results : " + rawResult.getText());

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(rawResult.getText());
        builder.setTitle("Barcode Results");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(MainActivity.this);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scannerView.stopCamera();
    }

    public void scanOnCLick(View view) {
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                scannerView.startCamera();
                scannerView.resumeCameraPreview(MainActivity.this);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}
                        , ALL_PERMISSION_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean ok = true;
        for (int result :
                grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                ok = false;
                break;
            }
        }

        if (ok) {
            scannerView.startCamera();
            scannerView.resumeCameraPreview(MainActivity.this);
        } else {
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permissions required !", Snackbar.LENGTH_SHORT);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    requestPermissions();
                }
            });
            snackbar.show();
            //Toast.makeText(this, "Permissions required !", Toast.LENGTH_SHORT).show();
        }
    }
}
