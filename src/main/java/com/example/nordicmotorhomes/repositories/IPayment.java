package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Payment;

import java.util.List;

public interface IPayment {
    List<Payment> getAll();
    List<Payment> getThisMonths();
    Payment get(int id);
    boolean update(Payment payment);
    boolean add(Payment payment);
    boolean delete(int id);
}
