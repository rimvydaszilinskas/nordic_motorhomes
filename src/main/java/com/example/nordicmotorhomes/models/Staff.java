package com.example.nordicmotorhomes.models;

public class Staff extends Person {
    String password;
    String position;
    String description;

    public Staff(int id, String firstName, String lastName, String CPR, String address, String postCode, String city, String country, String phone, String password, String position, String description) {
        super(id, firstName, lastName, CPR, address, postCode, city, country, phone);
        this.password = password;
        this.position = position;
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
