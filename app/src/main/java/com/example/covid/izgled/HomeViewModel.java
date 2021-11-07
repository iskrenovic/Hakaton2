package com.example.covid.izgled;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid.Game.Challenge;

import java.util.Date;

public class HomeViewModel extends ViewModel {

    private Callback callback;
    private MutableLiveData<String> datumVakcinacije;
    private MutableLiveData<Integer> dozaVakcinacije;

    private MutableLiveData<String> zad1;
    private MutableLiveData<String> zad2;
    private MutableLiveData<String> zad3;
    public HomeViewModel(){
        datumVakcinacije = new MutableLiveData<>();
        dozaVakcinacije = new MutableLiveData<>();
        zad1 = new MutableLiveData<>();
        zad2 = new MutableLiveData<>();
        zad3 = new MutableLiveData<>();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void datumChanged(Editable editable){
        datumVakcinacije.setValue(editable.toString());
    }

    public void dozaChanged(Editable editable){
        dozaVakcinacije.setValue(Integer.getInteger(editable.toString()));
    }

    public LiveData<String> getZad1(){
        return zad1;
    }
    public LiveData<String> getZad2(){
        return zad2;
    }
    public LiveData<String> getZad3(){
        return zad3;
    }

    public void clickZad1(){
        callback.challengeClick(1);
    }

    public void clickZad2(){
        callback.challengeClick(2);
    }

    public void clickZad3(){
        callback.challengeClick(3);
    }

    public void pozitivan(){
        callback.pozitivan();
    }

    public void vakcinacisan(){
        callback.vakcinisan();
    }

    public interface Callback{
        void challengeClick(int ch);
        void pozitivan();
        void vakcinisan();
    }
}
