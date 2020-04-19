package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    CircularImageView photo_user;
    LinearLayout btn_ticket_pisa, btn_ticket_torri, btn_ticket_pagoda, btn_ticket_borobudur, btn_ticket_sphinx, btn_ticket_monas;
    TextView user_balance, full_name, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_borobudur = findViewById(R.id.btn_ticket_borobudur);
        btn_ticket_sphinx = findViewById(R.id.btn_ticket_sphinx);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);
        user_balance = findViewById(R.id.user_balance);
        bio = findViewById(R.id.bio);
        full_name = findViewById(R.id.full_name);
        photo_user = findViewById(R.id.photo_user);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                full_name.setText(dataSnapshot.child("full_name").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeActivity.this).load(Objects.requireNonNull(dataSnapshot.child("url_photo_profile").getValue()).toString()).into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        photo_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeActivity.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                // meletakan data ke intent
                gotopisaticket.putExtra("ticket_type", "Pisa");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                gotopisaticket.putExtra("ticket_type", "Torri");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                gotopisaticket.putExtra("ticket_type", "Pagoda");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_borobudur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                gotopisaticket.putExtra("ticket_type", "Borobudur");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                gotopisaticket.putExtra("ticket_type", "Sphinx");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this, TicketDetailAct.class);
                gotopisaticket.putExtra("ticket_type", "Monas");
                startActivity(gotopisaticket);
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
