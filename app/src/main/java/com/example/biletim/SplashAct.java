package com.example.biletim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btt;
    ImageView app_logo;
    TextView splash_slogan;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        // load element
        app_logo = findViewById(R.id.app_logo);
        splash_slogan = findViewById(R.id.splash_slogan);

        // run animation
        app_logo.startAnimation(app_splash);
        splash_slogan.startAnimation(btt);

        getUsernameLocal();

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if(username_key_new.isEmpty()){
            // setting timer 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah act ke act lain
                    Intent gogetstarted = new Intent(SplashAct.this,GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000);
        }
        else {
            // setting timer 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah act ke act lain
                    Intent gotohome = new Intent(SplashAct.this,HomeActivity.class);
                    startActivity(gotohome);
                    finish();
                }
            }, 2000);
        }
    }
}
