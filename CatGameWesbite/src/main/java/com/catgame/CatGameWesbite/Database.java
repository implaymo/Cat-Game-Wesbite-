package com.catgame.CatGameWesbite;

import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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

    
}
