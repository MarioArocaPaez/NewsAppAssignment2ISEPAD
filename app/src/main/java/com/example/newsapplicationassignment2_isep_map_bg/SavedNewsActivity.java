package com.example.newsapplicationassignment2_isep_map_bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Source;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SavedNewsActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    CustomArticleAdapter adapter;

    GoogleSignInAccount account;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Articles> ls;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle AbToggle;
    ImageView profilePicture;
    TextView googName;
    TextView gMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        Intent i = getIntent();
        ls = (List<Articles>) i.getSerializableExtra("LIST");

        account = GoogleSignIn.getLastSignedInAccount(this);

        firebaseDatabase = FirebaseDatabase.getInstance("https://newsapp-808c0-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference(account.getId()).child("saved");


        showNews(ls);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.sidebarSaved);
        AbToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(AbToggle);
        AbToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting the behavior for the selection different items of the sidebar
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_news:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                        break;
                    }
                    case R.id.nav_profile:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    }
                    case R.id.nav_country:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), CountryActivity.class));
                        break;
                    }
                    case R.id.nav_saved:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //startActivity(new Intent(getApplicationContext(), SavedNewsActivity.class).putExtra("LIST", (Serializable) ls));
                        break;
                    }
                }
                return SavedNewsActivity.super.onOptionsItemSelected(item);
            }
        });

        profilePicture = navigationView.getHeaderView(0).findViewById(R.id.profile_picture);
        googName = navigationView.getHeaderView(0).findViewById(R.id.google_name);
        gMail = navigationView.getHeaderView(0).findViewById(R.id.google_gmail);


        if(account != null){
            Uri googlePicture = account.getPhotoUrl();
            String Name = account.getDisplayName();
            String Mail = account.getEmail();

            Picasso.get().load(googlePicture).into(profilePicture);
            googName.setText(Name);
            gMail.setText(Mail);
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void showNews(List<Articles> ls) {

        if(ls.isEmpty()){
            TextView msg = findViewById(R.id.msgTextView);
            msg.setText("No saved articles yet\n:(");
            //Toast.makeText(SavedNewsActivity.this, "No saved articles yet", Toast.LENGTH_SHORT).show();
        }else {
            TextView msg = findViewById(R.id.msgTextView);
            msg.setText("");
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            recyclerView = findViewById(R.id.recyclerViewMain);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            adapter = new CustomArticleAdapter(this, ls, this, account);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void OnNewsClicked(Articles articles) {
        startActivity(new Intent(SavedNewsActivity.this, DetailsActivity.class));
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class).putExtra("data", articles);
        startActivity(intent);
    }


}