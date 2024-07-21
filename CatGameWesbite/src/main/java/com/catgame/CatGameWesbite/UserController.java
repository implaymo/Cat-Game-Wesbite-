package com.catgame.CatGameWesbite;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.*;


@Controller
public class UserController {
        
    @GetMapping(value = "/registration")
    public String registration(){
        return "registration-page";
    }

    private static String REQUIRED_PARAMS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
    private static final Logger logger = LogManager.getLogger(UserController.class);


    @PostMapping("/registration")
    public String handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database database = new Database();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String checkboxValue = request.getParameter("checkbox");
        Pattern pattern = Pattern.compile(REQUIRED_PARAMS, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password1);

        if (password1 == null || password2 == null) {
            request.setAttribute("errorMessage", "Passwords must be filled.");
            logger.warn("Empty password.");
            return "registration-page";  
        }

        if (!password1.equals(password2)) {
            request.setAttribute("errorMessage", "Passwords must match.");
            logger.warn("Passwords dont match.");
            return "registration-page";
        }

        if (password1.length() < 8) {
            request.setAttribute("errorMessage", "Password needs to have at least 8 characters long");
            logger.warn("Password is too short.");
            return "registration-page"; 
        }

        if (!matcher.matches()) {
            request.setAttribute("errorMessage", "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            logger.warn("Password missing all the requirements.");
            return "registration-page";  
        }

        if (checkboxValue == null) {
            request.setAttribute("errorMessage", "You must agree with the terms and conditions.");
            logger.warn("User didn't agreed with terms and conditions.");
            return "registration-page";  
        }

        String hashPassword = BCrypt.hashpw(password1, BCrypt.gensalt());
        database.insertUser(name, email, hashPassword, 0);
        logger.info("Registration done successfully.");
        return "redirect:/";          
    }


    @GetMapping("/login")
    public String login(){
        return "login-page";
    }


    @RequestMapping("/twofacauth")
    public String auth(){
        return "twofa";
    }
}
