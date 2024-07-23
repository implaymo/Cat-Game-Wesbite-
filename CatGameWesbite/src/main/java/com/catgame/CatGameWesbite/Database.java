package com.catgame.CatGameWesbite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;



@Service
public class Database {

    Connection conn;
    private static final Logger logger = LogManager.getLogger(Database.class);
    Properties properties = new Properties();
    String dbUser;
    String dbPass;
    String dbUrl;



    public Database() {
        getDatabaseCredentials();
        this.conn = databaseConnection();
    }


    public void getDatabaseCredentials() {
        // Gets sensitive that from config.properties file
        try (InputStream input = new FileInputStream(".env")) {
            properties.load(input);
            dbUser = properties.getProperty("username");
            dbPass = properties.getProperty("password");
            dbUrl = properties.getProperty("url");
        } catch (IOException ex){
            ex.printStackTrace();
        }    
    }

    public Connection databaseConnection(){
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            if (conn != null) {
                var meta = conn.getMetaData();
                logger.info("The driver name is " + meta.getDriverName());
                logger.info("Connection created with database.");
            } 
            else{
                logger.error("Connection to Database failed.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return conn;
    }

    public void createTable() {
        var sql = "CREATE TABLE IF NOT EXISTS users ("
            + " id INTEGER PRIMARY KEY,"
            + " name TEXT NOT NULL,"
            + " email TEXT NOT NULL,"
            + " password TEXT NOT NULL,"
            + " highscore REAL"
            + ");";

        try (Statement stmt = conn.createStatement()){  
            stmt.execute(sql);  
            logger.info("Table created.");
        } catch (SQLException e) {  
            logger.error(e.getMessage());
        }  
    }
    
    public void insertUser(String name, String email, String password, Integer highscore) {
        String sql = "INSERT INTO users(name, email, password, highscore) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, highscore);
            ps.executeUpdate();
            logger.info("IT WORKED, NEW USER ADDED");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public String searchUser(String userEmail) {
        String sql = "SELECT * FROM users WHERE email = ?";
        String dbPassword;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info("User found.");
                    dbPassword = rs.getString("password");
                    return dbPassword;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
