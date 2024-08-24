package com.microsservices.country.repositorys.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InstaceDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/dbcountry_sport_medal";
    // private static final String URL = "jdbc:postgresql://db_country_medal:5432/dbcountry_sport_medal";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgresdb";
    private static InstaceDatabase instance;

    private InstaceDatabase() {
    }

    public static InstaceDatabase getInstance(){
        if(instance == null){
            instance = new InstaceDatabase();
        }
        return instance;
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    

}
