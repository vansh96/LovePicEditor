package com.example.vaksys_android.lovepiceditor.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.vaksys_android.lovepiceditor.Activity.StartActivity;
import com.example.vaksys_android.lovepiceditor.R;

public class Splash_screen extends AppCompatActivity
{
    Handler handler;
    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView= (ImageView) findViewById(R.id.img_splash);
        {
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {

                    Intent i = new Intent(Splash_screen.this,StartActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
