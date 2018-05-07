package com.example.nordicmotorhomes.models;

public class Office {
    int id;
    String name;
    String address;
    String city;
    String postCode;
    String country;

    public Office(int id, String name, String address, String city, String postCode, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public Office(String name, String address, String city, String postCode, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public Office() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
