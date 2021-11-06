package com.example.covid;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.example.covid.databinding.MapsFragmentBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.URL;
import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private MapsFragmentBinding binding;
    private MapsViewModel viewModel;
    private GoogleMap map;
    private MapView mapView;
    private ArrayList<Polygon> cubes=new ArrayList<>();
    private boolean drawn=false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maps_fragment, container, false);

        SupportMapFragment supportMapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        return v;
    }
    private void clearPolygons(){
        for(Polygon p:cubes){
            p.remove();
        }
        cubes.clear();
        drawn=false;
    }
    private void setCubes(double dimension){
        drawn=true;
        double size=(double)1/1000;//Petina stepena

        LatLngBounds bounds=map.getProjection().getVisibleRegion().latLngBounds;
        int startX=(int)Math.floor(bounds.southwest.longitude/size);
        int endX=(int)Math.ceil(bounds.northeast.longitude/size);
        int startY=(int)Math.floor(bounds.southwest.latitude/size);
        int endY=(int)Math.ceil(bounds.northeast.latitude/size);


        for(int i=startX;i<=endX;i++){
            for(int j=startY;j<=endY;j++){
                Polygon p= map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                     new LatLng(j*size,i*size),
                     new LatLng((j+1)*size,i*size),
                     new LatLng((j+1)*size,(i+1)*size),
                     new LatLng(j*size,(i+1)*size)
                )
                .fillColor(R.color.purple_700)
                .strokeColor(R.color.transparent));
                cubes.add(p);
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.addMarker(new MarkerOptions().position(new LatLng(45.1,37.5)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.1,37.5), 10));
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(@NonNull CameraPosition cameraPosition) {
                clearPolygons();
                if(map.getCameraPosition().zoom>=15){
                    setCubes(map.getCameraPosition().zoom);
                }
                else if(map.getCameraPosition().zoom<15) clearPolygons();
            }
        });
    }
}
