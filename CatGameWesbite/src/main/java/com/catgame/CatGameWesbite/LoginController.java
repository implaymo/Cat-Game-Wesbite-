package com.catgame.CatGameWesbite;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @PostMapping("/loginServlet")
    public String handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database database = new Database();
        String email = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String dbPassword = database.searchUser(email, userPassword);
        if (dbPassword != null) {
            if (database.validatePassword(userPassword, dbPassword)) {
                System.out.println("User found and logged in");
                return "redirect:/";
            } 
            else {
                request.setAttribute("errorMessage", "Invalid Credentials.");
                return "login";
            }
        }
        else {
            request.setAttribute("errorMessage", "User not found.");
            return "login"; 
        } 
    }
}
