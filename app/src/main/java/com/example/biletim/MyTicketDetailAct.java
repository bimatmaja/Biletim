package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailAct extends AppCompatActivity {

    LinearLayout btn_back;
    DatabaseReference reference;
    TextView xname_tour, xlocation, xdate_tour, xtime_tour, xrule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        btn_back = findViewById(R.id.btn_back);
        xname_tour = findViewById(R.id.xname_tour);
        xlocation = findViewById(R.id.xlocation);
        xdate_tour = findViewById(R.id.xdate_tour);
        xtime_tour = findViewById(R.id.xtime_tour);
        xrule = findViewById(R.id.xrule);

        // mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String new_tour_type = bundle.getString("name_tour");

        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(new_tour_type);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xname_tour.setText(dataSnapshot.child("name_tour").getValue().toString());
                xlocation.setText(dataSnapshot.child("location").getValue().toString());
                xdate_tour.setText(dataSnapshot.child("date_tour").getValue().toString());
                xtime_tour.setText(dataSnapshot.child("time_tour").getValue().toString());
                xrule.setText(dataSnapshot.child("rule").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
