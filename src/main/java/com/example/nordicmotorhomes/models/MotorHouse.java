package com.example.nordicmotorhomes.models;

public class MotorHouse {
    private int id;
    private String manufacturer;
    private String model;
    private int bed_count;
    private int seats;
    private int weight;
    private String description;
    private String gearbox;
    private int power;
    private int year;
    private int mileage;
    private String transmission;
    private double price;

    public MotorHouse(){}

    public MotorHouse(int id, String manufacturer, String model, int bed_count,
                      int seats, int weight, String description, String gearbox,
                      int year, int mileage, String transmission, int power, double price) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.bed_count = bed_count;
        this.seats = seats;
        this.weight = weight;
        this.description = description;
        this.gearbox = gearbox;
        this.year = year;
        this.mileage = mileage;
        this.transmission = transmission;
        this.power = power;
        this.price = price;
    }

    public MotorHouse(String manufacturer, String model, int bed_count,
                      int seats, int weight, String description, String gearbox,
                      int year, int mileage, String transmission, int power, double price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.bed_count = bed_count;
        this.seats = seats;
        this.weight = weight;
        this.description = description;
        this.gearbox = gearbox;
        this.year = year;
        this.mileage = mileage;
        this.transmission = transmission;
        this.power = power;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBed_count() {
        return bed_count;
    }

    public void setBed_count(int bed_count) {
        this.bed_count = bed_count;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
