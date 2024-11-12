package edu.mj.mart.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import kotlin.text.Charsets;

public class AESEncryption {

    private static final String SECRET_KEY = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ=";
    private static final String SALT = "QWlGNHNhMTJTQWZ2bGhpV3U="; // base64 decode => AiF4sa12SAfvlhiWu
    private static final String IV = "bVQzNFNhRkQ1Njc4UUFaWA=="; // base64 decode => mT34SaFD5678QAZX

    public static String encrypt(String strToEncrypt) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decode(IV, Base64.DEFAULT));

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), Base64.decode(SALT, Base64.DEFAULT), 10000, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return Base64.encodeToString(cipher.doFinal(strToEncrypt.getBytes(Charsets.UTF_8)), Base64.DEFAULT);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return "";
    }

    public static String decrypt(String strToDecrypt) {
        try {

            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decode(IV, Base64.DEFAULT));

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), Base64.decode(SALT, Base64.DEFAULT), 10000, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: $e");
        }
        return "";
    }
}
