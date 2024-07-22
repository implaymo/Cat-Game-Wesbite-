package com.catgame.CatGameWesbite.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.zxing.WriterException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    TwoFactorAuth auth = new TwoFactorAuth();
    


    @PostMapping("/authSuccess")
    public String handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inputCode = request.getParameter("token");
        String authCode = TwoFactorAuth.getCode();
        /// THE FOLLOWING CODE WAS JUST FOR TESTING, NEED TO FIND BETTER PLACE TO SETUP. IT GIVES A QRCODE SO USER CAN ACTIVATE 2FA
        String barCodeUrl = TwoFactorAuth.getGoogleAuthenticatorBarCode(TwoFactorAuth.secretKey, "badjoras", "cat");
        try {
                TwoFactorAuth.createQRCode(barCodeUrl, "qr_code.png", 250, 250);
            } catch (WriterException e) {
                e.printStackTrace(); 
            }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            logger.error("authCode is Null. Fix TwoFactorAuth.java");
        }
        return "twofa";
    }
}
