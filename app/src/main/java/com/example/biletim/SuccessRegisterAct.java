package com.example.biletim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    Button btn_start;
    ImageView success_register_image;
    TextView success_register_title, success_register_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        btn_start = findViewById(R.id.btn_start);
        success_register_image = findViewById(R.id.success_register_image);
        success_register_title = findViewById(R.id.success_register_title);
        success_register_des = findViewById(R.id.success_register_des);

        btn_start.startAnimation(btt);
        success_register_image.startAnimation(app_splash);
        success_register_title.startAnimation(ttb);
        success_register_des.startAnimation(ttb);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegisterAct.this,HomeActivity.class);
                startActivity(gotohome);
            }
        });
    }
}
