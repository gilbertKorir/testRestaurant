package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mFindRestaurantsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFindRestaurantsButton = (Button)findViewById(R.id.findRestaurantsButton);
        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
            }
        });
    }

}