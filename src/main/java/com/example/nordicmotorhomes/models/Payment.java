package com.example.nordicmotorhomes.models;

import java.time.LocalDate;

public class Payment {
    private int id;
    private double ammount;
    private int reservation_id;
    private LocalDate date;

    public Payment(){}

    public Payment(double ammount, int reservation_id, LocalDate date){
        this.ammount = ammount;
        this.reservation_id = reservation_id;
        this.date = date;
    }

    public Payment(int id, double ammount, int reservation_id, LocalDate date) {
        this.id = id;
        this.ammount = ammount;
        this.reservation_id = reservation_id;
        this.date = date;
    }

    public Payment(int id, double ammount, int reservation_id, String date) {
        this.id = id;
        this.ammount = ammount;
        this.reservation_id = reservation_id;
        String[] dateSplit = date.split("-");
        this.date = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
