package com.catgame.CatGameWesbite.services;

import java.io.IOException;

public class VerficaRecaptcha {

    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";
	private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String gRecaptchaResponse) throws IOException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
    }
    return false;
}
