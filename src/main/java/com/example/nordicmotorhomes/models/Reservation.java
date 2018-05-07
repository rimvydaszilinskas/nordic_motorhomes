package com.example.nordicmotorhomes.models;

import java.util.Date;

public class Reservation {
    private int id;
    private Date dateFrom;
    private Date dateTo;
    private Date bookingDate;
    private String status;
    private int customerID;
    private String customerName;
    private int motorhouseID;
    private String motorhouseName;

    public Reservation(){}

    public Reservation(int id){
        this.id = id;
    }

    public Reservation(int id, Date dateFrom, Date dateTo, Date bookingDate, String status, int customerID, String customerName, int motorhouseID, String motorhouseName) {
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

    public Reservation(Date dateFrom, Date dateTo, Date bookingDate, String status, int customerID, String customerName, int motorhouseID, String motorhouseName) {
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateFrom(String dateFrom){
        String[] dateSplit = dateFrom.split("-");
        Date date = new Date(Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
        this.dateFrom = date;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateTo(String dateTo){
        String[] dateSplit = dateTo.split("-");
        Date date = new Date(Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
        this.dateTo = date;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
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
