package com.example.covid;

import android.content.Context;

import com.example.covid.Storage.LocalSave;

public class Profile {
    private static final String PROFILE_FILE_NAME = "profile";

    private String name;
    private String username;
    private String mail;
    private String jmbg;
    private double lat, lon;

    public Profile(String mail, String name, String jmbg, double lat, double lon){
        this.mail = mail;
        this.name = name;
        this.jmbg = jmbg;
        this.lat = lat;
        this.lon = lon;
    }

    public Profile(String mail, String name, double lat, double lon){
        this.mail = mail;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String toString(){
        String output = "";
        output+=mail + "\n";
        output+=name + "\n";
        output+=Double.toString(lat) + "\n";
        output+=Double.toString(lon) + "\n";
        return output;
    }

    public void saveProfile(Context cx){
        //Cuvanje online
        LocalSave.saveToFile(cx,PROFILE_FILE_NAME,toString());
    }
}
