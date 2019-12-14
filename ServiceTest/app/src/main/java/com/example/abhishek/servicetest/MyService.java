package com.example.abhishek.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.e("MyService","onCreate Called");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService","onStartCommand Called");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("MyService","onDestroy Called");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("MyService","iBinder Called");
        return new LocalBinder();

    }

    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
}
