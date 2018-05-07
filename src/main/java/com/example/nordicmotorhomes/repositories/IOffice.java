package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Office;

import java.util.List;

public interface IOffice {
    List<Office> getAll();
    Office get(int id);
    boolean add(Office office);
    boolean update(Office office);
    boolean delete(Office office);
}
