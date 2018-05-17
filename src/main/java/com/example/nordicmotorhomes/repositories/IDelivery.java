package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Delivery;

import java.util.List;

public interface IDelivery {
    List<Delivery> getDeliveries(int reservationID);
    boolean add(Delivery delivery);
    boolean delete(int id);
    boolean update(Delivery delivery);
}
