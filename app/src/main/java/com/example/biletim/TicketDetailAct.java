package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TicketDetailAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy;
    TextView title_ticket, location_ticket, photo_spot_ticket, wifi_ticket, festival_ticket, short_desc_ticket;
    ImageView bg_ticket_detail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        bg_ticket_detail = findViewById(R.id.bg_ticket_detail);
        btn_buy = findViewById(R.id.btn_buy);
        btn_back = findViewById(R.id.btn_back);

        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);

        // mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String new_ticket_type = bundle.getString("ticket_type");

        // mengambil data dari fb berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(new_ticket_type);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yg ada dengan data baru
                title_ticket.setText(dataSnapshot.child("name_tour").getValue().toString());
                location_ticket.setText(dataSnapshot.child("location").getValue().toString());
                photo_spot_ticket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc_ticket.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailAct.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).into(bg_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(TicketDetailAct.this,TicketCheckoutAct.class);
                // meletakan data ke intent
                gotocheckout.putExtra("ticket_type", new_ticket_type);
                startActivity(gotocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoprev = new Intent(TicketDetailAct.this,HomeActivity.class);
                startActivity(backtoprev);
            }
        });
    }
}
