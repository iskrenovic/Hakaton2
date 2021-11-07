package com.example.covid.izgled;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterSecondViewModel extends ViewModel {

    private Callback callback;

    private MutableLiveData<String> jmbg;
    private MutableLiveData<String> username;

    public RegisterSecondViewModel(){
        jmbg = new MutableLiveData<>();
        username = new MutableLiveData<>();
    }

    public void jmbgChanged(Editable editable) {
        jmbg.setValue(editable.toString());
    }

    public void usernameChanged(Editable editable) {
        username.setValue(editable.toString());
    }

    public LiveData<String> getJMBG() {
        return jmbg;
    }

    public LiveData<String> getUsername() {return username;}

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void nextStep() {
        if (canContinue()) callback.nextStep(jmbg.toString(), username.toString());
    }

    private boolean canContinue() {
        return  jmbg.getValue() != null && username.getValue()!=null;
    }

    public interface Callback {
        void nextStep(String jmbg,String username);
    }

}
