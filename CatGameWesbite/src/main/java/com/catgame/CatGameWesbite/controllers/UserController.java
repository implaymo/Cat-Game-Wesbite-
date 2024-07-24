package com.catgame.CatGameWesbite.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.catgame.CatGameWesbite.models.RegisterDto;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.services.UserService;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);    


    @Autowired
    private UserService userService; 

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registerDto", new RegisterDto()); 
        logger.info("User able to enter registration page.");
        return "registration-page";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute RegisterDto registerDto, Model model) {
        // Perform registration logic, including validation and saving user
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            logger.error("Passwords do not match.");
            model.addAttribute("error", "Passwords do not match.");
            return "registration-page";
        }

        try {
            userService.registerUser(registerDto); 
            logger.info("Registration with success.");
            return "success"; 
        } catch (Exception e) {
            logger.error("Registration failed.");
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "registration-page";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginUser", new LoginUser()); 
        logger.info("User able to enter login page.");
        return "login-page";
    }

    @GetMapping("/twofacauth")
    public String auth() {
        return "twofa";
    }
}
