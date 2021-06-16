package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bus.dental.R;
import com.example.bus.dental.utilities.MySession;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    Animation topAnim ,bottomAnim;
    ImageView imageView;
    TextView textView;
    MySession mySession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mySession = new MySession(getApplicationContext());



        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView=findViewById(R.id.jaw);
        textView=findViewById(R.id.textview1);

        imageView.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mySession.logged()){
                    Intent intent= new Intent(SplashScreen.this,Home.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();
                }

            }
        },SPLASH_SCREEN);



    }
}