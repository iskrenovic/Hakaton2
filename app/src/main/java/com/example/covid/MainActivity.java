package com.example.covid;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.covid.Notifications.NotificationCenter;
import com.example.covid.Storage.ServerConnection;

public class MainActivity extends AppCompatActivity implements ServerConnection.ConnectionCallback {

    private static final String URL = "http://159.89.18.129/api/";

    private HomeFragment homeFragment;
    private MapsFragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCenter.createNotificationChannels(this);
        NotificationCenter.scheduleDailyChallengeNotification(this);

        homeFragment = new HomeFragment();
        mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.homeFragment).commitAllowingStateLoss();

        ServerConnection.setRequestQueue(this);
        ServerConnection.setCallback(this);
        ServerConnection.requestUserData();
    }

    @Override
    public void userDataReceived(String data) {
        homeFragment.DisplayMessege(data);
    }

    @Override
    public void userDataFailed() {
        homeFragment.DisplayMessege("fail");
    }

    @Override
    public void locationDataReceived(String data) {

    }

    @Override
    public void locationDataFailed() {

    }
}