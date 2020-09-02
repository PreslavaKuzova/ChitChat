package com.example.chitchat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chitchat.R;
import com.example.chitchat.ui.authenticate.AuthenticationActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000;

    private final Handler mHandler   = new Handler();
    private final Launcher mLauncher = new Launcher();

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mLauncher, SPLASH_DELAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    private void launch() {
        if (!isFinishing()) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    private class Launcher implements Runnable {
        @Override
        public void run() {
            launch();
        }
    }
}