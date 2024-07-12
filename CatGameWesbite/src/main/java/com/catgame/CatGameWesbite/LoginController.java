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
        String password = request.getParameter("password");
        if (database.searchUser(email, password)) {
            return "redirect:/";
        }
        else {
            return "login"; 
        } 
    }
}

