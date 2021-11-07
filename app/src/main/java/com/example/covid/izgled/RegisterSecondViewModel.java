package com.example.covid.izgled;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterSecondViewModel extends ViewModel {

    private Callback callback;

    private MutableLiveData<String> jmbg;
    private Location location;

    public LiveData<String> getJMBG() {
        return jmbg;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void nextStep() {
        if (canContinue()) callback.nextStep(jmbg.toString(), location);
    }

    private boolean canContinue() {
        return  jmbg != null && location != null;
    }

    public interface Callback {
        void nextStep(String jmbg, Location location);
    }

}
