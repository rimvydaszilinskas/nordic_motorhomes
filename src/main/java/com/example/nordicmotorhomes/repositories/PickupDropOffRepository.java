package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Pickup;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PickupDropOffRepository implements IPickupDropoff {
    private PreparedStatement preparedStatement;
    private Connection conn;
    private ResultSet result;

    public PickupDropOffRepository(){
        try{
            conn = Database.getConnection();
        }catch (SQLException ex){
            System.out.println("Could not connect to database. SQL state: " + ex.getSQLState());
        }
    }

    @Override
    public Pickup get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM picksdrops WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return new Pickup(result.getInt("id"),
                        result.getString("date"),
                        result.getDouble("fuel"),
                        result.getInt("reservation_id"),
                        (int) result.getDouble("mileage"));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public List<Pickup> getReservationPickups(int reservationID) {
        List<Pickup> pickups = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM picksdrops WHERE reservation_id=?");
            preparedStatement.setInt(1, reservationID);

            result = preparedStatement.executeQuery();

            while(result.next()){
                pickups.add(new Pickup(result.getInt("id"),
                        result.getString("date"),
                        result.getDouble("fuel"),
                        result.getInt("reservation_id"),
                        (int) result.getDouble("mileage")));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return pickups;
    }

    @Override
    public boolean create(Pickup pickup) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO picksdrops(date, fuel, reservation_id, mileage) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, LocalDate.now().toString());
            preparedStatement.setDouble(2, pickup.getFuel());
            preparedStatement.setInt(3, pickup.getReservationID());
            preparedStatement.setInt(4, pickup.getMileage());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean update(Pickup pickup) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE picksdrops SET date=?, fuel=?, reservation_id=?, mileage=? WHERE id=?");
            preparedStatement.setString(1, LocalDate.now().toString());
            preparedStatement.setDouble(2, pickup.getFuel());
            preparedStatement.setInt(3, pickup.getReservationID());
            preparedStatement.setInt(4, pickup.getMileage());
            preparedStatement.setInt(5, pickup.getId());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM picksdrops WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }
}
