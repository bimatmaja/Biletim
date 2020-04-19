package com.example.biletim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RegisterTwoAct extends AppCompatActivity {

    Button btn_continue, btn_add_photo;
    EditText full_name, bio;
    CircularImageView pic_photo_register_user;
    LinearLayout btn_back;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        bio = findViewById(R.id.bio);
        full_name = findViewById(R.id.full_name);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        pic_photo_register_user = findViewById(R.id.pic_photo_register_user);

        // Nyimpen di FireBase
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photouser").child(username_key_new);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ubah state menjadi loading
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");


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
                                                                                            reference.getRef().child("full_name").setValue(full_name.getText().toString());
                                                                                            reference.getRef().child("bio").setValue(bio.getText().toString());
                                                                                        }
                                                                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent gotosuccess = new Intent(RegisterTwoAct.this,SuccessRegisterAct.class);
                                    startActivity(gotosuccess);
                                }
                            });


                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoprev = new Intent(RegisterTwoAct.this,RegisterOneAct.class);
                startActivity(backtoprev);
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
            Picasso.with(this).load(photo_location).fit().centerCrop().into(pic_photo_register_user);

        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
