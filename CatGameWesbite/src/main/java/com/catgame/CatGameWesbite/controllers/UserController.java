package com.catgame.CatGameWesbite.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.catgame.CatGameWesbite.dto.ScoreDto;
import com.catgame.CatGameWesbite.dto.HighscoreDto;
import com.catgame.CatGameWesbite.dto.RegisterDto;
import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.catgame.CatGameWesbite.repository.UserRepository;
import org.springframework.security.core.Authentication;


@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);    


    @Autowired
    private UserService userService; 

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registerDto", new RegisterDto()); 
        logger.info("User able to enter registration page.");
        return "registration-page";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute RegisterDto registerDto, Model model) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            logger.error("Passwords do not match.");
            model.addAttribute("error", "Passwords do not match.");
            return "registration-page";
        }

        try {
            userService.registerUser(registerDto); 
            logger.info("Registration with success.");
            return "redirect:/successregistration"; 
        } catch (Exception e) {
            logger.error("Registration failed.");
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "registration-page";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginUser", new LoginUser()); 
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials.");
        }   

        logger.info("User able to enter login page.");
        return "login-page";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        session.invalidate();
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        logger.info("User logout successfully.");
        return "redirect:/login?logout";
    }


    @GetMapping("/successlogin")
    public String successlogin() {
        return "success-login";
    }

    @GetMapping("/successregistration")
    public String successRegistration() {
        return "success-registration";
    }


    @PostMapping("/updatehighscore")
    public ResponseEntity<String> updateHighscore(@RequestBody ScoreDto scoreDto, Authentication authentication) {        
        String email = authentication.getName();
        LoginUser user = userRepository.findUserByEmail(email);

        if (user != null) {
            Integer userHighscore = user.getHighscore();
            Integer lastGameScore = scoreDto.getGameScore();
            System.out.println("USER HIGHSCORE " + userHighscore);
            System.out.println("LAST GAME SCORE: " + lastGameScore);

            if (userHighscore < lastGameScore) {
                user.setHighscore(lastGameScore);
                userRepository.save(user);
                logger.info("Highscore updated successfully.");
                return ResponseEntity.ok("Highscore updated.");

            } 
            return ResponseEntity.ok("User found but Highscore not updated.");
        }
        else {
            logger.error("User not found.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
         }
    }

    @GetMapping("/getuserhighscore")
    public ResponseEntity<HighscoreDto> setHighscore(Authentication authentication) {
        String email = authentication.getName();
        LoginUser user = userRepository.findUserByEmail(email);

        if (user != null) {
            int userHighscore = user.getHighscore();
            HighscoreDto highscoreDto = new HighscoreDto(userHighscore);
            return ResponseEntity.ok(highscoreDto);
        } 
        else {
            logger.error("User not found.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/forgetpassword")
    public String forgetPassword() {
        return "forget-password";
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false); // Get the current session
        if (session != null) {
            model.addAttribute("sessionId", session.getId());
        }
        return "test";
    }
}
