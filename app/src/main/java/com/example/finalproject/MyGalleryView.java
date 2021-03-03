package com.example.finalproject;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import static com.example.finalproject.CharacterChoose.ivCharacter;
import static com.example.finalproject.CharacterChoose.tvCharacter;

// CharacterChoose.java의 Gallery 화면 제어를 위한, MyGalleryView

public class MyGalleryView extends BaseAdapter {

    Context context;
    static int character_pos;
    static Integer[] characterID = {R.drawable.goalkeeper1, R.drawable.goalkeeper2,
            R.drawable.goalkeeper3, R.drawable.goalkeeper4, R.drawable.basic_image};
    static String[] characterTitle = {"축구하는 학생1", "축구하는 학생2", "축구하는 곰", "축구하는 사자", "사용자 이미지 선택"};

    public MyGalleryView(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return characterID.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new Gallery.LayoutParams(300, 400));
        imageView.setScaleType(imageView.getScaleType().FIT_CENTER);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setImageResource(characterID[position]);

        final int pos = position;


        imageView.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){

                character_pos =pos;
                ivCharacter.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivCharacter.setImageResource(characterID[pos]);
                ivCharacter.setTag(characterID[pos]);
                tvCharacter.setText(characterTitle[pos]);

                return false;
            }
        });
        return imageView;
    }
}
