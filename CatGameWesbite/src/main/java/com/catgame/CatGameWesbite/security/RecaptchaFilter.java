package com.catgame.CatGameWesbite.security;



import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RecaptchaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println(recaptchaResponse);
        boolean verifyRecaptcha = VerifyRecaptcha.verify(recaptchaResponse);


    }
}
