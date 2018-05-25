package com.example.nordicmotorhomes.models;

import java.time.LocalDate;

public class Pickup {
    private int id;
    private LocalDate date;
    private double fuel;
    private int reservationID;
    private int mileage;

    public Pickup(int id, String date, double fuel, int reservationID, int mileage) {
        this.id = id;
        setDate(date);
        this.fuel = fuel;
        this.reservationID = reservationID;
        this.mileage = mileage;
    }

    public Pickup(int id, LocalDate date, double fuel, int reservationID, int mileage) {
        this.id = id;
        this.date = date;
        this.fuel = fuel;
        this.reservationID = reservationID;
        this.mileage = mileage;
    }

    public Pickup(LocalDate date, double fuel, int reservationID, int mileage) {
        this.date = date;
        this.fuel = fuel;
        this.reservationID = reservationID;
        this.mileage = mileage;
    }

    public Pickup(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) {
        String[] dateSplit = date.split("-");
        this.date = LocalDate.of(Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
