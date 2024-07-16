package com.catgame.CatGameWesbite;

import java.io.IOException;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class AuthController {
    

    @PostMapping("/authSuccess")
    public String handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = new Authentication();
        String inputCode = request.getParameter("token");
        String authCode = auth.getCode();
        if (authCode != null) {
            if (authCode.equals(inputCode)){
                return "success";
            }
        }
        else {
            request.setAttribute("errorMessage", "Wrong code.");
        }
        return "twofa";
    }
}
