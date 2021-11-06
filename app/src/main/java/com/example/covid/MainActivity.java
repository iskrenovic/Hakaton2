package com.example.covid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.covid.UI.HomeFragment;
import com.example.covid.UI.MapsFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MapsFragment mapsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, this.mapsFragment).commitAllowingStateLoss();
    }
}