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

public class GetStartedAct extends AppCompatActivity {

    Animation ttb, btt;
    Button btn_sign_in, btn_new_account_create;
    ImageView white_logo;
    TextView get_started_slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        white_logo = findViewById(R.id.white_logo);
        get_started_slogan = findViewById(R.id.get_started_slogan);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);

        white_logo.startAnimation(ttb);
        get_started_slogan.startAnimation(ttb);
        btn_sign_in.startAnimation(btt);
        btn_new_account_create.startAnimation(btt);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStartedAct.this,SigninAct.class);
                startActivity(gotosign);
            }
        });

        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(GetStartedAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
