package com.example.covid.Storage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerConnection {
    private static final String URL = "http://159.89.18.129/api/";
    private static RequestQueue queue;

    private static DataCallback dataCallback;
    private static LoginCallback loginCallback;
    private static CubeDataCallback cubeDataCallback;

    public static void setRequestQueue(Context cx){
        queue = Volley.newRequestQueue(cx);
    }
    public static String getUrl() {
        return  URL;
    }

    public static void setDataCallback(DataCallback cb){
        dataCallback = cb;
    }

    public static void setLoginCallback(LoginCallback cb){
        loginCallback = cb;
    }
    public static void setCubesCallback(CubeDataCallback cdc){
        cubeDataCallback=cdc;
    }


    public static void requestUserData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "user", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataCallback.userDataReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataCallback.userDataFailed();
            }
        });
        queue.add(stringRequest);
    }

    public void requestLocationData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "location",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataCallback.locationDataReceived(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataCallback.locationDataFailed();
                    }
                });
    }

    public static void login(Context cx, String username, String password){
        if(queue == null){
            setRequestQueue(cx);
        }
        JSONObject data = new JSONObject();
        try{
            data.put("username", username);
            data.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL + "login", data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loginCallback.loginSuccess(response);
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    loginCallback.loginFailed();
                }
            });
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void register(Context cx){
        if(queue==null)
            setRequestQueue(cx);

        JSONObject data = new JSONObject();
        try {
            data = Profile.toJSONObject();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL + "register", data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loginCallback.registerSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loginCallback.registerFailed();
                }
            });
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void getCubes(Context cx, JSONArray jsonArray){
        if(queue == null){
            setRequestQueue(cx);
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL + "location/manylocations", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cubeDataCallback.cubesReceived(response);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                cubeDataCallback.cubesDataFailed();
            }
        });
        queue.add(request);

    }

    public interface DataCallback {
        void userDataReceived(String data);
        void userDataFailed();
        void locationDataReceived(String data);
        void locationDataFailed();
    }

    public interface LoginCallback{
        void loginSuccess(JSONObject object);
        void loginFailed();
        void registerSuccess();
        void registerFailed();
    }
    public interface CubeDataCallback{
        void cubesReceived(JSONArray data);
        void cubesDataFailed();
    }
}
