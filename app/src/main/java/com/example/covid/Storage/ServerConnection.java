package com.example.covid.Storage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ServerConnection {
    private static final String URL = "http://159.89.18.129/api/";
    private static RequestQueue queue;

    private static ConnectionCallback callback;

    public static void setRequestQueue(Context cx){
        queue = Volley.newRequestQueue(cx);
    }

    public static void setCallback(ConnectionCallback cb){
        callback = cb;
    }

    public static Response.Listener<String> userListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            callback.userDataReceived(response);
        }
    };

    public static Response.ErrorListener userErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            callback.userDataFailed();
        }
    };


    public static void requestUserData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "user", userListener, userErrorListener);
        queue.add(stringRequest);
    }

    public void requestLocationData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "location",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.locationDataReceived(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.locationDataFailed();
                    }
                });
    }

    public interface ConnectionCallback{
        void userDataReceived(String data);
        void userDataFailed();
        void locationDataReceived(String data);
        void locationDataFailed();
    }
}
