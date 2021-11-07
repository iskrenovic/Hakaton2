package com.example.covid.izgled;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid.Profile;
import com.example.covid.R;

public class LoginActivity extends AppCompatActivity implements RegisterFragment.Callback, RegisterSecondFragment.Callback {
    private RegisterFragment registerFragment;
    private RegisterSecondFragment registerSecondFragment;

    private LoginFragment loginFragment;

    private Profile profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerFragment = new RegisterFragment();
        registerFragment.setCallback(this);

        registerSecondFragment = new RegisterSecondFragment();
        registerSecondFragment.setCallback(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.registerFragment).commitAllowingStateLoss();
    }

    @Override
    public void nextStep(Profile profile) {
        this.profile = profile;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.registerSecondFragment).commitAllowingStateLoss();

    }

    @Override
    public void nextStep(String jmbg, Location location) {
        profile.setJmbg(jmbg);
        profile.setLocation(location);
    }
}
