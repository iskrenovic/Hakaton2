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
import com.example.covid.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setVm(viewModel);
        return binding.getRoot();
    }

    public void DisplayMessege(String mess){
        if(viewModel!=null) viewModel.setText(mess);
    }
}
