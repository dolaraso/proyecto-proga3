package com.cybersentinels.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptacion {
    public static String encriptarSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar: " + e.getMessage());
        }
    }

    public static boolean compararHash(String texto, String hash) {
        return encriptarSHA256(texto).equals(hash);
    }
}
