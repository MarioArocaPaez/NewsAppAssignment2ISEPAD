package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    Button logInBut;
    Button exitBut;
    GoogleSignInOptions gso;
    public static GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInBut = findViewById(R.id.logInBut);
        exitBut = findViewById(R.id.exitBut);
        //Sign in will be regular with gmail
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Signed in user stored in gsc
        gsc = GoogleSignIn.getClient(this,gso);

        logInBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn();
            }
        });

        exitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void LogIn() {
        Intent intent = gsc.getSignInIntent();
        //100 -> everything ok, continue
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                MainActivity();
            } catch (ApiException e){
                Toast.makeText(this, "Error trying to log in", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Go to main activity when logged in
    //Main Activity is News Activity
    private void MainActivity() {
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);
    }
}