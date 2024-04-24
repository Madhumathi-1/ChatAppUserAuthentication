package com.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordHashing {

//    public static String hashPassword(String line) {
//        try {
//            MessageDigest msg = MessageDigest.getInstance("SHA-512");
//            byte[] msgdigest = msg.digest(line.getBytes());
//            BigInteger bigInt = new BigInteger(1, msgdigest);
//            String hashedPassword = bigInt.toString(16);
//
//            while (hashedPassword.length() < 128) {
//                hashedPassword = "0" + hashedPassword;
//            }
//
//            return hashedPassword;
//        } catch (Exception ee) {
//            System.out.println("Error hashing password: " + ee.getMessage());
//            return null; 
//        }

	public static String hashPassword(String line) {
		String text = null;
		try {
			MessageDigest message;
			message = MessageDigest.getInstance("SHA-512");
			byte[] messageDigest = message.digest(line.getBytes());
			BigInteger r = new BigInteger(1, messageDigest);
			text = r.toString(16);
			while (text.length() < 128) {
				text = "0" + text;
			}
		} catch (Exception ee) {
			System.out.println(ee);
		}
		return text;
	}
}
