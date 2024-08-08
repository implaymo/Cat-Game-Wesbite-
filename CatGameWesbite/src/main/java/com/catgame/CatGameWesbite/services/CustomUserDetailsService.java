package com.catgame.CatGameWesbite.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.catgame.CatGameWesbite.models.LoginUser;
import org.springframework.security.core.userdetails.User;


import com.catgame.CatGameWesbite.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginUser user = userRepository.findUserByEmail(email);
        if (user != null) {
            logger.info("User authenticated with email: " + email);
            return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
        } else {
            logger.error("User not found with email " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        LoginUser user = userRepository.findUserByEmail(email);
        if (user != null) {
            logger.info("Token created " + token);
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public LoginUser getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(LoginUser user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}