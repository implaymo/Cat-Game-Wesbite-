package com.catgame.CatGameWesbite.services;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.catgame.CatGameWesbite.models.LoginUser;
import org.springframework.security.core.userdetails.User;


import com.catgame.CatGameWesbite.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;



    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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
            logger.info("Token created ");
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

    public void sendEmail(String recipientEmail, String link)
        throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom("goncalomoreirasilva@gmail.com", "Cat Game Website");
        helper.setTo(recipientEmail);
        
        String subject = "Here's the link to reset your password";
        
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        
        helper.setSubject(subject);
        
        helper.setText(content, true);
        
        mailSender.send(message);
        logger.info("Successfully sent email to user.");
    }

}