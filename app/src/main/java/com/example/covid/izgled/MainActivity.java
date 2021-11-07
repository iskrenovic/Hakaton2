package com.example.covid.izgled;


import android.content.ComponentName;
import android.content.ServiceConnection;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid.Location.LocationHandler;
import com.example.covid.Notifications.NotificationCenter;
import com.example.covid.R;
import com.example.covid.Storage.ServerConnection;
import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements ServerConnection.DataCallback {

    private static final String URL = "http://159.89.18.129/api/";

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private int fetchLocationMinutes = 20;
    private LocationHandler locationHandler;

    private HomeFragment homeFragment;
    private MapsFragment mapsFragment;
    private RegisterSecondFragment registerSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCenter.createNotificationChannels(this);
        NotificationCenter.scheduleDailyChallengeNotification(this);

        homeFragment = new HomeFragment();
        mapsFragment = new MapsFragment();
        registerSecondFragment=new RegisterSecondFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.registerSecondFragment).commitAllowingStateLoss();

        ServerConnection.setRequestQueue(this);
        ServerConnection.setDataCallback(this);
        ServerConnection.requestUserData();
    }

    @Override
    public void userDataReceived(String data) {
        homeFragment.DisplayMessage(data);
    }

    @Override
    public void userDataFailed() {
        homeFragment.DisplayMessage("fail");
    }

    @Override
    public void locationDataReceived(String data) {

    }

    @Override
    public void locationDataFailed() {

    }
    private ServiceConnection locationServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            locationHandler=((LocationHandler.LocationHandlerBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationHandler=null;
        }
    };
}