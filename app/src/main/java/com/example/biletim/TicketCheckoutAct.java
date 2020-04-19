package com.example.biletim;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_pay, btn_min_ticket, btn_plus_ticket;
    TextView texttotal_ticket, textuser_balance, texttotal_price, name_tour, location, rule;
    ImageView exclamation_symbol;
    Integer valuetotal_ticket = 1;
    Integer user_balance = 0;
    Integer valuetotalprice = 0;
    Integer valueticketprice = 0;
    Integer balance_left = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_tour = "";
    String time_tour = "";

    //generate random number for unique
    Integer transaction_number = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String new_ticket_type = bundle.getString("ticket_type");

        btn_back = findViewById(R.id.btn_back);
        btn_pay = findViewById(R.id.btn_pay);
        btn_min_ticket = findViewById(R.id.btn_min_ticket);
        btn_plus_ticket = findViewById(R.id.btn_plus_ticket);
        exclamation_symbol = findViewById(R.id.exclamation_symbol);
        texttotal_ticket = findViewById(R.id.texttotal_ticket);
        textuser_balance = findViewById(R.id.textuser_balance);
        texttotal_price = findViewById(R.id.texttotal_price);
        name_tour = findViewById(R.id.name_tour);
        location = findViewById(R.id.location);
        rule = findViewById(R.id.rule);


        // setting value baru untuk beberapa komponen
        texttotal_ticket.setText(valuetotal_ticket.toString());

        // secara default, hiding minus btn
        btn_min_ticket.animate().alpha(0).setDuration(300).start();
        btn_min_ticket.setEnabled(false);
        exclamation_symbol.setVisibility(View.GONE);

        // mengambil data user dari fb
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_balance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textuser_balance.setText("US$ "+ user_balance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // mengambil data dari fb berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(new_ticket_type);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yg ada dengan data baru
                name_tour.setText(dataSnapshot.child("name_tour").getValue().toString());
                location.setText(dataSnapshot.child("location").getValue().toString());
                rule.setText(dataSnapshot.child("rule").getValue().toString());

                date_tour = dataSnapshot.child("date_tour").getValue().toString();
                time_tour = dataSnapshot.child("time_tour").getValue().toString();

                valueticketprice = Integer.valueOf(dataSnapshot.child("ticket_price").getValue().toString());

                valuetotalprice = valueticketprice * valuetotal_ticket;
                texttotal_price.setText("US$ " + valueticketprice+"");
                 }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_plus_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuetotal_ticket += 1;
                texttotal_ticket.setText(valuetotal_ticket.toString());
                if (valuetotal_ticket > 1) {
                    btn_min_ticket.animate().alpha(1).setDuration(300).start();
                    btn_min_ticket.setEnabled(true);
                }
                valuetotalprice = valueticketprice * valuetotal_ticket;
                texttotal_price.setText("US$ " + valuetotalprice+"");
                if(valuetotalprice > user_balance){
                    btn_pay.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay.setEnabled(false);
                    exclamation_symbol.setVisibility(View.VISIBLE);
                    textuser_balance.setTextColor(Color.parseColor("#D1206B"));
                }
            }
        });

        btn_min_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuetotal_ticket-=1;
                texttotal_ticket.setText(valuetotal_ticket.toString());
                if (valuetotal_ticket < 2) {
                    btn_min_ticket.animate().alpha(0).setDuration(300).start();
                    btn_min_ticket.setEnabled(false);
                }
                valuetotalprice = valueticketprice * valuetotal_ticket;
                texttotal_price.setText("US$ " + valuetotalprice+"");
                if(valuetotalprice < user_balance) {
                    btn_pay.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay.setEnabled(true);
                    exclamation_symbol.setVisibility(View.GONE);
                    textuser_balance.setTextColor(Color.parseColor("#203DD1"));
                }

            }
        });



        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menyimpan data user ke fb dan membuat tabel baru "My Tickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(name_tour.getText().toString() + transaction_number);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(name_tour.getText().toString() + transaction_number);
                        reference3.getRef().child("name_tour").setValue(name_tour.getText().toString());
                        reference3.getRef().child("location").setValue(location.getText().toString());
                        reference3.getRef().child("rule").setValue(rule.getText().toString());
                        reference3.getRef().child("total_ticket").setValue(valuetotal_ticket.toString());
                        reference3.getRef().child("date_tour").setValue(date_tour);
                        reference3.getRef().child("time_tour").setValue(time_tour);

                        Intent gotosuccess = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                        startActivity(gotosuccess);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                // update data balance to user
                // mengambil data user dari fb
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        balance_left = user_balance - valuetotalprice;
                        reference4.getRef().child("user_balance").setValue(balance_left);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
