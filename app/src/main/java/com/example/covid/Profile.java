package com.example.covid;

import android.content.Context;
import android.location.Location;

import com.example.covid.Storage.LocalSave;

public class Profile {
    private static final String PROFILE_FILE_NAME = "profile";

    private String name;
    private String username;
    private String password;
    private String mail;
    private String jmbg;
    private Location location;

    public Profile(String username, String name, String password, String mail){
        this.username = username;
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public Profile(String mail, String name, String jmbg, Location location){
        this.mail = mail;
        this.name = name;
        this.jmbg = jmbg;
        this.location = location;
    }

    public Profile(String mail, String name, Location location){
        this.mail = mail;
        this.name = name;
        this.location = location;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String toString(){
        String output = "";
        output+=mail + "\n";
        output+=name + "\n";
        output+=location.toString() + "\n";
        return output;
    }

    public void saveProfile(Context cx){
        //Cuvanje online
        LocalSave.saveToFile(cx,PROFILE_FILE_NAME,toString());
    }
}
