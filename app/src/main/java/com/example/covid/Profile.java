package com.example.covid;

import android.content.Context;
import android.location.Location;

import com.example.covid.Storage.LocalSave;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {
    private static final String PROFILE_FILE_NAME = "profile";
    public static String name;
    public static String username;
    public static String password;
    public static String mail;
    public static String jmbg;
    public static Location location;

    public String toString(){
        String output = "";
        output+=mail + "\n";
        output+=name + "\n";
        output+=location.toString() + "\n";
        return output;
    }

    public static JSONObject toJSONObject() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("document_id",jmbg);
        data.put("username", username);
        data.put("name",name);
        data.put("email",mail);
        data.put("adress", String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()));
        data.put("password", password);
        return data;
    }

    public void saveProfile(Context cx){
        //Cuvanje online
        LocalSave.saveToFile(cx,PROFILE_FILE_NAME,toString());
    }
}
