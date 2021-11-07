package com.example.covid.Storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
        try {
            String output = "";
            Scanner scanner = new Scanner(new File(DIRECTORY_NAME + "/fileName"));
            do{
                output+= scanner.nextLine();
                output+="\n";
            }while(scanner.hasNext());
            scanner.close();
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
