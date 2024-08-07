package com.catgame.CatGameWesbite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.repository.UserRepository;



@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName();
        LoginUser user = userRepository.findUserByEmail(email);

        if (user != null && user.isTwoFactorEnabled()) {
            response.sendRedirect("/checkcode");
        } else {
            response.sendRedirect("/successlogin");
        }
    }
}
