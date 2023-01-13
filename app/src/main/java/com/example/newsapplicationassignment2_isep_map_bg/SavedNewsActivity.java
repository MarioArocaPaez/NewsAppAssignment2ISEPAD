package com.example.newsapplicationassignment2_isep_map_bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Source;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SavedNewsActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;

    GoogleSignInAccount account;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Articles> ls;


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
            adapter = new CustomAdapter(this, ls, this, account);
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