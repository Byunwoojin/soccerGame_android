package com.example.finalproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    MediaPlayer mediaPlayer;
    boolean isRunning;
    PendingIntent notifyPendingIntent;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setContentTitle("알람");

            builder.setContentText("게임을 종료할 시간입니다");
            builder.setContentIntent(notifyPendingIntent);
            Notification notification = builder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).build();
            startForeground(1, notification);
        }
        String state = intent.getStringExtra("state");
        if (!this.isRunning && state.equals("ON")) {
            this.mediaPlayer = MediaPlayer.create(this, R.raw.piano1);
            this.mediaPlayer.start();

            this.isRunning = true;
        } else if (state.equals("OFF")) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
            this.mediaPlayer.release();
            this.isRunning = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
            }
        }
        return START_NOT_STICKY;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelID = "Alarm";
        String channelName = getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setSound(null,null);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        channel.setDescription(channelName);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this,Alarm.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.createNotificationChannel(channel);
        return channelID;
    }

}

