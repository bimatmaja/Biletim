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

public class SuccessBuyTicketAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    Button btn_home, btn_view_ticket;
    ImageView success_buy_img;
    TextView success_buy_title, success_buy_des;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        btn_home = findViewById(R.id.btn_home);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        success_buy_img = findViewById(R.id.success_buy_img);
        success_buy_title = findViewById(R.id.success_buy_title);
        success_buy_des = findViewById(R.id.success_buy_des);

        btn_home.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);
        success_buy_img.startAnimation(app_splash);
        success_buy_title.startAnimation(ttb);
        success_buy_des.startAnimation(ttb);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtohome = new Intent(SuccessBuyTicketAct.this,HomeActivity.class);
                startActivity(backtohome);

            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(SuccessBuyTicketAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

    }
}
