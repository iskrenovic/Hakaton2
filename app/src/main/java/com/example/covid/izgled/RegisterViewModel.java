package com.example.covid.izgled;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private Callback callback;

    private MutableLiveData<String> name;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> password2;

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public LiveData<String> getPassword2() {
        return password2;
    }

    public RegisterViewModel() {
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        password2 = new MutableLiveData<>();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void emailChanged(Editable editable) {
        email.setValue(editable.toString());
    }

    public void nameChanged(Editable editable) {

        name.setValue(editable.toString());

    }

    public void passwordChanged(Editable editable) {

        password.setValue(editable.toString());

    }

    public void password2Changed(Editable editable) {

        password2.setValue(editable.toString());

    }

    public void nextStep() {
        if (canContinue())
            callback.nextStep(name.toString(), email.toString(), password.toString());
    }

    private boolean canContinue() {
        if (name.getValue() != null && email.getValue() != null && password.getValue() != null && password2.getValue() != null) {
            return password.getValue().equals(password2.getValue());
        }
        return false;
    }

    public interface Callback {
        void nextStep(String name, String email, String password);
    }
}
