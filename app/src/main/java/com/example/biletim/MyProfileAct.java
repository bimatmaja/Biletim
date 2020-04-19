package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MyProfileAct extends AppCompatActivity {

    Button btn_back,btn_edit_profile,btn_sign_out;
    CircularImageView photo_user;
    TextView full_name, bio;
    LinearLayout btn_ticket_det;

    DatabaseReference reference, reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView myticket_place;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_ticket_det = findViewById(R.id.btn_ticket_det);
        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);

        photo_user = findViewById(R.id.photo_user);
        full_name = findViewById(R.id.full_name);
        bio = findViewById(R.id.bio);

        myticket_place = findViewById(R.id.myticket_place);
        myticket_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTicket>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                full_name.setText(dataSnapshot.child("full_name").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(MyProfileAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).into(photo_user);
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

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(MyProfileAct.this,EditProfileAct.class);
                startActivity(editprofile);
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    list.add(p);
                }
                ticketAdapter = new TicketAdapter(MyProfileAct.this, list);
                myticket_place.setAdapter(ticketAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menghapus isi/ nilai / value dari username local
                // menyimpan data ke local storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();
                // pindah act
                Intent signout = new Intent(MyProfileAct.this,SigninAct.class);
                startActivity(signout);
                finish();
            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
