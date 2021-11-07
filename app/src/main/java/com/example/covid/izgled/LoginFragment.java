package com.example.covid.izgled;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.covid.R;
import com.example.covid.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment implements  LoginViewModel.Callback{

    private LoginFragmentBinding binding;
    private LoginViewModel viewModel;

    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.setCallback(this);
        binding.setVm(viewModel);
        return binding.getRoot();
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public void login(String username, String password) {
        callback.login(username, password);
    }

    public interface Callback{
        void login(String username, String password);
    }
}
