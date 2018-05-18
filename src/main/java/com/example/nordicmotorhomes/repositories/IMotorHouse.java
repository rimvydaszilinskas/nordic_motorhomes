package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.models.Transmission;

import java.util.Date;
import java.util.List;

public interface IMotorHouse {
    boolean add(MotorHouse motorHouse);
    boolean delete(int id);
    boolean addModel(MotorHouse motorHouse);
    boolean update(MotorHouse motorHouse);
    List<MotorHouse> getAll();
    List<MotorHouse> getAllOnGearbox(String gearbox);
    List<MotorHouse> getAllOnCategory(String category);
    List<MotorHouse> getAllOnFilters(String manufacturer, String gearbox, String transmission, int price);
    List<MotorHouse> getAllFreeMotorhouses(String from, String to, int seats, int beds);
    MotorHouse get(int id);
    MotorHouse getReservation(int id);
    List<String> getAllManufacturers();
    List<Transmission> getAllTransmissions();
    List<String> getAllGearboxes();
}
