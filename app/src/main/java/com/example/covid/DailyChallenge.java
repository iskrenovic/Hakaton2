package com.example.covid;

import android.content.Context;

import com.example.covid.Storage.LocalSave;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.Date;

public class DailyChallenge {

    private static final String DAILY_CHALLENGE_SAVE_FILE_NAME = "dailyc";

    private Challenge c1;
    private Challenge c2;
    private Challenge c3;

    private static Date date; // Datum kada su postavljeni zadaci

    public DailyChallenge(Context cx){
        String current = LocalSave.readFromFile(cx, DAILY_CHALLENGE_SAVE_FILE_NAME);
        if(current!=null){
            convertToDailyChallenge(cx,current);
            if(isNextDay(date, Calendar.getInstance().getTime())){
                setNewChallenges(cx);
            }
        }
        else{
            setNewChallenges(cx);
        }
        c1.isDone();
        c2.isDone();
        c3.isDone();
    }

    private void setNewChallenges(Context cx){
        date = Calendar.getInstance().getTime();
        c1 = new Challenge(cx.getString(R.string.challenge1_text),0, 3);
        c2 = new Challenge(cx.getString(R.string.challenge2_text),0, 1);
        c3 = new Challenge(cx.getString(R.string.challenge3_text),0, 1);
    }

    private void convertToDailyChallenge(Context cx, String s){
        int ind = s.indexOf("\n");
        date = new Date(s.substring(0, ind));
        c1 = new Challenge(cx.getString(R.string.challenge1_text),Integer.getInteger(s.substring(0,ind)),3);

        s = s.substring(ind + 1);
        ind = s.indexOf("\n");
        c2 = new Challenge(cx.getString(R.string.challenge2_text),Integer.getInteger(s.substring(0,ind)),1);

        s = s.substring(ind + 1);
        ind = s.indexOf("\n");
        c3 = new Challenge(cx.getString(R.string.challenge3_text),Integer.getInteger(s.substring(0,ind)),1);
    }

    private String convertToString(){
        String output = "";
        output+=date.toString() + "\n";
        output+= c1.toString() + "\n";
        output+= c2.toString() + "\n";
        output += c3.toString() + "\n";
        return output;
    }

    private static boolean isNextDay(Date saved, Date current){
        long diff = current.getTime() - saved.getTime();
        return diff / (1000 * 60*60*60*24) != 0;
    }

    private void addToChallenge(int challengeId){
        switch (challengeId){
            case 1:
                if(!c1.isDone()) {
                    c1.doChallenge();
                    if(c1.isDone()) giveAward();
                }
                break;
            case 2:
                if(!c2.isDone()) {
                    c2.doChallenge();
                    if(c2.isDone()) giveAward();
                }
                break;
            case 3:
                if(!c3.isDone()) {
                    c3.doChallenge();
                    if(c3.isDone()) giveAward();
                }

                break;
        }
    }

    private void giveAward(){
        //Daj nagradu ovde
    }
}
