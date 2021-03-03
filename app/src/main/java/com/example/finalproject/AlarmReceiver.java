package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra("state");
        Intent service_intent = new Intent(context,AlarmService.class);
        service_intent.putExtra("state",state);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            context.startForegroundService(service_intent);
        }else{
            context.startService(service_intent);
        }
    }
}

