package com.catgame.CatGameWesbite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class Database {

    String url = "jdbc:sqlite:my.db";
    private Connection conn;
    private static final Logger logger = LogManager.getLogger(Database.class);



    public Database() {
        conn = databaseConnection();
    }

    public Connection databaseConnection(){
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                var meta = conn.getMetaData();
                logger.info("The driver name is " + meta.getDriverName());
                logger.info("Connection created with database.");
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

    public boolean validatePassword(String userPassword, String dbPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
        if (encoder.matches(userPassword, dbPassword)) {
            logger.info("PASSWORD MATCH");
            return true;
        } else {
            logger.warn("WRONG PASSWORD");
            return false;
        }
    }
}
