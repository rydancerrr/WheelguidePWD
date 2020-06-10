package com.rysum.pwdfriend.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.rysum.pwdfriend.R;
import com.rysum.pwdfriend.Auth.Register;


public class SplashScreen extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Register.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, 1500);

    }
}