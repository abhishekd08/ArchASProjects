package com.example.abhishek.navigationsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

public class WifiStateChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Toast.makeText(context, "NETWORK STATE CHANGED", Toast.LENGTH_SHORT).show();
        }
    }
}
