package com.basri.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ///membuat jedah
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //to do
                //berpindah
                startActivity(new Intent(SplashActivity.this,
                        MainActivity.class));
                //mengclose activity
                finish();
            }
        }, 5000);
    }
}