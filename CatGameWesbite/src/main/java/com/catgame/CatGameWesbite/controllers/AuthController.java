package com.catgame.CatGameWesbite.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.repository.UserRepository;
import org.springframework.ui.Model;




@Controller
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);  

    @Autowired
    private UserRepository userRepository;

 


    @GetMapping("settings2fa")
    public String settings2fa() {
        return "settings-page";
    }

    @GetMapping("/qrcode") 
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

    @GetMapping("/twofactoractivation")
    public String activateTwoFactorAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        LoginUser user = userRepository.findUserByEmail(email);

        String inputCode = request.getParameter("token");
        String authCode = TwoFactorAuth.getCode();
        if (authCode != null) {
            if (authCode.equals(inputCode)){
                logger.info("Auth Codes match.");
                user.setTwoFactorAuth(true);
                userRepository.save(user);
                return "success-2fa-activation";
            }
            else {
                request.setAttribute("errorMessage", "Wrong code.");
                logger.error("User provided a wrong code.");
            }
        }
        else {
            logger.error("authCode is Null. Fix TwoFactorAuth.java");
        }
        return "qrcode-page";
    }

    @PostMapping("check2falogin")
    public String check2faLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String inputCode = request.getParameter("token");
        String authCode = TwoFactorAuth.getCode();
        if (authCode.equals(inputCode)) {
            return "success-login";
        }
        else {
            model.addAttribute("error", "Invalid Code.");
            return "checkcode";
        }
    }


}
