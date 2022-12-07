package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button bDetails;

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
        bDetails = findViewById(R.id.seeTextDetails);

        article = (Articles) getIntent().getSerializableExtra("data");

        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        time.setText(article.getPublishedAt());
        detail.setText(article.getDescription());
        content.setText(article.getContent());
        Picasso.get().load(article.getUrlToImage()).into(ImgNews);

        bDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webPage = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(webPage);
            }
        });

        //TODO: feature ver mas

    }
}