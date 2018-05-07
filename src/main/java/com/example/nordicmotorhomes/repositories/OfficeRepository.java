package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Office;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OfficeRepository implements IOffice {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public OfficeRepository(){
        try{
            conn = Database.getConnection();
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }

    }

    @Override
    public List<Office> getAll() {
        List<Office> offices = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM offices");

            result = preparedStatement.executeQuery();

            while(result.next()){
                offices.add(new Office(result.getInt("id"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getString("city"),
                        result.getString("postal_code"),
                        result.getString("country")));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return offices;
    }

    @Override
    public Office get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM offices WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();
            if(result.next()){
                return new Office(result.getInt("id"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getString("city"),
                        result.getString("postal_code"),
                        result.getString("country"));
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public boolean add(Office office) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO offices(name, address, postal_code, city, country) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, office.getName());
            preparedStatement.setString(2, office.getAddress());
            preparedStatement.setString(3, office.getPostCode());
            preparedStatement.setString(4, office.getCity());
            preparedStatement.setString(5, office.getCountry());

            if(preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean update(Office office) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE offices SET name=?, address=?, postal_code=?, city=?, country=? WHERE id=?");
            preparedStatement.setString(1, office.getName());
            preparedStatement.setString(2, office.getAddress());
            preparedStatement.setString(3, office.getPostCode());
            preparedStatement.setString(4, office.getCity());
            preparedStatement.setString(5, office.getCountry());
            preparedStatement.setInt(6, office.getId());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean delete(Office office) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM offices WHERE id=?");
            preparedStatement.setInt(1, office.getId());

            if(preparedStatement.executeUpdate()>0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }
}
