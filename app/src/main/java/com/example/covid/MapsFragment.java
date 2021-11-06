package com.example.covid;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.covid.databinding.MapsFragmentBinding;

public class MapsFragment extends Fragment {
    private MapsFragmentBinding binding;
    private MapsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.maps_fragment,container, false);
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        return binding.getRoot();
    }
}
