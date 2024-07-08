package com.catgame.CatGameWesbite;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.*;


@Controller
public class RegistrationController {


    @PostMapping("/registrationServlet")
    public String handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database database = new Database();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        if (password1.equals(password2) && password1.length() >= 8) {
            String hashPassword = BCrypt.hashpw(password1, BCrypt.gensalt());
            System.out.println("HASH " + hashPassword);
            database.insertUser(name, email, hashPassword, 0);
        }
        else 
        {
            System.out.println("Easy password. Try again.");
        }
        return "redirect:/";  
    }


}

