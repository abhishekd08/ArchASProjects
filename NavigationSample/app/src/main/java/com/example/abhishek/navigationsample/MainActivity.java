package com.example.abhishek.navigationsample;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private TextView textView;
    private WifiStateChangeListener wifi;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String msg = intent.getStringExtra("text");
                Toast.makeText(context, "msg at main activity ::: "+msg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("text");
            Toast.makeText(context, msg , Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        if (savedInstanceState != null && savedInstanceState.containsKey("text")) {
            textView.setText(savedInstanceState.getString("text"));
        }
        Log.d(TAG, "onCreate called");
        textView.append("\nOnCreate Called");

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver, new IntentFilter("custom_event"));
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver2, new IntentFilter("sleep_event"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView.append("\nOnStart Called");
        Log.d(TAG, "onStart: called");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifi = new WifiStateChangeListener();
        registerReceiver(wifi, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        if (i != null) {
            if (i.getStringExtra("text") != null) {
                Toast.makeText(this, i.getStringExtra("text"), Toast.LENGTH_SHORT).show();
            }
        }
        textView.append("\nOnResume Called");
        Log.d(TAG, "onResume: called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("text", textView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        textView.append("\nOnPause Called");
        Log.d(TAG, "onPause: called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        textView.append("\nOnStop Called");
        Log.d(TAG, "onStop: called");

        unregisterReceiver(wifi);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textView.append("\nOnDestroy Called");
        Log.d(TAG, "onDestroy: called");
    }

    public void addButtonOnCLick(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("a", 10);
        intent.putExtra("b", 20);
        startActivityForResult(intent, 100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "addition : " + data.getIntExtra("addition", 0), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startServiceButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        startService(intent);
    }

    public void sendIntentButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
