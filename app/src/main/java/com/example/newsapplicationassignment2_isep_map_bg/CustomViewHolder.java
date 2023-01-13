package com.example.newsapplicationassignment2_isep_map_bg;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView textTitle;
    TextView textSource;
    ImageView img;
    CardView cardView;
    ImageView saveButton;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        textTitle = itemView.findViewById(R.id.title);
        textSource = itemView.findViewById(R.id.source);
        img = itemView.findViewById(R.id.image);
        cardView = itemView.findViewById(R.id.mainContainer);
        saveButton = itemView.findViewById(R.id.saveButton);
    }
}
