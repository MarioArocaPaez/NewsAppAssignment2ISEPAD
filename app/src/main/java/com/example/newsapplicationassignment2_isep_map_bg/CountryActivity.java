package com.example.newsapplicationassignment2_isep_map_bg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    public TextView textView;
    public TextView currentCountry;
    public ListView listView;
    public static String country;
    private String messageCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        List<String> countries = Arrays.asList(new String[]{"United Arab Emirates","Argentina","Austria","Australia","Belgium","Bulgaria","Brazil",
                "Canada","Switzerland","China","Colombia","Cuba","Czechia","Germany","Egypt","France","United Kingdom","Greece","Hong Kong",
                "Hungary","Indonesia","Ireland","Israel","India","Italy","Japan","Korea","Lithuania","Latvia","Morocco","Mexico","Malasya",
                "Nigeria","Netherlands","Norway","New Zealand","Philippines","Poland","Portugal","Romania","Serbia","Russia","Saudi Arabia",
                "Sweden","Singapore","Slovenia","Slovakia","Thailand","Turkey","Taiwan","Ukraine","United States","Venezuela","South Africa"});

        List<String> codes = Arrays.asList(new String[]{"ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr",
                "hk","hu","id","ie","il","in","it","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa",
                "se","sg","si","sk","th","tr","tw","ua","us","ve","za"});

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        currentCountry = findViewById(R.id.currentCountry);

        currentCountry.setText(messageCountry);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_countries, countries);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                country = codes.get(position);
                messageCountry = countries.get(position);
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }



}