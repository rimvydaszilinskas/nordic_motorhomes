package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Customer;

import java.util.List;

public interface IPerson<T> {
    List<T> getAll();
    T get(int id);
    List<T> getLike(String name);
    boolean create(T customer);
    boolean update(T customer);
    boolean delete(int id);
}
