package com.example.covid.izgled;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private Callback callback;

    private MutableLiveData<String> username;
    private MutableLiveData<String> password;

    public LiveData<String> getUsername() {return username;}
    public LiveData<String> getPassword() {return password;}

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public interface Callback{
        void login(String username, String password);
    }

}
