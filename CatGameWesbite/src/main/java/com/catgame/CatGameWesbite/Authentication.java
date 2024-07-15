package com.catgame.CatGameWesbite;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import de.taimos.totp.TOTP;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class Authentication {

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
            System.out.println("Loaded secret key: " + secretKey);
        }   catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("THIS IS THE KEY " + key);
        return key;
    }


    public String getCode () {
        System.out.println("THIS IS THE GET SECRET KEY: " +  getSecretKey());
        secretKey = getSecretKey();
        String lastCode = null; 
        while (true) {
            String code = getTOTPCode(secretKey);
            if (!code.equals(lastCode)) {
                System.out.println(code);
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {};
        }
    }

	public static String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

}
