package com.catgame.CatGameWesbite.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.catgame.CatGameWesbite.models.RegisterDto;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            return "redirect:/successregistration"; 
        } catch (Exception e) {
            logger.error("Registration failed.");
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "registration-page";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginUser", new LoginUser()); 
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials.");
        }   

        logger.info("User able to enter login page.");
        return "login-page";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        session.invalidate();
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        
        return "redirect:/login?logout";
    }


    @GetMapping("/twofacauth")
    public String auth() {
        return "twofa";
    }

    
    @GetMapping("/successlogin")
    public String successlogin() {
        return "success-login";
    }

    @GetMapping("/successregistration")
    public String successRegistration() {
        return "success-registration";
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false); // Get the current session
        if (session != null) {
            model.addAttribute("sessionId", session.getId());
        }
        return "test";
}
}
