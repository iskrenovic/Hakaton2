package com.example.covid.Storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LocalSave {

    private static final String DIRECTORY_NAME = "localSave";
    public static void saveToFile(Context cx, String fileName, String content){
        File dir = new File(cx.getFilesDir(),DIRECTORY_NAME);
        if(!dir.exists()){
            dir.mkdir();
        }
        try{
            File file = new File(dir, fileName);
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(Context cx, String fileName) {
        File file = new File(cx.getFilesDir(), DIRECTORY_NAME + "/" + fileName);
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line!=null){
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
}
