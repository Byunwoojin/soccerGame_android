package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.finalproject.Alarm.alarmManager;
import static com.example.finalproject.Alarm.pendingIntent;

// 로그인 성공시에, 나타나는 메뉴 화면
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        LinearLayout menu1 = (LinearLayout)findViewById(R.id.menuLayout1);
        LinearLayout menu2 = (LinearLayout)findViewById(R.id.menuLayout2);
        LinearLayout menu3 = (LinearLayout)findViewById(R.id.menuLayout3);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),user_name+"님 환영합니다.\n메뉴를 골라주세요", Toast.LENGTH_SHORT).show();

        // 게임 시작
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListActivity.this, CharacterChoose.class);
                startActivity(it);
            }
        });

        // 알람 설정
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListActivity.this, Alarm.class);
                startActivity(it);
            }
        });

        // 로그아웃
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder dig = new android.app.AlertDialog.Builder(ListActivity.this);
                dig.setMessage("정말 로그아웃 하시겠습니까?");
                dig.setNegativeButton("아니오", null);
                dig.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 알람 초기화 및 로그인 화면으로 전환
                        alarmManager.cancel(pendingIntent);
                        Intent intent = new Intent(ListActivity.this,AlarmReceiver.class);
                        intent.putExtra("state","OFF");
                        sendBroadcast(intent);
                        pendingIntent=null;
                        Intent it = new Intent(ListActivity.this, MainActivity.class);
                        startActivity(it);
                    }
                });
                dig.show();
            }
        });
    }
}