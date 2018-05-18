package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Extra;

import java.util.List;

public interface IExtras {
    List<Extra> getAll();
    double inRangeTotal(List<Integer> ids);
    Extra get(int id);
    List<Extra> getReservation(int reservationID);
    boolean createReservationExtras(int reservationID, List<Integer> extraIDs);
    boolean update(Extra extra);
    boolean create(Extra extra);
    boolean delete(int id);
}
