package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.HashMap;
import java.util.Map;

import static com.example.finalproject.CharacterChoose.selectedImageUri;

public class Game<onOptionsItemSelected> extends AppCompatActivity {
    SeekBar seekBar;
    static RatingBar ratingBar;
    static int possible_game_num;
    static float toX;
    Animation animation;
    static ImageView iv_ball;
    static ImageView iv_goalkeeper;
    MenuItem menuShare;
    static Button gameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        iv_ball = (ImageView) findViewById(R.id.imageBall);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        // 점수 초기화
        possible_game_num=5;
        ratingBar.setRating(possible_game_num);

        // CharacterChoose.java가 보낸 데이터에 근거하여, 게임 캐릭터 이미지를 지정한다.
        iv_goalkeeper = (ImageView) findViewById(R.id.imageGoalKeeper);
        Intent it = getIntent();
        String type = it.getExtras().getString("type");
        if (type.equals("int")) {
            iv_goalkeeper.setImageResource(it.getExtras().getInt("ID"));
        } else {
            if(selectedImageUri!=null)
                iv_goalkeeper.setImageURI(selectedImageUri);
            }


        // gameBtn 클릭 시, 게임이 시작된다.
        gameBtn = (Button) findViewById(R.id.gameBtn);
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (possible_game_num > 0) {
                    startGame();
                    gameBtn.setText("게임 중..");

                } else {
                   ;
                }
            }

            // 골키퍼 게임 시작
            protected void startGame() {
                float fromX, fromY;
                class startGame {
                    ;
                }
                fromX = (float) Math.random() * 700 + 100;
                toX = (float) Math.random() * 700 + 100;
                fromY = iv_ball.getY() + 100;
                animation = new TranslateAnimation(fromX, toX, fromY, -800);
                animation.setAnimationListener(new MyAnimationListener());
                animation.setDuration(4000);
                animation.setStartOffset(200);
                iv_ball.startAnimation(animation);

            }
        });

        // seekBar로, 골키퍼 캐릭터를 이동시킨다.
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        seekBar.setMax(700);
        seekBar.setProgress(350);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int move = seekBar.getProgress();
                iv_goalkeeper.setX(move);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    // OptionMenu: 카카오톡 공유하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menuShare = menu.add("친구에게 공유하기");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals((menuShare))) {
            kakaolink();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    // 카카오톡 공유하기 버튼을 눌렀을 때, 전송되는 메시지
    public void kakaolink() {
        FeedTemplate params = FeedTemplate.newBuilder(ContentObject.newBuilder("골키퍼 게임",
                "https://en.pimg.jp/035/018/961/1/35018961.jpg",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("시간을 정해두고 게임을 할 수 있습니다.")
                        .build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl("https://developers.kakao.com").build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {}

            @Override
            public void onSuccess(KakaoLinkResponse result) {
            }
        });

    }
}
