package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.MotorHouse;

import java.util.List;

public interface IMotorHouse {
    boolean add(MotorHouse motorHouse);
    boolean delete(int id);
    boolean addModel(MotorHouse motorHouse);
    boolean update(MotorHouse motorHouse);
    List<MotorHouse> getAll();
    List<MotorHouse> getAllOnGearbox(String gearbox);
    List<MotorHouse> getAllOnCategory(String category);
    List<MotorHouse> getAllOnManufacturer(String manufacturer);
    MotorHouse get(int id);
}
