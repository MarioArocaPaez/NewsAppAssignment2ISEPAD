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
    ProgressDialog dialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Articles> ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        //TODO: Add something better than ProgressDialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Searching for your saved articles...");
        dialog.show();

        Intent i = getIntent();
        ls = (List<Articles>) i.getSerializableExtra("LIST");

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        firebaseDatabase = FirebaseDatabase.getInstance("https://newsapp-808c0-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference(account.getId()).child("saved");

        // List<Articles> ls = getSavedData(databaseReference);
        //listener.onFetchData(ls, "");

        showNews(ls);
        dialog.dismiss();

    }

    public List<Articles> getSavedData(DatabaseReference databaseReference) {
        List<Articles> ls = new ArrayList<Articles>();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    //Toast.makeText(getApplicationContext(),"Data Not Available",Toast.LENGTH_LONG).show();
                    Log.d("dataSnapshot", "Vacío");
                } else {
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        Gson gson = new Gson();
                        String json = gson.toJson(messageSnapshot.getValue());

                        Gson g = new Gson();
                        Articles article = g.fromJson(json, Articles.class);

                        ls.add(article);
                        Log.d("Art en savednewsact:", ls.get(0).getTitle());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return ls;

    }

    private final OnFetchDataListener<ApiResponse> listener = new OnFetchDataListener<ApiResponse>() {
        @Override
        public void onFetchData(List<Articles> ls, String message) {


            if(ls.isEmpty()){
                Toast.makeText(SavedNewsActivity.this, "No results ", Toast.LENGTH_SHORT).show();
            }else{
                showNews(ls);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(SavedNewsActivity.this, "Error occurred :(", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Articles> ls) {
        if(ls.isEmpty()){
            Toast.makeText(SavedNewsActivity.this, "No saved articles yet", Toast.LENGTH_SHORT).show();
        }else {
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