package com.catgame.CatGameWesbite.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.catgame.CatGameWesbite.controllers.UserController;

public class VerficaRecaptcha {

	    private static final Logger logger = LogManager.getLogger(VerficaRecaptcha.class);    


    public static final String API_URL = "https://www.google.com/recaptcha/api/siteverify";
	public static final String SECRET = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";
	private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String recaptchaResponse) throws IOException {

		if (recaptchaResponse == null || "".equals(recaptchaResponse)) {
			logger.info("Recaptcha wasnt provided or is null.");		
			return false;
		}
        try {
			URL url = URI.create(API_URL).toURL();
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String postParams = "secret=" + SECRET + "&response="
			+ recaptchaResponse;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				logger.info("Successfully connect with google api.");
				StringBuilder response = new StringBuilder();
				try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
        		}

			JSONObject jsonResponse = new JSONObject(response.toString());
			System.out.println("JSON RESPONSE \n" + jsonResponse);
			logger.info("Parsed Json with success");
			con.disconnect();		
			return jsonResponse.getBoolean("success");	
			} else { 
				logger.error("ERROR, response code: " + responseCode);
				return false;
			}
   		}
		   catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
}