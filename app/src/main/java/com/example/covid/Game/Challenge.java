package com.example.covid.Game;

public class Challenge {
    private String text;
    private int current;
    private int target;
    private boolean done;

    public Challenge(String text, int current, int target){
        this.text = text;
        this.current = current;
        this.target = target;
    }

    public String toString(){
        return Integer.toString(current);
    }

    public boolean isDone() {
        return done = current>=target;
    }

    public void doChallenge(){
        ++current;
    }
}
