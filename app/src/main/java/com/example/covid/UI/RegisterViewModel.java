package com.example.covid.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private Callback callback;

    private MutableLiveData<String> name;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> password2;
    private MutableLiveData<String> username;

    public LiveData<String> getName() {return name;}
    public LiveData<String> getEmail() {return email;}
    public LiveData<String> getPassword() {return password;}
    public LiveData<String> getPassword2() {return password2;}
    public LiveData<String> getUsername() {return username;}

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public void nextStep(){
        if(canContinue()) callback.nextStep(name.toString(), email.toString(),password.toString(), username.toString());
    }

    private boolean canContinue(){
        if(name!=null && email!=null && password!=null  && password2!=null && username!=null) {
            return password == password2;
        }
        return false;
    }

    public interface Callback{
        void nextStep(String name, String email, String password, String username);
    }
}
