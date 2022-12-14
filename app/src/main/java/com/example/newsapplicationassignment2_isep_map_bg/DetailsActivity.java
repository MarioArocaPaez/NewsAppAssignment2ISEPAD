package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
    Button bShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.textTitle);
        author = findViewById(R.id.textAuthor);
        time = findViewById(R.id.textTime);
        detail = findViewById(R.id.textDetail);
        content = findViewById(R.id.textContent);
        ImgNews = findViewById(R.id.imageDetail);
        bDetails = findViewById(R.id.seeTextDetails);

        article = (Articles) getIntent().getSerializableExtra("data");

        title.setText(article.getTitle());
        String aux = article.getAuthor();
        if(aux != null){
            author.setText(article.getAuthor());
        }else {
            author.setText("No author specified");
        }

        time.setText(article.getPublishedAt().substring(0,10) + " " + article.getPublishedAt().substring(11,16));
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

        bShare = findViewById(R.id.shareBut);
        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = article.getUrl();
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent,"Share using"));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}