package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Delivery;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DeliveryRepository implements IDelivery {
    private PreparedStatement preparedStatement;
    private ResultSet result;
    private Connection conn;

    public DeliveryRepository(){
        try{
            conn = Database.getConnection();
        } catch (SQLException ex){
            System.out.println("here" + ex.getSQLState());
        }
    }

    @Override
    public List<Delivery> getDeliveries(int reservationID) {
        List<Delivery> deliveries = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM delivery WHERE reservation_id=?");
            preparedStatement.setInt(1, reservationID);

            result = preparedStatement.executeQuery();
            while(result.next()){
                deliveries.add(new Delivery(result.getInt("id"),
                        result.getString("address"),
                        result.getDouble("distance")));
            }
        }catch (SQLException ex){
            System.out.println("here " + ex.getSQLState());
        }
        return deliveries;
    }

    @Override
    public boolean add(Delivery delivery) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO delivery(address, distance) VALUES (?, ?)");
            preparedStatement.setString(1, delivery.getAddress());
            preparedStatement.setDouble(2, delivery.getDistance());

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
            preparedStatement = conn.prepareStatement("DELETE FROM delivery WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean update(Delivery delivery) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE delivery SET address=?, distance=? WHERE id=?");
            preparedStatement.setString(1, delivery.getAddress());
            preparedStatement.setDouble(2, delivery.getDistance());
            preparedStatement.setInt(3, delivery.getId());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }
}
