package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.google.android.gms.common.api.Api;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RequestManager manager = new RequestManager(this);
        manager.getArticles(listener, "general", null);
    }

    private final OnFetchDataListener<ApiResponse> listener = new OnFetchDataListener<ApiResponse>() {
        @Override
        public void onFetchData(List<Articles> ls, String message) {

        }

        @Override
        public void onError(String message) {

        }
    }
}