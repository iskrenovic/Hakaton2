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

import com.example.covid.Game.DailyChallenge;
import com.example.covid.R;
import com.example.covid.Storage.ServerConnection;
import com.example.covid.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment implements HomeViewModel.Callback {

    private HomeFragmentBinding binding;
    private HomeViewModel viewModel;

    private DailyChallenge dailyChallenge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setVm(viewModel);
        viewModel.setCallback(this);

        dailyChallenge = new DailyChallenge(getContext());
        return binding.getRoot();
    }

    @Override
    public void challengeClick(int ch) {
        dailyChallenge.addToChallenge(ch);
    }

    @Override
    public void pozitivan() {
        ServerConnection.iAmInfeceted();
    }

    @Override
    public void vakcinisan() {

    }
}
