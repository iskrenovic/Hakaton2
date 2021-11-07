package com.example.covid.izgled;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.R;
import com.example.covid.Storage.ServerConnection;
import com.example.covid.databinding.MapsFragmentBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MapsFragment extends Fragment implements OnMapReadyCallback, ServerConnection.CubeDataCallback {
    private MapsFragmentBinding binding;
    private MapsViewModel viewModel;
    private static GoogleMap map;
    private MapView mapView;
    private ArrayList<Polygon> cubes = new ArrayList<>();
    private ArrayList<Coordinate> cubeCoordinate = new ArrayList<>();
    private boolean drawn = false;
    private static Location currentLocation;
    private JSONArray jsonArray;
    private static RequestQueue queue;

    @Override
    public void cubesReceived(JSONArray data) {
        try {
            paint(data.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cubesDataFailed() {
        Log.e("Error", "Not received");
    }

    private class Coordinate {
        public int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maps_fragment, container, false);

        currentLocation = new Location("");
        currentLocation.setLatitude(43.32);
        currentLocation.setLongitude(21.89);
        ServerConnection.setCubesCallback(this);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        return v;
    }

    private void clearPolygons() {
        for (Polygon p : cubes) {
            p.remove();
        }
        cubes.clear();
        drawn = false;
    }

    public static void setCurrentLocation(Location newLocation) {
        currentLocation.set(newLocation);
        FocusMapOnLocation();
    }

    private void setCubes(double dimension) {
        drawn = true;
        double size = (double) 1 / 1000;//Petina stepena

        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        int startX = (int) Math.floor(bounds.southwest.longitude / size);
        int endX = (int) Math.ceil(bounds.northeast.longitude / size);
        int startY = (int) Math.floor(bounds.southwest.latitude / size);
        int endY = (int) Math.ceil(bounds.northeast.latitude / size);

        jsonArray = new JSONArray();
        /*for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("longitude", i);
                    jsonObject.put("latitude", j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }*/
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("longitude",1);
            jsonObject.put("latitude",1);
            jsonArray.put(jsonObject);
            jsonObject= new JSONObject();
            jsonObject.put("longitude",11);
            jsonObject.put("latitude",1);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request="Melose: "+jsonArray.toString();
        ServerConnection.getCubes(getContext(),request);

        Random r= new Random();
        r.setSeed(7112021);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                int color=0x00ff0000;
                int transparency=r.nextInt(255);
                color+=transparency*256*256*256;
                Polygon p = map.addPolygon(new PolygonOptions()
                        .clickable(true)
                        .add(
                                new LatLng(j * size, i * size),
                                new LatLng((j + 1) * size, i * size),
                                new LatLng((j + 1) * size, (i + 1) * size),
                                new LatLng(j * size, (i + 1) * size)
                        )
                        .fillColor(color)
                        .strokeColor(R.color.transparent)
                        .strokeWidth(3));
                cubes.add(p);
            }
        }
    }

    public void paint(JSONObject data) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        LatLng locationLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.addMarker(new MarkerOptions().position(locationLatLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 10));
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(@NonNull CameraPosition cameraPosition) {
                clearPolygons();
                if (map.getCameraPosition().zoom >= 15) {
                    setCubes(map.getCameraPosition().zoom);
                } else if (map.getCameraPosition().zoom < 15) clearPolygons();
            }
        });
    }

    public static void FocusMapOnLocation() {
        if (currentLocation != null)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 10));
    }

    public static void setRequestQueue(Context cx) {
        queue = Volley.newRequestQueue(cx);
    }

}


