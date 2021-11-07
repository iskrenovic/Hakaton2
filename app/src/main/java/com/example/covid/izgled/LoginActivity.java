package com.example.covid.izgled;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid.Profile;
import com.example.covid.R;
import com.example.covid.Storage.ServerConnection;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements RegisterFragment.Callback, RegisterSecondFragment.Callback , LoginFragment.Callback, ServerConnection.LoginCallback{
    private RegisterFragment registerFragment;
    private RegisterSecondFragment registerSecondFragment;

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerFragment = new RegisterFragment();
        registerFragment.setCallback(this);

        registerSecondFragment = new RegisterSecondFragment();
        registerSecondFragment.setCallback(this);

        ServerConnection.setLoginCallback(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.registerFragment).commitAllowingStateLoss();

    }

    @Override
    public void nextStep() {

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,this.registerSecondFragment).commitAllowingStateLoss();

    }

    @Override
    public void nextStep(String jmbg, Location location) {
        Profile.jmbg = jmbg;
        Profile.location = location;
        ServerConnection.register(this);
    }

    @Override
    public void login(String username, String password) {
        //Zovem melosev login
        ServerConnection.login(this, username, password);
    }

    @Override
    public void loginSuccess(JSONObject object) {
        Toast.makeText(this,object.toString(), Toast.LENGTH_LONG);
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"Ispusih ga", Toast.LENGTH_LONG);
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFailed() {

    }
}
