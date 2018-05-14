package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Payment;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PaymentRepository implements IPayment {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public PaymentRepository(){
        try {
            this.conn = Database.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM payments");
            result = preparedStatement.executeQuery();

            while(result.next()){
                payments.add(new Payment(result.getInt("id"),
                                        result.getDouble("ammount"),
                                        result.getInt("reservation_id"),
                                        result.getString("date")));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return payments;
    }

    @Override
    public List<Payment> getThisMonths() {
        List<Payment> payments = new LinkedList<>();
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        StringBuilder str = new StringBuilder(year).append("-").append(month).append("-").append("01");
        String start = str.toString();
        str = new StringBuilder(year).append("-").append(month).append("-").append("31");
        String end = str.toString();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM payments WHERE date BETWEEN ? AND ?");
            preparedStatement.setString(1, start);
            preparedStatement.setString(2, end);

            result = preparedStatement.executeQuery();
            while(result.next()){
                payments.add(new Payment(result.getInt("id"),
                        result.getDouble("ammount"),
                        result.getInt("reservation_id"),
                        result.getString("date")));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return payments;
    }

    @Override
    public Payment get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM payments WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();
            if(result.next()){
                return new Payment(result.getInt("id"),
                        result.getDouble("ammount"),
                        result.getInt("reservation_id"),
                        result.getString("date"));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public boolean update(Payment payment) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE payments SET ammount=?, reservation_id=?, date=? WHERE id=?");
            preparedStatement.setDouble(1, payment.getAmmount());
            preparedStatement.setInt(2, payment.getReservation_id());
            preparedStatement.setString(3, payment.getDate().toString());
            preparedStatement.setInt(4, payment.getId());

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean add(Payment payment) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO payments(ammount, reservation_id, date) VALUES(?, ?, ?)");
            preparedStatement.setDouble(1, payment.getAmmount());
            preparedStatement.setInt(2, payment.getReservation_id());
            preparedStatement.setString(3, payment.getDate().toString());

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM payments WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }
}
