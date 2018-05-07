package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Extra;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ExtrasRepository implements IExtras {
    private PreparedStatement preparedStatement;
    private ResultSet result;
    private Connection conn;

    public ExtrasRepository() {
        try{
            conn = Database.getConnection();
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
    }

    @Override
    public List<Extra> getAll() {
        List<Extra> extras = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM extras");
            result = preparedStatement.executeQuery();

            while(result.next()){
                extras.add(new Extra(result.getInt("id"), result.getString("name"), result.getFloat("price")));
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return extras;
    }

    @Override
    public Extra get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM extras WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();
            if(result.next()){
                return new Extra(result.getInt("id"), result.getString("name"), result.getFloat("price"));
            }
        } catch(SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return null;
    }

    @Override
    public List<Extra> getReservation(int reservationID){
        List<Extra> extras = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM reservationextras WHERE reservation_id=?");
            preparedStatement.setInt(1, reservationID);

            result = preparedStatement.executeQuery();

            List<Integer> ids = new LinkedList<>();
            while(result.next()){
                ids.add(result.getInt("extra_id"));
            }
            extras = getInRange(ids);
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return extras;
    }

    @Override
    public boolean update(Extra extra) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE extras SET name=?, price=? WHERE id=?");

            preparedStatement.setString(1, extra.getName());
            preparedStatement.setFloat(2, extra.getPrice());
            preparedStatement.setInt(3, extra.getId());

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    @Override
    public boolean create(Extra extra) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO extras(name, price) VALUES(?, ?)");
            preparedStatement.setString(1, extra.getName());
            preparedStatement.setFloat(2, extra.getPrice());

            if(preparedStatement.executeUpdate() != 0){
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
            preparedStatement = conn.prepareStatement("DELETE FROM extras WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getSQLState());
        }
        return false;
    }

    private List<Extra> getInRange(List<Integer> ids) throws SQLException{
        List<Extra> extras = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM extras WHERE id IN (");
        for(int i=0; i<ids.size(); i++){
            if(i != 0){
                stringBuilder.append(", ");
            }
            stringBuilder.append("? ");
        }
        stringBuilder.append(")");

        preparedStatement = conn.prepareStatement(stringBuilder.toString());

        for(int i = 0; i < ids.size(); i++){
            preparedStatement.setInt(i+1, ids.get(i));
        }

        result = preparedStatement.executeQuery();

        while(result.next()){
            extras.add(new Extra(result.getInt("id"), result.getString("name"), result.getFloat("price")));
        }

        return extras;
    }
}
