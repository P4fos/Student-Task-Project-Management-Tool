package edu.aitu.oop3.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements IDB {


    private final String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private final String USER = "postgres.nbvwspufndatertzzlvr";
    private final String PASSWORD = "UgXqW3PE986uBxrX";


    public DatabaseConnection() {
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
            return null;
        }
    }
}