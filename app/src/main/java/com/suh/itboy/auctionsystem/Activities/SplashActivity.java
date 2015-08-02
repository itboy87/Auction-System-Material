package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.R;

public class SplashActivity extends Activity {
    private static final int SPLASH_WAIT_TIME = 1000;
    Runnable runStartAccountActivity;
    Handler handler;

    private boolean isUserLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialSetup();

        setContentView(R.layout.activity_splash);

        isUserLogin = UserSessionHelper.getInstance(this).isUserLogin();

        initializeHandler();
    }

    private void initializeHandler() {
        handler = new Handler();
        runStartAccountActivity = new Runnable() {
            @Override
            public void run() {
                startNextActivity();
            }
        };
        handler.postDelayed(runStartAccountActivity, SPLASH_WAIT_TIME);
    }

    private void initialSetup() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        decorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    handler.removeCallbacks(runStartAccountActivity);
                    startNextActivity();
                }
                return false;
            }
        });

    }
    private void startNextActivity(){
        if (isUserLogin){
            startHomeActivity();
        }else{
            startAccountActivity();
        }
    }
    private void startAccountActivity() {
        Intent intent = new Intent(SplashActivity.this, AccountActivity.class);
        startActivity(intent);
        finish();
    }
    private void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
