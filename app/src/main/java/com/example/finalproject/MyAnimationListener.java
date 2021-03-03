package com.example.finalproject;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import static com.example.finalproject.Game.gameBtn;
import static com.example.finalproject.Game.iv_ball;
import static com.example.finalproject.Game.iv_goalkeeper;
import static com.example.finalproject.Game.possible_game_num;
import static com.example.finalproject.Game.ratingBar;
import static com.example.finalproject.Game.toX;

public class MyAnimationListener implements Animation.AnimationListener{
    View contentView = iv_ball.getRootView();

    static Boolean continue_Game=true;


    @Override
    public void onAnimationStart(Animation animation) {
        ;
    }

    @Override
    public void onAnimationEnd(Animation animation) {


        // 골키퍼의 X좌표
        float keeper_x1 = iv_goalkeeper.getX();
        float keeper_x2 = iv_goalkeeper.getX() + iv_goalkeeper.getWidth();


        // 축구 공의 x좌표
        float b_x1 = toX;
        float b_x2 = toX + iv_ball.getWidth();

        // Collision check
        if (b_x2 < keeper_x1 || b_x1 > keeper_x2) {
            continue_Game = false;

        } else {
            continue_Game = true;
        }


        // 골키퍼가 축구공을 막은 경우(=충돌 발생시), 게임이 계속된다.
        if (continue_Game == true) {

            float fromX = (float) Math.random() * 700 + 100;
            toX = (float) Math.random() * 700 + 100;
            float fromY = iv_ball.getY() + 100;
            animation = new TranslateAnimation(fromX, toX, fromY, -800);
            animation.setAnimationListener(new MyAnimationListener());
            animation.setDuration(4000);
            animation.setStartOffset(200);
            iv_ball.startAnimation(animation);


        }

        // 골키퍼가 축구공을 막지 못한 경우(=충돌 발생하지 않은 경우), 게임 가능 횟수가 줄어든다.
        if(continue_Game==false) {
            possible_game_num--;
            continue_Game = true;
            if (possible_game_num == 0) {
                iv_ball.clearAnimation();
                Toast.makeText(ratingBar.getContext(), "게임 종료", Toast.LENGTH_LONG).show();
                possible_game_num = 5;
                ratingBar.setRating(possible_game_num);
                gameBtn.setText("Start");
            } else {
                ratingBar.setRating(possible_game_num);
                Toast.makeText(ratingBar.getContext(), "게임 가능횟수 -1", Toast.LENGTH_LONG).show();
                gameBtn.setText("남은기회 사용");
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        ;
    }

}