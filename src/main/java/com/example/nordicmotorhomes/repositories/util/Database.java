package com.example.nordicmotorhomes.repositories.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final static String USERNAME = "rimboooo";
    private final static String PASSWORD = "Xs3E95i-~8SI";
    private final static String CONNSTRING = "jdbc:mysql://den1.mysql3.gear.host/rimboooo?useSSL=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
    }
}
