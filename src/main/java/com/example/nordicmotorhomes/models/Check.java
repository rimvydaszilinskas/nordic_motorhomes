package com.example.nordicmotorhomes.models;

import java.time.LocalDate;

public class Check extends MandatoryCheck{
    private int id;
    private int reservationID;
    private LocalDate date;
    private String notes;

    public Check(){}

    public Check(int reservationID, LocalDate date, boolean lights, boolean chassis, boolean engine, boolean interior, boolean exterior, String notes) {
        this.reservationID = reservationID;
        this.date = date;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.notes = notes;
    }

    public Check(int id, int reservationID, LocalDate date, boolean lights, boolean chassis, boolean engine, boolean interior, boolean exterior, String notes) {
        this.id = id;
        this.reservationID = reservationID;
        this.date = date;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.notes = notes;
    }

    public Check(int id, int reservationID, LocalDate date, int lights, int chassis, int engine, int interior, int exterior, String notes) {
        this.id = id;
        this.reservationID = reservationID;
        this.date = date;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.notes = notes;
    }

    public Check(int id, int reservationID, String date, boolean lights, boolean chassis, boolean engine, boolean interior, boolean exterior, String notes) {
        this.id = id;
        this.reservationID = reservationID;
        setDate(date);
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.notes = notes;
    }

    public Check(int id, int reservationID, String date, int lights, int chassis, int engine, int interior, int exterior, String notes) {
        this.id = id;
        this.reservationID = reservationID;
        setDate(date);
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) {
        String[] datesplit = date.split("-");

        this.date = LocalDate.of(Integer.parseInt(datesplit[0]),
                Integer.parseInt(datesplit[1]),
                Integer.parseInt(datesplit[2]));
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
