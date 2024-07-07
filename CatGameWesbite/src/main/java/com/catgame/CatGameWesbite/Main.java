package com.catgame.CatGameWesbite;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class Main {
    @Autowired
    private Database database;
    
    @RequestMapping("/")
    public String homepage(){
        database.DatabaseConnection();
        database.createTable();
        return "index";
    }
    
    @RequestMapping("/registration")
    public String registration(){
        return "registration";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
