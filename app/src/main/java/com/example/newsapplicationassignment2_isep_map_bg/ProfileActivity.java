package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ProfileActivity extends AppCompatActivity {

    TextView welcome, name, mail;
    Button logout;
    ImageView profilePic;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        welcome = findViewById(R.id.welcome);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        profilePic = findViewById(R.id.imageView);
        logout = findViewById(R.id.logOutBut);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        firebaseDatabase = FirebaseDatabase.getInstance("https://newsapp-808c0-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference(account.getId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String country_code;
                String country_label;
                if (snapshot.child("country_code").getValue() == null) {
                    country_code = "";
                    country_label = "All";
                } else {
                    country_code = snapshot.child("country_code").getValue(String.class);
                    country_label = snapshot.child("country_label").getValue(String.class);
                }
                CountryActivity.country = country_code;
                RequestManager.countryLabel = country_label;
                if (first == true) {
                    name.setText("Current country: " + RequestManager.countryLabel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String Name = "";
        String Mail = "";
        if(account != null){
            Uri googlePicture = account.getPhotoUrl();
            Name = account.getDisplayName();
            Mail = account.getEmail();
            //In case no google pic we will have a default pic
            if(googlePicture != null){
                Picasso.get().load(googlePicture).into(profilePic);
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });

        if (first == true) {
            welcome.setText("Welcome Back!");
            logout.setText("Go to news");
            mail.setText(Name);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                }
            });
        } else {
            welcome.setText("Your profile:");
            name.setText(Name);
            mail.setText(Mail);
        }
    }

    private void LogOut() {
        LoginActivity.gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }
    //back button in bar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}