package com.example.covid;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid.UI.HomeFragment;
import com.example.covid.UI.MapsFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MapsFragment mapsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapsFragment = new MapsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, this.mapsFragment).commitAllowingStateLoss();
    }
}