package com.example.nordicmotorhomes.models;

import java.time.LocalDate;

public class Service extends MandatoryCheck{
    int id;
    int motorhouseID;
    LocalDate dateFrom;
    LocalDate dateTo;
    String description;
    double amount;

    public Service(){}

    public Service(int id, int motorhouseID, LocalDate dateFrom, LocalDate dateTo, String description, boolean lights, boolean chassis, boolean engine, boolean interior, boolean exterior, double amount) {
        this.id = id;
        this.motorhouseID = motorhouseID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.amount = amount;
    }

    public Service(LocalDate dateFrom, int motorhouseID, LocalDate dateTo, String description, boolean lights, boolean chassis, boolean engine, boolean interior, boolean exterior, double amount) {
        this.motorhouseID = motorhouseID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.amount = amount;
    }

    public Service(int id, int motorhouseID, String dateFrom, String dateTo, String description, int lights, int chassis, int engine, int interior, int exterior, double amount) {
        this.id = id;
        this.motorhouseID = motorhouseID;
        setDateFrom(dateFrom);
        if(dateTo != null && !dateTo.equals(""))
            setDateTo(dateTo);
        this.description = description;
        super.setLights(lights);
        super.setChassis(chassis);
        super.setEngine(engine);
        super.setInterior(interior);
        super.setExterior(exterior);
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = stringToDate(dateFrom);
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = stringToDate(dateTo);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMotorhouseID() {
        return motorhouseID;
    }

    public void setMotorhouseID(int motorhouseID) {
        this.motorhouseID = motorhouseID;
    }

    private LocalDate stringToDate(String date){
        String[] dateSplit = date.split("-");
        return LocalDate.of(Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
    }
}
