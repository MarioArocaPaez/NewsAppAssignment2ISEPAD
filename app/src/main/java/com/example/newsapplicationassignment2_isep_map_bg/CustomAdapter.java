package com.example.newsapplicationassignment2_isep_map_bg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Articles> articles;

    public CustomAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textTitle.setText(articles.get(position).getTitle());
        holder.textSource.setText(articles.get(position).getSource().getName());

        if(articles.get(position).getUrlToImage() != null){
            Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
