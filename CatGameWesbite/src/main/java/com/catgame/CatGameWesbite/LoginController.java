package com.catgame.CatGameWesbite;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @PostMapping("/loginServlet")
    public String handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database database = new Database();
        String email = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String dbPassword = database.searchUser(email, userPassword);
        if (dbPassword != null) {
            if (database.validatePassword(userPassword, dbPassword)) {
                logger.info("User found and logged in");
                return "redirect:/";
            } 
            else {
                request.setAttribute("errorMessage", "Invalid Credentials.");
                logger.warn("Invalid credentials");
                return "login";
            }
        }
        else {
            request.setAttribute("errorMessage", "User not found.");
            logger.warn("Users not found.");
            return "login"; 
        } 
    }
}
