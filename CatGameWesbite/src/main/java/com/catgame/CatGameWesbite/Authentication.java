package com.catgame.CatGameWesbite;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.taimos.totp.TOTP;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class Authentication {

    private static final Logger logger = LogManager.getLogger(Authentication.class);


    String secretKey;
    Properties properties = new Properties();

    public Authentication() {
        secretKey = getSecretKey();
    }

    public String getSecretKey() {
        String key = null;
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            key = properties.getProperty("SECRET_KEY");
            if (key != null) {
                logger.info("Secret key get successfully.");
            }
        }   catch (IOException ex){
            logger.error(ex.getMessage());
        }
        return key;
    }


    public String getCode () {
        secretKey = getSecretKey();
        String lastCode = null; 
        while (true) {
            String code = getTOTPCode(secretKey);
            if (!code.equals(lastCode)) {
                logger.info("Code created success.");
                return code;
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            };
        }
    }

	public static String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

}
