package com.catgame.CatGameWesbite;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    Authentication auth = new Authentication();



    @PostMapping("/authSuccess")
    public String handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inputCode = request.getParameter("token");
        String authCode = auth.getCode();
        if (authCode != null) {
            if (authCode.equals(inputCode)){
                logger.info("Auth Codes match.");
                return "success";
            }
            else {
                request.setAttribute("errorMessage", "Wrong code.");
                logger.error("User provided a wrong code.");
            }
        }
        else {
            logger.error("authCode is Null. Fix Authentication.java");
        }
        return "twofa";
    }
}
