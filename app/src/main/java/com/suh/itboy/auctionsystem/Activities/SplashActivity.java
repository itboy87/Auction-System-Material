package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.suh.itboy.auctionsystem.R;

public class SplashActivity extends Activity {
    Runnable runStartAccountActivity;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialSetup();

        setContentView(R.layout.activity_splash);

        initializeHandler();


    }
    private void initializeHandler(){
        handler = new Handler();
        runStartAccountActivity = new Runnable() {
            @Override
            public void run() {
                startAccountActivity();
            }
        };

        handler.postDelayed(runStartAccountActivity, 000);


    }
    private void initialSetup(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        decorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    handler.removeCallbacks(runStartAccountActivity);
                    startAccountActivity();
                }
                return false;
            }
        });
    }
    private void startAccountActivity()
    {
        Intent intent = new Intent(SplashActivity.this, AccountActivity.class);
        startActivity(intent);
        finish();
    }
}
