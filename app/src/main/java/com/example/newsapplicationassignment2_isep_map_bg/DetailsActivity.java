package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    Articles article;
    TextView title;
    TextView author;
    TextView time;
    TextView detail;
    TextView content;
    ImageView ImgNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = findViewById(R.id.textTitle);
        author = findViewById(R.id.textAuthor);
        time = findViewById(R.id.textTime);
        detail = findViewById(R.id.textDetail);
        content = findViewById(R.id.textContent);
        ImgNews = findViewById(R.id.imageDetail);

        article = (Articles) getIntent().getSerializableExtra("data");

        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        time.setText(article.getPublishedAt());
        detail.setText(article.getDescription());
        content.setText(article.getContent());
        Picasso.get().load(article.getUrlToImage()).into(ImgNews);

        //TODO: feature ver mas

    }
}