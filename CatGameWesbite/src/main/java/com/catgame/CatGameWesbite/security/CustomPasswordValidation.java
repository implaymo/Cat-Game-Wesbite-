package com.catgame.CatGameWesbite.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomPasswordValidation {

    private static final Logger logger = LogManager.getLogger(CustomPasswordValidation.class); 
    
    private static final String special_chars = "!(){}[]:;<>?,@#$%^&*+=_-~`|./'";



    public boolean passwordStrenght(String password) {
        boolean containsLowerChar= false;
        boolean containsUpperChar = false;
        boolean containsDigit = false;
        boolean containsSpecialChar = false; 
        boolean minLength = false;
        char[] ch = password.toCharArray();

        if (password.length() >= 8) {
            minLength = true;
        }

        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(ch[i])) {
                containsLowerChar = true;
            }

            if (Character.isUpperCase(ch[i])) {
                containsUpperChar = true;
            }
            if (Character.isDigit(ch[i])) {
                containsDigit = true;
            }
            if (special_chars.contains(String.valueOf(ch[i]))) {
                containsSpecialChar = true;
            }
        }

        if (minLength && containsDigit && containsUpperChar && containsSpecialChar && containsLowerChar) {
            logger.info("Password checks all the requirements.");
            return true;
        } 
        
        logger.error("Password didn't meet all the requirements.");
        return false;
    }
}
