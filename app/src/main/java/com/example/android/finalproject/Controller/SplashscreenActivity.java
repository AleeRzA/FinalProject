package com.example.android.finalproject.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android.finalproject.R;

public class SplashscreenActivity extends AppCompatActivity {


    private LottieAnimationView mLottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splashscreen);
        mLottieAnimationView = findViewById(R.id.animation_view);
        mLottieAnimationView.setSpeed(1.2f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = MainActivity.newIntent(SplashscreenActivity.this);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                finish();
            }
        }, 4000);

    }


}
