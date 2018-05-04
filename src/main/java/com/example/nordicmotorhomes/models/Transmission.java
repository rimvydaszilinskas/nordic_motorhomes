package com.example.nordicmotorhomes.models;

public class Transmission {
    private String name;
    private String shortName;

    public Transmission(String name){
        setName(name);
        this.shortName = name;
    }

    public void setName(String shortName){
        if(shortName.equals("default")){
            this.name = "default";
        } else if(shortName.equals("AWD")){
            this.name = "All Wheel Drive";
        } else if(shortName.equals("RWD")){
            this.name = "Rear Wheel Drive";
        } else {
            this.name = "Front Wheel Drive";
        }
    }

    public String getName(){
        return name;
    }

    public String getShortName(){
        return shortName;
    }
}
