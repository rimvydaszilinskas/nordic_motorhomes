package com.example.nordicmotorhomes.models;

public class Customer extends Person {

    public Customer(int id, String firstName, String lastName, String CPR, String address, String postCode, String city, String country, String phone) {
        super(id, firstName, lastName, CPR, address, postCode, city, country, phone);
    }
}
