package com.example.nordicmotorhomes.models;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDate bookingDate;
    private String status;
    private int customerID;
    private String customerName;
    private int motorhouseID;
    private String motorhouseName;

    public Reservation(){}

    public Reservation(int id){
        this.id = id;
    }

    public Reservation(int id, LocalDate dateFrom, LocalDate dateTo, LocalDate bookingDate, String status, int customerID, String customerName, int motorhouseID, String motorhouseName) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.bookingDate = bookingDate;
        this.status = status;
        this.customerID = customerID;
        this.customerName = customerName;
        this.motorhouseID = motorhouseID;
        this.motorhouseName = motorhouseName;
    }

    public Reservation(int id, String dateFrom, String dateTo, String bookingDate, String status, int customerID, String customerName, int motorhouseID, String motorhouseName) {
        this.id = id;
        setDateFrom(dateFrom);
        setDateTo(dateTo);
        setBookingDate(bookingDate);
        this.status = status;
        this.customerID = customerID;
        this.customerName = customerName;
        this.motorhouseID = motorhouseID;
        this.motorhouseName = motorhouseName;
    }

    public Reservation(LocalDate dateFrom, LocalDate dateTo, LocalDate bookingDate, String status, int customerID, String customerName, int motorhouseID, String motorhouseName) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.bookingDate = bookingDate;
        this.status = status;
        this.customerID = customerID;
        this.customerName = customerName;
        this.motorhouseID = motorhouseID;
        this.motorhouseName = motorhouseName;
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

    public void setDateFrom(String dateFrom){
        String[] dateSplit = dateFrom.split("-");
        this.dateFrom = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateTo(String dateTo){
        String[] dateSplit = dateTo.split("-");
        this.dateTo = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        String[] dateSplit = bookingDate.split("-");
        this.bookingDate = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getMotorhouseID() {
        return motorhouseID;
    }

    public void setMotorhouseID(int motorhouseID) {
        this.motorhouseID = motorhouseID;
    }

    public String getMotorhouseName() {
        return motorhouseName;
    }

    public void setMotorhouseName(String motorhouseName) {
        this.motorhouseName = motorhouseName;
    }
}
