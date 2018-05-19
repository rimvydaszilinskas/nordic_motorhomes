package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Check;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckRepository implements ICheck{
    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public CheckRepository(){
        try{
            conn = Database.getConnection();
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
    }

    @Override
    public Check get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM checkup WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return new Check(result.getInt("id"),
                        result.getInt("reservation_id"),
                        result.getString("date"),
                        result.getInt("lights"),
                        result.getInt("chasis"),
                        result.getInt("engine"),
                        result.getInt("interrior"),
                        result.getInt("exterrior"),
                        result.getString("notes"));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public Check getReservation(int reservationID) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM checkup WHERE reservation_id=?");
            preparedStatement.setInt(1, reservationID);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return new Check(result.getInt("id"),
                        result.getInt("reservation_id"),
                        result.getString("date"),
                        result.getInt("lights"),
                        result.getInt("chasis"),
                        result.getInt("engine"),
                        result.getInt("interior"),
                        result.getInt("exterior"),
                        result.getString("notes"));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public boolean create(Check check) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO checkup(reservation_id, date, lights, chasis, engine, interior, exterior, notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, check.getReservationID());
            preparedStatement.setString(2, check.getDate().toString());
            preparedStatement.setInt(3, check.getLights());
            preparedStatement.setInt(4, check.getChasis());
            preparedStatement.setInt(5, check.getEngine());
            preparedStatement.setInt(6, check.getInterrior());
            preparedStatement.setInt(7, check.getExterrior());
            preparedStatement.setString(8, check.getNotes());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean update(Check check) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE checkup SET reservation_id=?, date=?, lights=?, chasis=?, engine=?, interior=?, exterior=?, notes=? WHERE id=?");

            preparedStatement.setInt(1, check.getReservationID());
            preparedStatement.setString(2, check.getDate().toString());
            preparedStatement.setInt(3, check.getLights());
            preparedStatement.setInt(4, check.getChasis());
            preparedStatement.setInt(5, check.getEngine());
            preparedStatement.setInt(6, check.getInterrior());
            preparedStatement.setInt(7, check.getExterrior());
            preparedStatement.setString(8, check.getNotes());
            preparedStatement.setInt(9, check.getId());

            if(preparedStatement.executeUpdate() > 0){
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
            preparedStatement = conn.prepareStatement("DELETE FROM checkup WHERE id=?");

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
