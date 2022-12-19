package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button bBusiness;
    Button bEntertainment;
    Button bGeneral;
    Button bHealth;
    Button bScience;
    Button bSports;
    Button bTechnology;
    SearchView searchView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle AbToggle;
    ImageView profilePicture;
    TextView googName;
    TextView gMail;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(AbToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //TODO: Add something better than ProgressDialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Searching for news articles...");
        dialog.show();

        bBusiness = findViewById(R.id.btnBusiness);
        bBusiness.setOnClickListener(this);
        bEntertainment = findViewById(R.id.btnEntertainment);
        bEntertainment.setOnClickListener(this);
        bGeneral = findViewById(R.id.btnGeneral);
        bGeneral.setOnClickListener(this);
        bHealth = findViewById(R.id.btnHealth);
        bHealth.setOnClickListener(this);
        bScience = findViewById(R.id.btnScience);
        bScience.setOnClickListener(this);
        bSports = findViewById(R.id.btnSports);
        bSports.setOnClickListener(this);
        bTechnology = findViewById(R.id.btnTechnology);
        bTechnology.setOnClickListener(this);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching for News of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(NewsActivity.this);
                manager.getArticles(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        RequestManager manager = new RequestManager(this);
        manager.getArticles(listener, "general", null);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.sidebar);
        AbToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(AbToggle);
        AbToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_profile:
                    {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    }
                    case R.id.nav_country:
                    {
                        Toast.makeText(NewsActivity.this, "Country pressed", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return NewsActivity.super.onOptionsItemSelected(item);
            }
        });

        profilePicture = navigationView.getHeaderView(0).findViewById(R.id.profile_picture);
        googName = navigationView.getHeaderView(0).findViewById(R.id.google_name);
        gMail = navigationView.getHeaderView(0).findViewById(R.id.google_gmail);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

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

    private final OnFetchDataListener<ApiResponse> listener = new OnFetchDataListener<ApiResponse>() {
        @Override
        public void onFetchData(List<Articles> ls, String message) {
            if(ls.isEmpty()){
                Toast.makeText(NewsActivity.this, "No results ", Toast.LENGTH_SHORT).show();
            }else{
                showNews(ls);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(NewsActivity.this, "Error occurred :(", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Articles> ls) {
        recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, ls, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(Articles articles) {
        startActivity(new Intent(NewsActivity.this, DetailsActivity.class));
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class).putExtra("data", articles);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        dialog.setTitle("Searching news of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getArticles(listener, category, null);
    }
}