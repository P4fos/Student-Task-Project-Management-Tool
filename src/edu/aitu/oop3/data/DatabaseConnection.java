package edu.aitu.oop3.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection implements IDB {

    @Override
    public Connection getConnection() {
        Properties props = new Properties();
        String connectionUrl = "";
        String user = "";
        String pass = "";


        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            connectionUrl = props.getProperty("db.url");
            user = props.getProperty("db.user");
            pass = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println(" ERROR: config.properties file not found in project root!");
            e.printStackTrace();
            return null;
        }

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, user, pass);
        } catch (Exception e) {
            System.err.println(" CONNECTION ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}