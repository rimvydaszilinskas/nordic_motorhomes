package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Reservation;
import com.example.nordicmotorhomes.utilities.JSON;

import java.util.Date;
import java.util.List;

public interface IReservation {
    Reservation get(int id);
    List<Reservation> getAll();
    List<Reservation> getAll(Date from, Date to);
    List<Reservation> getVehicleReservations(int vanID);
    List<Reservation> getCustomerReservation(int customerID);
    JSON getLastReservation(int vanID);
    boolean update(Reservation reservation);
    boolean delete(int id);
    boolean create(Reservation reservation);
}
