package com.example.covid.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.covid.Profile;
import com.example.covid.R;
import com.example.covid.databinding.RegisterFragmentBinding;

public class RegisterFragment extends Fragment implements RegisterViewModel.Callback{

    private RegisterFragmentBinding binding;
    private RegisterViewModel viewModel;

    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_fragment, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        viewModel.setCallback(this);
        binding.setVm(viewModel);
        return this.binding.getRoot();
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public void nextStep(String name, String email, String password, String username) {
        callback.nextStep(new Profile(username, name, email, password));
    }

    public interface Callback{
        void nextStep(Profile profile);
    }
}
