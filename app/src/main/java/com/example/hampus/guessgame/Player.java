package com.example.hampus.guessgame;

/**
 * Created by hampus on 2017-04-21.
 */

public class Player {
    private String name;
    private int points;
    public Player(String name){
        this.name=name;
        this.points=0;
    }

    public String getName(){
        return this.name;
    }
    public void addPoint(){
        this.points++;
    }
    public int getPoints(){
        return this.points;
    }

}
