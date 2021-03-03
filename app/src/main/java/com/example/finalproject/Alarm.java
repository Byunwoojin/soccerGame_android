package com.example.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Alarm extends AppCompatActivity {
    static AlarmManager alarmManager;
    TimePicker timePicker;
    static PendingIntent pendingIntent;
    Button startButton,stopButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        timePicker = findViewById(R.id.time_picker);

        startButton = (Button) findViewById(R.id.alarmSTART);
        stopButton = (Button)findViewById(R.id.alarmSTOP);
        startButton.setOnClickListener(ClockListener);
        stopButton.setOnClickListener(ClockListener);
    }
    private void start(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        calendar.set(Calendar.MINUTE,timePicker.getMinute());
        calendar.set(Calendar.SECOND,00);

        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1);
        }
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("state","ON");
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(this,"알람 예정: "+format.format(calendar.getTime()),Toast.LENGTH_SHORT).show();


    }
    private void stop(){

        if(pendingIntent==null){
            return;
        }
        alarmManager.cancel(pendingIntent);
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("state","OFF");

        sendBroadcast(intent);
        pendingIntent=null;
    }

    View.OnClickListener ClockListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.alarmSTART:
                    start();
                    break;
                case R.id.alarmSTOP:
                    stop();
                    break;
            }
        }
    };
}