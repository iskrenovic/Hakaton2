package com.example.covid.UI;

import android.location.Location;
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
import com.example.covid.databinding.RegisterSecondFragmentBinding;

public class RegisterSecondFragment extends Fragment implements RegisterSecondViewModel.Callback {

    private RegisterSecondFragmentBinding binding;
    private RegisterSecondViewModel viewModel;

    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_second_fragment, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(RegisterSecondViewModel.class);
        viewModel.setCallback(this);
        binding.setVm(viewModel);
        return this.binding.getRoot();
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public void nextStep(String jmbg, Location location) {
        callback.nextStep(jmbg, location);
    }


    public interface Callback{
        void nextStep(String jmbg, Location location);
    }

}
