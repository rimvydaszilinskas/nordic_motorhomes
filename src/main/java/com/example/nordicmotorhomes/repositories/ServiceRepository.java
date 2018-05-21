package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Service;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ServiceRepository implements IService{
    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public ServiceRepository(){
        try{
            conn = Database.getConnection();
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
    }

    @Override
    public Service get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM service WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();

            if(result.next()){
                return new Service(result.getInt("id"),
                        result.getInt("motorhouse_id"),
                        result.getString("date_from"),
                        result.getString("date_to"),
                        result.getString("description"),
                        result.getInt("lights"),
                        result.getInt("chasis"),
                        result.getInt("engine"),
                        result.getInt("interior"),
                        result.getInt("exterior"),
                        result.getDouble("ammount"));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public List<Service> getAll() {
        List<Service> services = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM service");

            result = preparedStatement.executeQuery();

            while(result.next()){
                services.add(new Service(result.getInt("id"),
                        result.getInt("motorhouse_id"),
                        result.getString("date_from"),
                        result.getString("date_to"),
                        result.getString("description"),
                        result.getInt("lights"),
                        result.getInt("chasis"),
                        result.getInt("engine"),
                        result.getInt("interior"),
                        result.getInt("exterior"),
                        result.getDouble("ammount")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return services;
    }

    @Override
    public List<Service> getMotorhomes(int id) {
        List<Service> services = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM service WHERE motorhouse_id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();

            while(result.next()){
                services.add(new Service(result.getInt("id"),
                        result.getInt("motorhouse_id"),
                        result.getString("date_from"),
                        result.getString("date_to"),
                        result.getString("description"),
                        result.getInt("lights"),
                        result.getInt("chasis"),
                        result.getInt("engine"),
                        result.getInt("interior"),
                        result.getInt("exterior"),
                        result.getDouble("ammount")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return services;
    }

    @Override
    public boolean create(Service service) {
        try{
            if(service.getDateTo() == null){
                preparedStatement = conn.prepareStatement("INSERT INTO service (motorhouse_id, date_from, description, lights, chasis, engine, interior, exterior, ammount) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, service.getMotorhouseID());
                preparedStatement.setString(2, service.getDateFrom().toString());
                preparedStatement.setString(3, service.getDescription());
                preparedStatement.setInt(4, service.getLights());
                preparedStatement.setInt(5, service.getChasis());
                preparedStatement.setInt(6, service.getEngine());
                preparedStatement.setInt(7, service.getInterrior());
                preparedStatement.setInt(8, service.getExterrior());
                preparedStatement.setDouble(9, service.getAmount());
            } else {
                preparedStatement = conn.prepareStatement("INSERT INTO service (motorhouse_id, date_from, date_to, description, lights, chasis, engine, interior, exterior, ammount) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, service.getMotorhouseID());
                preparedStatement.setString(2, service.getDateFrom().toString());
                preparedStatement.setString(3, service.getDateTo().toString());
                preparedStatement.setString(4, service.getDescription());
                preparedStatement.setInt(5, service.getLights());
                preparedStatement.setInt(6, service.getChasis());
                preparedStatement.setInt(7, service.getEngine());
                preparedStatement.setInt(8, service.getInterrior());
                preparedStatement.setInt(9, service.getExterrior());
                preparedStatement.setDouble(10, service.getAmount());
            }

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean update(Service service) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE service SET motorhouse_id=?, date_from=?, date_to=?, description=?, " +
                    "lights=?, chasis=?, engine=?, interrior=?, exterrior=?,ammmount=? WHERE id=?");

            preparedStatement.setInt(1, service.getMotorhouseID());
            preparedStatement.setString(2, service.getDateFrom().toString());
            preparedStatement.setString(3, service.getDateTo().toString());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setInt(5, service.getLights());
            preparedStatement.setInt(6, service.getChasis());
            preparedStatement.setInt(7, service.getEngine());
            preparedStatement.setInt(8, service.getInterrior());
            preparedStatement.setInt(9, service.getExterrior());
            preparedStatement.setDouble(10, service.getAmount());
            preparedStatement.setInt(11, service.getId());

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
            preparedStatement = conn.prepareStatement("DELETE FROM service WHERE id=?");
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
