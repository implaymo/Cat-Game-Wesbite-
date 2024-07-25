package com.catgame.CatGameWesbite.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


import com.google.zxing.WriterException;

import jakarta.persistence.PostUpdate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);    


    @GetMapping("activate2fa")
    public String activate2fa() {
        return "settings-page";
    }

    @GetMapping("qrcode") 
    public String qrcodeCreation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TwoFactorAuth.getCode();
        String barCodeUrl = TwoFactorAuth.getGoogleAuthenticatorBarCode(TwoFactorAuth.secretKey, "badjoras", "cat");
        try {
                TwoFactorAuth.createQRCode(barCodeUrl, "src\\main\\resources\\static\\qr_code.png", 250, 250);
                logger.info("QRCODE created successfully.");
            } catch (WriterException e) {
                logger.error(e.getMessage());
            }
        return "qrcode-page";
    }

    @GetMapping("/checkcode")
    public String auth() {
        return "checkcode";
    }

    @PostMapping("/authsuccess")
    public String handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inputCode = request.getParameter("token");
        String authCode = TwoFactorAuth.getCode();
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
