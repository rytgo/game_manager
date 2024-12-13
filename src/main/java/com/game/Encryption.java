package com.game;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private static final String ALG = "AES";
    private static final byte[] KEY = "KEYSKEYSKEYSKEYS".getBytes();

    public static String encrypt(String data){
        try{
            SecretKey secretKey = new SecretKeySpec(KEY, ALG);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e){
            throw new RuntimeException("Error encrypting data", e);
        }
    }
    public static String decrypt(String data){
        try{
            SecretKey secretKey = new SecretKeySpec(KEY, ALG);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decrypted);
        } catch (Exception e){
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
