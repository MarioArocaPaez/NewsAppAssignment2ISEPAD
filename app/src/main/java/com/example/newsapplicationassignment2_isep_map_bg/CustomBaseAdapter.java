package com.example.newsapplicationassignment2_isep_map_bg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<String> countries;
    int countryFlags[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, List<String> countries, int[] countryFlags){
        this.context = ctx;
        this.countries = countries;
        this.countryFlags = countryFlags;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_country_list_view, null);
        TextView textView = (TextView) convertView.findViewById(R.id.countryName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.countryFlag);
        textView.setText(countries.get(position));
        if (position <= 19){
            imageView.setImageResource(countryFlags[position]);
        } else{
            imageView.setImageResource(R.drawable.all);
        }

        return convertView;
    }
}
