package com.example.newsapplicationassignment2_isep_map_bg;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapplicationassignment2_isep_map_bg.Models.ApiResponse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    public static String countryLabel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //library Retrofit used to facilitate API calls
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getArticles(OnFetchDataListener listener, String category, String query){

        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<ApiResponse> call = callNewsApi.callArticles(CountryActivity.country, category, query, context.getString(R.string.api_key));

        try{
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Failure in response", Toast.LENGTH_SHORT).show();
                    }
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    listener.onError("Request Failure");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<ApiResponse> callArticles(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String apiKey
        );
    }
}
