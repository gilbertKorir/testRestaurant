package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class RestaurantsActivity extends AppCompatActivity {
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the restaurants near: " + location);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String restaurant = ((TextView)view).getText().toString();
                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();

                YelpApi client = YelpClient.getClient();
                Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");

                call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
                    @Override
                    public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<Business> restaurantsList = response.body().getBusinesses();
                            String[] restaurants = new String[restaurantsList.size()];
                            String[] categories = new String[restaurantsList.size()];

                            for (int i = 0; i < restaurants.length; i++){
                                restaurants[i] = restaurantsList.get(i).getName();
                            }

                            for (int i = 0; i < categories.length; i++) {
                                Category category = restaurantsList.get(i).getCategories().get(0);
                                categories[i] = category.getTitle();
                            }

                            ArrayAdapter adapter
                                    = new MyRestaurantsArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
                            mListView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onResponse(Call<YelpBusinessesSearchResponse> call, retrofit2.Response<YelpBusinessesSearchResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {

                    }

                });
            }
        });
    }
}