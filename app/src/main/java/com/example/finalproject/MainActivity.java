package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// 시작화면: 로그인화면
public class MainActivity extends AppCompatActivity {
    protected Button button;
    protected EditText edtID;
    protected EditText edtPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        button = (Button) findViewById(R.id.loginButton);
        edtID = (EditText)findViewById(R.id.edtID);
        edtPW = (EditText)findViewById(R.id.edtPW);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = edtID.getText().toString();
                String PW = edtPW.getText().toString();
                if(ID.length()==0 || PW.length()==0){
                    Toast.makeText(getApplicationContext(),"아이디와 패스워드를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    edtID.setText("");
                    edtPW.setText("");
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("Id", ID);
                    startActivity(intent);
                }
            }

        });
    }
    private void checkDangerousPermissions() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==1){
            for(int i=0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,permissions[i]+"권한이 승인됨",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,permissions[i]+"권한이 승인되지 않음",Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}