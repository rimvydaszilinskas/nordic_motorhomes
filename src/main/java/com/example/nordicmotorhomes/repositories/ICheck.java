package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Check;

public interface ICheck {
    Check get(int id);
    Check getReservation(int reservationID);
    boolean create(Check check);
    boolean update(Check check);
    boolean delete(int id);
}
