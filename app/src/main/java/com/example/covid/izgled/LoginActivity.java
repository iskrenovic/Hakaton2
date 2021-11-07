package com.example.covid.izgled;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid.Profile;
import com.example.covid.R;
import com.example.covid.Storage.LocalSave;
import com.example.covid.Storage.ServerConnection;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements RegisterFragment.Callback, RegisterSecondFragment.Callback , LoginFragment.Callback, ServerConnection.LoginCallback{

    private static final String USER_FILE_NAME = "user_local_data";

    private RegisterFragment registerFragment;
    private RegisterSecondFragment registerSecondFragment;

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String read = LocalSave.readFromFile(this,USER_FILE_NAME);
        if(read!=null){
            if(Boolean.getBoolean(read)==true) openNextAcitivity();
        }


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
    public void nextStep(String jmbg,String username, Location location) {
        Profile.username = username;
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
        openNextAcitivity();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"Ne radi", Toast.LENGTH_LONG);
    }

    @Override
    public void registerSuccess() {
        openNextAcitivity();
    }

    @Override
    public void registerFailed() {

    }

    private void openNextAcitivity() {
        LocalSave.saveToFile(this,USER_FILE_NAME,"");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
