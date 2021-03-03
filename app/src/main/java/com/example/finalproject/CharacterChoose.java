package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.finalproject.MyGalleryView.characterTitle;
import static com.example.finalproject.MyGalleryView.character_pos;

public class CharacterChoose extends AppCompatActivity {
    static ImageView ivCharacter;
    static Uri selectedImageUri;
    Button selectButton;
    static TextView tvCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choose);

        selectButton = (Button) findViewById(R.id.selectButton);
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        ivCharacter = (ImageView) findViewById(R.id.ivCharacter);
        tvCharacter = (TextView)findViewById(R.id.tvCharacter);

        MyGalleryView galAdapter = new MyGalleryView(this);
        gallery.setAdapter(galAdapter);

        // 이미지뷰 리스너
        ivCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 캐릭터: '사용자 이미지 선택'에 해당하는 이미지를 클릭한 경우, 외장 미디어에 접근한다.
                if (character_pos == 4) {
                    int resultCode = 1;
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, resultCode);

                }
            }
        });

        // 'select'버튼 리스너
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dig = new AlertDialog.Builder(CharacterChoose.this);
                dig.setTitle("골키퍼 캐릭터");
                dig.setMessage("해당 이미지를 골키퍼 캐릭터로 선택하시겠습니까?");
                dig.setNegativeButton("아니오", null);
                dig.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(getApplicationContext(), Game.class);

                        // 선택된 캐릭터 이미지 위치를, Game.java에게로 전달한다.
                        TextView text = (TextView)findViewById(R.id.tvCharacter);
                        CharSequence textText = text.getText();
                        if (characterTitle[0].equals(textText)) {
                            it.putExtra("type","int");
                            it.putExtra("ID",R.drawable.goalkeeper1);
                        } else if (characterTitle[1].equals(textText)) {
                            it.putExtra("type","int");
                            it.putExtra("ID",R.drawable.goalkeeper2);
                        } else if (characterTitle[2].equals(textText)) {
                            it.putExtra("type","int");
                            it.putExtra("ID",R.drawable.goalkeeper3);
                        } else if (characterTitle[3].equals(textText)) {
                            it.putExtra("type","int");
                            it.putExtra("ID",R.drawable.goalkeeper4);
                        } else if (characterTitle[4].equals(textText)) {
                            it.putExtra("type","Uri");
                            it.putExtra("ID",R.drawable.basic_image);
                        }
                        startActivity(it);
                    }
                });
                dig.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // 사용자가 선택한 외장 미디어 이미지를, 게임 캐릭터로 지정한다.
        if (requestCode==1 && data != null) {
            selectedImageUri = data.getData();
            ivCharacter.setImageURI(selectedImageUri);
        }
        else{
            ;
        }
    }
}