package com.catgame.CatGameWesbite.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.models.RegisterDto;
import com.catgame.CatGameWesbite.repository.UserRepository;



@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterDto registerDto) {
        if (userRepository.findUserByEmail(registerDto.getEmail()) != null) {
            logger.error("User already exists with email: " + registerDto.getEmail());
            throw new RuntimeException("User already exists with email: " + registerDto.getEmail());
        }

        LoginUser newUser = new LoginUser();
        newUser.setName(registerDto.getName());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword())); 
        userRepository.save(newUser);
        logger.info("New user created.");
    }

    public boolean isTwoFactorAuthEnabled(String email) {
        LoginUser user = userRepository.findUserByEmail(email);
        if (user != null) {
            return user.isTwoFactorEnabled();
        } else {
            logger.error("User not found with email: " + email);
            return false; 
        }
    }
}

