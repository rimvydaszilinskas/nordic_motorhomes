package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MotorHouseRepo implements IMotorHouse {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public MotorHouseRepo(){
        try {
            this.conn = Database.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    @Override
    public boolean add(MotorHouse motorHouse) {
        try{

            int modelID = checkIfModelExists(motorHouse);

            if(modelID == 0) {
                modelID = insertModel(motorHouse);
            }

            preparedStatement = conn.prepareStatement("INSERT INTO motorhomes(model, gearbox, year, mileage, transmission) VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, modelID);
            preparedStatement.setString(2, motorHouse.getGearbox());
            preparedStatement.setInt(3, motorHouse.getYear());
            preparedStatement.setInt(4, motorHouse.getMileage());
            preparedStatement.setString(5, motorHouse.getTransmission());

            if(preparedStatement.executeUpdate() == 1){
                return true;
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM motorhomes WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addModel(MotorHouse motorHouse) {
        try{
            if(insertModel(motorHouse) > 0)
                return true;
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public List<MotorHouse> getAll() {
        List<MotorHouse> motorHouses = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM motorhome");
            result = preparedStatement.executeQuery();

            while(result.next()){
                motorHouses.add(new MotorHouse(
                        result.getInt("id"),
                        result.getString("manufacturer"),
                        result.getString("model"),
                        result.getInt("bed_count"),
                        result.getInt("seats"),
                        result.getInt("weight"),
                        "",
                        result.getString("gearbox"),
                        result.getInt("year"),
                        result.getInt("mileage"),
                        result.getString("transmission"),
                        0));
            }

            return motorHouses;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MotorHouse> getAllOnGearbox(String gearbox) {
        List<MotorHouse> motorHouses = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM motorhome WHERE gearbox=?");
            preparedStatement.setString(1, gearbox);
            result = preparedStatement.executeQuery();

            while(result.next()){
                motorHouses.add(new MotorHouse(
                        result.getInt("id"),
                        result.getString("manufacturer"),
                        result.getString("model"),
                        result.getInt("bed_count"),
                        result.getInt("seats"),
                        result.getInt("weight"),
                        result.getString("description"),
                        result.getString("gearbox"),
                        result.getInt("year"),
                        result.getInt("mileaage"),
                        result.getString("transmission"),
                        0));
            }

            return motorHouses;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MotorHouse> getAllOnCategory(String category) {
        return null;
    }

    @Override
    public List<MotorHouse> getAllOnManufacturer(String manufacturer) {
        List<MotorHouse> motorHouses = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM motorhome WHERE manufacturer=?");
            preparedStatement.setString(1, manufacturer);
            result = preparedStatement.executeQuery();

            while(result.next()){
                motorHouses.add(new MotorHouse(
                        result.getInt("id"),
                        result.getString("manufacturer"),
                        result.getString("model"),
                        result.getInt("bed_count"),
                        result.getInt("seats"),
                        result.getInt("weight"),
                        result.getString("description"),
                        result.getString("gearbox"),
                        result.getInt("year"),
                        result.getInt("mileaage"),
                        result.getString("transmission"),
                        0));
            }

            return motorHouses;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public MotorHouse get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM motorhome WHERE id=?");
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            if(result.next()){
                return new MotorHouse(
                        result.getInt("id"),
                        result.getString("manufacturer"),
                        result.getString("model"),
                        result.getInt("bed_count"),
                        result.getInt("seats"),
                        result.getInt("weight"),
                        result.getString("description"),
                        result.getString("gearbox"),
                        result.getInt("year"),
                        result.getInt("mileaage"),
                        result.getString("transmission"),
                        0);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(MotorHouse motorHouse){
        int modelID = 0;
        try{
            //check if exists current model
            preparedStatement = conn.prepareStatement("SELECT * FROM models WHERE manufacturer=? AND model=?");
            preparedStatement.setString(1, motorHouse.getManufacturer());
            preparedStatement.setString(2, motorHouse.getModel());

            result = preparedStatement.executeQuery();

            if(result.next()){
                //if current model exists check if its only one of a kind
                if(result.getInt("bed_count") != motorHouse.getBed_count() ||
                        result.getInt("seats") != motorHouse.getSeats() ||
                        result.getInt("weight") != motorHouse.getWeight() ||
                        result.getString("description") != motorHouse.getDescription()){
                    int id = result.getInt("id");
                    //if its not the same check if the model has more than one campervan
                    preparedStatement = conn.prepareStatement("SELECT COUNT(id) FROM motorhome WHERE model_id = ?");
                    preparedStatement.setInt(1, id);

                    result = preparedStatement.executeQuery();
                    if(result.getInt("count(id)") > 1){
                        modelID = insertModel(motorHouse);
                    }
                }
            }
            String sql;
            if(modelID != 0)
                sql = "UPDATE motorhomes SET model=" + modelID + ", gearbox=?, year=?, mileage=?, transmission=?";
            else
                sql = "UPDATE motorhomes SET gearbox=?, year=?, mileage=?, transmission=?";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, motorHouse.getGearbox());
            preparedStatement.setInt(2, motorHouse.getYear());
            preparedStatement.setInt(3, motorHouse.getMileage());
            preparedStatement.setString(4, motorHouse.getTransmission());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    private int checkIfModelExists(MotorHouse motorHouse){
        //checks the view models if the model already exists
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM models WHERE manufacturer=? AND model=?");
            preparedStatement.setString(1, motorHouse.getManufacturer());
            preparedStatement.setString(2, motorHouse.getModel());

            result = preparedStatement.executeQuery();

            if(result.next())
                return result.getInt("id");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    private int insertModel(MotorHouse motorHouse) throws SQLException{
        preparedStatement = conn.prepareStatement("INSERT INTO models(manufacturer, model, bed_count, seats, weight, description) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, motorHouse.getManufacturer());
        preparedStatement.setString(2, motorHouse.getModel());
        preparedStatement.setInt(3, motorHouse.getBed_count());
        preparedStatement.setInt(4, motorHouse.getSeats());
        preparedStatement.setInt(5, motorHouse.getWeight());
        preparedStatement.setString(6, motorHouse.getDescription());

        if(preparedStatement.executeUpdate() == 1){
            preparedStatement = conn.prepareStatement("SELECT id FROM models ORDER BY id DESC LIMIT 1");

            result = preparedStatement.executeQuery();
            if(result.next()){
                 return result.getInt("id");
            }
        }

        return 0;
    }
}
