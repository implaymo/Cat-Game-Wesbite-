package com.catgame.CatGameWesbite;

import org.springframework.stereotype.Service;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.Statement;  


@Service
public class Database {

    String url = "jdbc:sqlite:my.db";

    public Database() {
        DatabaseConnection();
    }

    public void DatabaseConnection(){
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
}
