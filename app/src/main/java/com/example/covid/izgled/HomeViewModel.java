package com.example.covid.izgled;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> text;
    public HomeViewModel(){
        text = new MutableLiveData<>();
    }

    public LiveData<String> getText(){
        return text;
    }
    public void setText(String s){
        text = new MutableLiveData<>(s);
    }
}
