package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninAct extends AppCompatActivity {

    EditText xusername, xpassword;
    TextView btn_new_account;
    Button btn_sign_in;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SigninAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty username!", Toast.LENGTH_SHORT).show();
                    // ubah state menjadi SIGN IN
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }
                else{
                    if(password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Empty password!", Toast.LENGTH_SHORT).show();
                        // ubah state menjadi SIGN IN
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    // ambil pass dari FB
                                    String passfromFB = dataSnapshot.child("password").getValue().toString();

                                    // validasi pass
                                    if (password.equals(passfromFB)) {
                                        // menyimpan data ke local storage
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        // pindah act
                                        Intent gotohome = new Intent(SigninAct.this, HomeActivity.class);
                                        startActivity(gotohome);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                                        // ubah state menjadi SIGN IN
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No username!", Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                }

        });
    }
}
