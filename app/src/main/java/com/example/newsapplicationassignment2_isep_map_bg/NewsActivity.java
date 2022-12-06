package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.google.android.gms.common.api.Api;

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
        bSports = findViewById(R.id.btnTechnology);
        bSports.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getArticles(listener, "general", null);
    }

    private final OnFetchDataListener<ApiResponse> listener = new OnFetchDataListener<ApiResponse>() {
        @Override
        public void onFetchData(List<Articles> ls, String message) {
            showNews(ls);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

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
        RequestManager manager = new RequestManager(this);
        manager.getArticles(listener, category, null);
    }
}