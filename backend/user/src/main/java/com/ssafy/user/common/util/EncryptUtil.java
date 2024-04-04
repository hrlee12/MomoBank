package com.ssafy.user.common.util;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptUtil {

    private String alg = "HmacSHA256";
    @Value("${encrypt.secret-key}")
    private String aesSecretKey;
    private final IvParameterSpec iv = new IvParameterSpec(new byte[16]);
    private final String aesAlg = "AES/CBC/PKCS5Padding";



    public String getRandomKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    public String hashEncrypt(String word, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), alg);

        Mac hasher = Mac.getInstance(alg);
        hasher.init(secretKey);
        byte[] hash = hasher.doFinal(word.getBytes());
        String hashed = HexUtils.toHexString(hash);

        return hashed;
    }



    public String aesEncrypt(String s) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(aesAlg);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(s.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encrypted);
    }


    public String aesDecrypt(String s) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(aesAlg);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(s));

        return new String(decrypted, "UTF-8");
    }



}
