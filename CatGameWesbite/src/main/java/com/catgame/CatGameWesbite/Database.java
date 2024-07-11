package com.catgame.CatGameWesbite;

import org.springframework.stereotype.Service;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import javax.naming.spi.DirStateFactory.Result;



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

    public void searchUser(String email, String password) {
        String sql = "SELECT email, password FROM users WHERE email = email AND password = password";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String passwordUser = rs.getString("password");
                System.out.println("USER PASSWORD FOUND " + passwordUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
