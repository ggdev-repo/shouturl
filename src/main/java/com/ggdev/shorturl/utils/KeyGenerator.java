package com.ggdev.shorturl.utils;

import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator {

    private final char[] base62Array;

    @Value("${env.service.length:7}")
    private static final int SHORT_URL_LENGTH = 7; // 단축 URL 길이

    // 생성자
    public KeyGenerator() {
        this.base62Array = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    }

    // SHA 해시를 생성하는 메소드
    public String hashSHA(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Base62 인코딩을 사용하여 단축 URL을 생성하는 메소드
    public String encodingBASE62(String hash) {
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = (hash.charAt(i) % base62Array.length);
            shortURL.append(base62Array[index]);
        }
        return shortURL.toString();
    }

}