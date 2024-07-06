package com.catgame.CatGameWesbite;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.ServerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class Main {
    @Autowired
    private Database database;
    
    @RequestMapping("/")
    public String homepage(){
        database.DatabaseConnection();
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
