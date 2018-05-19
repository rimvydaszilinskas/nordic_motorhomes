package com.example.nordicmotorhomes.models;

public class MandatoryCheck {
    // Class to hold the common variables for Check and Service

    private boolean lights;
    private boolean chassis;
    private boolean engine;
    private boolean interior;
    private boolean exterior;

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public void setLights(int lights) {
        this.lights = convert(lights);
    }

    public boolean isChassis() {
        return chassis;
    }

    public void setChassis(boolean chassis) {
        this.chassis = chassis;
    }

    public void setChassis(int chasis) {
        this.chassis = convert(chasis);
    }

    public boolean isEngine() {
        return engine;
    }

    public void setEngine(boolean engine) {
        this.engine = engine;
    }

    public void setEngine(int engine) {
        this.engine = convert(engine);
    }

    public boolean isInterior() {
        return interior;
    }

    public void setInterior(boolean interior) {
        this.interior = interior;
    }

    public void setInterior(int interior) {
        this.interior = convert(interior);
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public void setExterior(int exterior) {
        this.exterior = convert(exterior);
    }

    public int getLights(){
        if(isLights())
            return 1;
        return 0;
    }

    public int getChasis(){
        if(isChassis())
            return 1;
        return 0;
    }

    public int getEngine(){
        if(isEngine())
            return 1;
        return 0;
    }

    public int getInterrior(){
        if(isInterior())
            return 1;
        return 0;
    }

    public int getExterrior(){
        if(isExterior())
            return 1;
        return 0;
    }

    private boolean convert(int state){
        return state == 1;
    }
}
