package com.example.covid.izgled;

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

import com.example.covid.R;
import com.example.covid.databinding.RegisterSecondFragmentBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RegisterSecondFragment extends Fragment implements RegisterSecondViewModel.Callback, OnMapReadyCallback {

    private RegisterSecondFragmentBinding binding;
    private RegisterSecondViewModel viewModel;
    private GoogleMap map;
    private Callback callback;
    private Location userLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_second_fragment, container, false);
        binding.setLifecycleOwner(this);

        SupportMapFragment supportMapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapSelect);
        supportMapFragment.getMapAsync(this);

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        final LatLng[] clickLatLng = new LatLng[1];
        map=googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                clickLatLng[0] =latLng;
                userLocation=new Location("");
                userLocation.setLongitude(clickLatLng[0].longitude);
                userLocation.setLatitude(clickLatLng[0].latitude);
                map.clear();
                map.addMarker(new MarkerOptions().position(clickLatLng[0]));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(clickLatLng[0],map.getCameraPosition().zoom));
            }
        });

    }


    public interface Callback{
        void nextStep(String jmbg, Location location);
    }

}
