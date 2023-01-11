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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CountryActivity extends AppCompatActivity {

    public TextView textView;
    public TextView currentCountry;
    public ListView listView;
    public static String country;
    public static List<String> countries;
    public static List<String> codes;

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

        currentCountry.setText(RequestManager.countryLabel);
        CustomBaseAdapter countryAdapter = new CustomBaseAdapter(getApplicationContext(), countries, countryFlags);
        listView.setAdapter(countryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                country = codes.get(position);
                RequestManager.countryLabel = countries.get(position);
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }



}