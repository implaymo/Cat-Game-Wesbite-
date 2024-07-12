package com.catgame.CatGameWesbite;

import org.springframework.stereotype.Service;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service
public class Database {

    String url = "jdbc:sqlite:my.db";
    Connection conn;

    public Database() {
        conn = DatabaseConnection();
        
    }

    public Connection DatabaseConnection(){
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Connection created with database.");
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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

        try{  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void insertUser(String name, String email, String password, Integer highscore) {
        String sql = "INSERT INTO users(name, email, password, highscore) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, highscore);
            ps.executeUpdate();
            System.out.println("IT WORKED, NEW USER ADDED");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            close(ps);
        }
    }

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean searchUser(String userEmail, String userPassword) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (encoder.matches(userPassword, dbPassword)) {
                        System.out.println("USER LOGGED IN ");
                        return true;  
                    } else {
                        System.out.println("WRONG PASSWORD");
                        return false;
                    }

                } else {
                    System.out.println("User not found");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
