package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Service;

import java.util.List;

public interface IService{
    Service get(int id);
    List<Service> getAll();
    List<Service> getMotorhomes(int id);
    List<Service> getOngoing();
    boolean create(Service service);
    boolean update(Service service);
    boolean delete(int id);
}
