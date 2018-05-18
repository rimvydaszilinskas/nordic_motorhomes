package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Pickup;

import java.util.List;

public interface IPickupDropoff {
    Pickup get(int id);
    List<Pickup> getReservationPickups(int reservationID);
    boolean create(Pickup pickup);
    boolean update(Pickup pickup);
    boolean delete(int id);
}
