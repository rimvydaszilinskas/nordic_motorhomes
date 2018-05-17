package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Reservation;
import com.example.nordicmotorhomes.repositories.util.Database;
import com.example.nordicmotorhomes.utilities.JSON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReservationRepository implements IReservation {
    private PreparedStatement preparedStatement;
    private Connection conn;
    private ResultSet result;

    public ReservationRepository(){
        try{
            conn = Database.getConnection();
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
    }

    @Override
    public Reservation get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM bookings WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();
            if(result.next()){
                return new Reservation(result.getInt("id"),
                        result.getDate("date_from").toString(),
                        result.getDate("date_to").toString(),
                        result.getDate("date_booked").toString(),
                        result.getString("status"),
                        result.getInt("customer_id"),
                        result.getString("firstname") + " " + result.getString("lastname"),
                        result.getInt("motorhome_id"),
                        result.getString("manufacturer") + " " + result.getString("model"),
                        result.getDouble("total"));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM bookings WHERE date_from > ? OR status != 'finished' ORDER BY date_from ASC");

            preparedStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            result = preparedStatement.executeQuery();

            while(result.next()){
                reservations.add(new Reservation(result.getInt("id"),
                        result.getDate("date_from").toString(),
                        result.getDate("date_to").toString(),
                        result.getDate("date_booked").toString(),
                        result.getString("status"),
                        result.getInt("customer_id"),
                        result.getString("firstname") + " " + result.getString("lastname"),
                        result.getInt("motorhome_id"),
                        result.getString("manufacturer") + " " + result.getString("model"),
                        result.getDouble("total")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return reservations;
    }

    @Override
    public List<Reservation> getAll(Date from, Date to) {
        List<Reservation> reservations = new LinkedList<>();

        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM bookings WHERE (date_to BETWEEN ? AND ?) OR (date_from BETWEEN ? AND ?) ORDER BY id DESC, dateFrom asc");
            preparedStatement.setString(1, from.toString());
            preparedStatement.setString(2, to.toString());
            preparedStatement.setString(3, from.toString());
            preparedStatement.setString(4, to.toString());

            result = preparedStatement.executeQuery();

            while(result.next()){
                reservations.add(new Reservation(result.getInt("id"),
                        result.getDate("date_from").toString(),
                        result.getDate("date_to").toString(),
                        result.getDate("date_booked").toString(),
                        result.getString("status"),
                        result.getInt("customer_id"),
                        result.getString("firstname") + " " + result.getString("lastname"),
                        result.getInt("motorhome_id"),
                        result.getString("manufacturer") + " " + result.getString("model"),
                        result.getDouble("total")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }

        return reservations;
    }

    @Override
    public List<Reservation> getVehicleReservations(int vanID) {
        List<Reservation> reservations = new LinkedList<>();

        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM bookings WHERE motorhome_id=?");
            preparedStatement.setInt(1, vanID);

            result = preparedStatement.executeQuery();

            while(result.next()){
                reservations.add(new Reservation(result.getInt("id"),
                        result.getDate("date_from").toString(),
                        result.getDate("date_to").toString(),
                        result.getDate("date_booked").toString(),
                        result.getString("status"),
                        result.getInt("customer_id"),
                        result.getString("firstname") + " " + result.getString("lastname"),
                        result.getInt("motorhome_id"),
                        result.getString("manufacturer") + " " + result.getString("model"),
                        result.getDouble("total")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return reservations;
    }

    @Override
    public JSON getLastReservation(int vanID){
        JSON json = new JSON();

        try{
            preparedStatement = conn.prepareStatement("SELECT date_from, date_to FROM reservations WHERE motorhome_id=? AND ");
            preparedStatement.setInt(1, vanID);

            result = preparedStatement.executeQuery();

            while(result.next()){}
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }

        return json;
    }

    @Override
    public List<Reservation> getCustomerReservation(int customerID) {
        List<Reservation> reservations = new LinkedList<>();

        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM bookings WHERE customer_id=?");
            preparedStatement.setInt(1, customerID);

            result = preparedStatement.executeQuery();

            while(result.next()){
                reservations.add(new Reservation(result.getInt("id"),
                        result.getDate("date_from").toString(),
                        result.getDate("date_to").toString(),
                        result.getDate("date_booked").toString(),
                        result.getString("status"),
                        result.getInt("customer_id"),
                        result.getString("firstname") + " " + result.getString("lastname"),
                        result.getInt("motorhome_id"),
                        result.getString("manufacturer") + " " + result.getString("model"),
                        result.getDouble("total")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return reservations;
    }

    @Override
    public boolean update(Reservation reservation) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE reservations SET date_from=?, date_to=?, motorhome_id=?, customer_id=?, status=?, date_booked=?, total=? WHERE id=?");
            preparedStatement.setString(1, reservation.getDateFrom().toString());
            preparedStatement.setString(2, reservation.getDateTo().toString());
            preparedStatement.setInt(3, reservation.getMotorhouseID());
            preparedStatement.setInt(4, reservation.getCustomerID());
            preparedStatement.setString(5, reservation.getStatus());
            preparedStatement.setString(6, reservation.getBookingDate().toString());
            preparedStatement.setDouble(7, reservation.getTotal());
            preparedStatement.setInt(8, reservation.getId());

            if(preparedStatement.executeUpdate() != 0)
                return true;
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM reservations WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() != 0)
                return true;
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean create(Reservation reservation) {
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO reservations(date_from, date_to, motorhome_id, customer_id, status, date_booked, total) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setDate(1, java.sql.Date.valueOf(reservation.getDateFrom()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(reservation.getDateTo()));
            preparedStatement.setInt(3, reservation.getMotorhouseID());
            preparedStatement.setInt(4, reservation.getCustomerID());
            preparedStatement.setString(5, reservation.getStatus());
            preparedStatement.setDate(6, java.sql.Date.valueOf(LocalDate.now().toString()));
            preparedStatement.setDouble(7, reservation.getTotal());

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean setTaken(int id){
        try{
            preparedStatement = conn.prepareStatement("UPDATE reservations SET status='taken' WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }
}
