package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    public TextView textView;
    public TextView currentCountry;
    public ListView listView;
    public static String country;
    public static List<String> countries;
    public static List<String> codes;
    private GoogleSignInAccount account;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countries = Arrays.asList(new String[]{"All","United Arab Emirates","Argentina","Austria","Australia","Belgium","Bulgaria","Brazil",
                "Canada","Switzerland","China","Colombia","Cuba","Czechia","Germany","Egypt","France","United Kingdom","Greece","Hong Kong",
                "Hungary","Indonesia","Ireland","Israel","India","Italy","Japan","Korea","Lithuania","Latvia","Morocco","Mexico","Malasya",
                "Nigeria","Netherlands","Norway","New Zealand","Philippines","Poland","Portugal","Romania","Serbia","Russia","Saudi Arabia",
                "Sweden","Singapore","Slovenia","Slovakia","Thailand","Turkey","Taiwan","Ukraine","United States","Venezuela","South Africa"});

        codes = Arrays.asList(new String[]{"","ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr",
                "hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa",
                "se","sg","si","sk","th","tr","tw","ua","us","ve","za"});

        int countryFlags [] = {R.drawable.all, R.drawable.united_arab_emirates, R.drawable.argentina, R.drawable.austria, R.drawable.australia, R.drawable.belgium, R.drawable.bulgaria,
                R.drawable.brazil, R.drawable.canada, R.drawable.switzerland, R.drawable.china, R.drawable.colombia, R.drawable.cuba,
                R.drawable.czechia, R.drawable.germany, R.drawable.egypt, R.drawable.france, R.drawable.uk, R.drawable.greece, R.drawable.hong_kong,
                R.drawable.hungary, R.drawable.indonesia, R.drawable.ireland, R.drawable.israel, R.drawable.india, R.drawable.italy, R.drawable.japan,
                R.drawable.korea, R.drawable.lithuania, R.drawable.latvia, R.drawable.morocco, R.drawable.mexico, R.drawable.malasya,
                R.drawable.nigeria, R.drawable.netherlands, R.drawable.norway, R.drawable.new_zealand, R.drawable.philipines, R.drawable.poland,
                R.drawable.portugal, R.drawable.romania, R.drawable.serbia, R.drawable.russia, R.drawable.saudi_arabia, R.drawable.sweden,
                R.drawable.singapore, R.drawable.slovenia, R.drawable.slovakia, R.drawable.thailand, R.drawable.turkey, R.drawable.taiwan,
                R.drawable.ukraine, R.drawable.usa, R.drawable.venezuela, R.drawable.south_africa};

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        currentCountry = findViewById(R.id.currentCountry);
        //currentCountry is the text we see in the screen with the current country, not to miss with countryLabel
        currentCountry.setText(RequestManager.countryLabel);
        CustomCountryAdapter countryAdapter = new CustomCountryAdapter(getApplicationContext(), countries, countryFlags);
        listView.setAdapter(countryAdapter);

        account = GoogleSignIn.getLastSignedInAccount(this);

        firebaseDatabase = FirebaseDatabase.getInstance("https://newsapp-808c0-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference(account.getId());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //country is the code that will be used the API call
                country = codes.get(position);
                databaseReference.child("country_code").setValue(country);
                databaseReference.child("country_label").setValue(countries.get(position));
                //countryLabel will be the text in currentCountry next time we enter this activity
                RequestManager.countryLabel = countries.get(position);
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }



}