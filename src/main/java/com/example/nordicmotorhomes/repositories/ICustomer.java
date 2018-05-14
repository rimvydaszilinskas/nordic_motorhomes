package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Customer;

import java.util.List;

public interface ICustomer {
    List<Customer> getAll();
    Customer get(int id);
    List<Customer> getLike(String name);
    boolean create(Customer customer);
    boolean update(Customer customer);
    boolean delete(int id);
}
