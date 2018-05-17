package com.example.nordicmotorhomes.models;

public class Delivery {
    int id;
    String address;
    double distance;

    public Delivery(int id, String address, double distance) {
        this.address = address;
        this.distance = distance;
    }

    public Delivery(double distance){
        this.distance = distance;
    }

    public Delivery(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
