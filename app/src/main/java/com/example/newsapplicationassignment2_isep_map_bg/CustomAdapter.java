package com.example.newsapplicationassignment2_isep_map_bg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplicationassignment2_isep_map_bg.Models.Articles;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;



public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Articles> articles;
    private SelectListener listener;
    private GoogleSignInAccount account;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    public CustomAdapter(Context context, List<Articles> articles, SelectListener listener, GoogleSignInAccount account) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
        this.account = account;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        firebaseDatabase = FirebaseDatabase.getInstance("https://newsapp-808c0-default-rtdb.europe-west1.firebasedatabase.app");

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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(articles.get(position));
            }
        });



        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = (String)holder.saveButton.getTag();
                if(tag == "unsaved") {
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_added_24);
                    holder.saveButton.setTag("saved");
                    addDatatoFirebase(articles.get(position));
                } else {
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    holder.saveButton.setTag("unsaved");
                    deleteDataFromFirebase(articles.get(position));
                }
            }

            private void deleteDataFromFirebase(Articles article) {
                // below lines are used to get reference for our database.
                databaseReference = firebaseDatabase.getReference(account.getId()).child(article.getPublishedAt());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void addDatatoFirebase(Articles article) {

                // below lines are used to get reference for our database.
                databaseReference = firebaseDatabase.getReference(account.getId()).child(article.getPublishedAt());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference will sends data to firebase.
                        databaseReference.setValue(article);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
