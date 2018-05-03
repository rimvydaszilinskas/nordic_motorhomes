package com.example.nordicmotorhomes.repositories.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";
    private final static String CONNSTRING = "jdbc:mysql://localhost/nordic_motorhomes";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
    }
}
