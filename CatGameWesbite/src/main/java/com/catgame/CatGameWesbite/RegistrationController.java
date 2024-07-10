package com.catgame.CatGameWesbite;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.*;


@Controller
public class RegistrationController {

    String requiredParams = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

    @PostMapping("/registrationServlet")
    public String handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database database = new Database();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        Pattern pattern = Pattern.compile(requiredParams, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password1);

        if (password1.equals(password2) && password1.length() >= 8) {
            String hashPassword = BCrypt.hashpw(password1, BCrypt.gensalt());
            database.insertUser(name, email, hashPassword, 0);
            return "redirect:/";  
        }
        else
        {
            System.out.println("Easy password. Try again.");
            request.setAttribute("errorMessage", "Passwords do not match or are too short.");
            return "registration";  
        }
    }


}

