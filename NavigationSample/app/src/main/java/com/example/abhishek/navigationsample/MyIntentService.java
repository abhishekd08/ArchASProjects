package com.example.abhishek.navigationsample;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: From intent service");
        //Toast.makeText(this, "From Intent Service", Toast.LENGTH_SHORT).show();
        Intent in = new Intent("sleep_event");
        in.putExtra("text","goint to sleep for 5 min");
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(in);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent i = new Intent("custom_event");
        i.putExtra("text","return intent from intent service");
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(i);
    }

}
