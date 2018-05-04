package com.example.nordicmotorhomes.repositories;

import com.example.nordicmotorhomes.models.Customer;
import com.example.nordicmotorhomes.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CustomerRepository implements ICustomer{
    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public CustomerRepository() {
        try {
            this.conn = Database.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new LinkedList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM customers");

            result = preparedStatement.executeQuery();

            while(result.next()){
                customers.add(new Customer(result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("CPR"),
                        result.getString("address"),
                        result.getString("postalcode"),
                        result.getString("city"),
                        result.getString("country"),
                        result.getString("phone")));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return customers;
    }

    @Override
    public Customer get(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM customers WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeQuery();
            if(result.next()){
                return new Customer(result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("CPR"),
                        result.getString("address"),
                        result.getString("postalcode"),
                        result.getString("city"),
                        result.getString("country"),
                        result.getString("phone"));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Customer customer) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO customers(firstname, lastname, CPR, address, postalcode, city, country, phone)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCPR());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getPostCode());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getCountry());
            preparedStatement.setString(8, customer.getPhone());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE customers SET firstname=?, lastname=?, CPR=?, address=?, postalcode=?, city=?, country=?, phone=? WHERE id=?");

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCPR());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getPostCode());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getCountry());
            preparedStatement.setString(8, customer.getPhone());
            preparedStatement.setInt(9, customer.getId());

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM customers WHERE id=?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
