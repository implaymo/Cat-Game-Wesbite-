package com.catgame.CatGameWesbite;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import de.taimos.totp.TOTP;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import java.net.URLEncoder;


public class TwoFactorAuth {

    private static final Logger logger = LogManager.getLogger(TwoFactorAuth.class);


    static String secretKey;
    static Properties properties = new Properties();

    public TwoFactorAuth() {
        secretKey = getSecretKey();
    }

    public static String getSecretKey() {
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

    public static String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

    public static String getCode () {
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




    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
    try {
        return "otpauth://totp/"
                + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    
    public static void createQRCode(String barCodeData, String filePath, int height, int width)
        throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }  
}
