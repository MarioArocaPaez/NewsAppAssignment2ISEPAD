package com.example.newsapplicationassignment2_isep_map_bg;

import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;

import java.util.List;

public interface OnFetchDataListener<ApiResponse> {

    void onFetchData(List<Articles> ls, String message);
    void onError(String message);
}
