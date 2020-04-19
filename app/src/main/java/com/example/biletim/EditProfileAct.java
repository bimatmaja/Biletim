package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditProfileAct extends AppCompatActivity {

    Button btn_save_profile, btn_edit_photo;
    CircularImageView photo_user_edit;
    TextView xfull_name, xbio, xpassword, xemail;

    Uri photo_location;
    Integer photo_max = 1;
    StorageReference storage;

    LinearLayout btn_back;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_edit_photo = findViewById(R.id.btn_edit_photo);
        btn_save_profile = findViewById(R.id.btn_save_profile);
        photo_user_edit = findViewById(R.id.photo_user_edit);
        xfull_name = findViewById(R.id.xfull_name);
        xbio = findViewById(R.id.xbio);
        xpassword = findViewById(R.id.xpassword);
        xemail = findViewById(R.id.xemail);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photouser").child(username_key_new);

        // ganti sesuatu realtime
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xfull_name.setText(dataSnapshot.child("full_name").getValue().toString());
                xbio.setText(dataSnapshot.child("bio").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail.setText(dataSnapshot.child("email_address").getValue().toString());
                Picasso.with(EditProfileAct.this).load(Objects.requireNonNull(dataSnapshot.child("url_photo_profile").getValue()).toString()).into(photo_user_edit);
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

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ubah state menjadi loading
                btn_save_profile.setEnabled(false);
                btn_save_profile.setText("Loading ...");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("full_name").setValue(xfull_name.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(xbio.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xemail.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // validasi untuk file
                if (photo_location != null){
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent gotomyprof = new Intent(EditProfileAct.this, MyProfileAct.class);
                                    startActivity(gotomyprof);
                                }
                            });



                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            // berpindah activity
                            Intent gotomyprof = new Intent(EditProfileAct.this,MyProfileAct.class);
                            startActivity(gotomyprof);
                        }
                    });
                }

            }
        });
        btn_edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

    }
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            photo_location = data.getData();
            Picasso.with(this).load(photo_location).fit().centerCrop().into(photo_user_edit);

        }
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
