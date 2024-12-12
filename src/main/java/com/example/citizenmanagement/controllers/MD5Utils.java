package com.example.citizenmanagement.controllers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/*
SQL: UPDATE NGUOIQUANLY
SET MATKHAU = CONVERT(VARCHAR(64), HASHBYTES('MD5', MATKHAU), 2)
Hàm chuyển pass MD5 sang bình thường
 */
public class MD5Utils {
    public static String hashPassword(String password) {
        try {
            // Initialize MD5 hashing
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Update the hash with UTF-16LE bytes (to match SQL Server)
            md.update(password.getBytes(StandardCharsets.UTF_16LE));

            // Get the hashed byte array
            byte[] byteData = md.digest();

            // Convert the byte array to a hexadecimal string (uppercase)
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02X", b)); // Uppercase hex
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
// Test
//    public static void main(String[] args) {
//        // Input password
//        String inputPassword = "123";
//
//        // Hash the input password
//        String hashedPassword = MD5Utils.hashPassword(inputPassword);
//
//        // Expected MD5 hash for comparison
//        String expectedHash = "5FA285E1BEBE0A6623E33AFC04A1FBD5";
//
//        // Output the results
//        if (hashedPassword != null && hashedPassword.equals(expectedHash)) {
//            System.out.println("Test Passed: Hash matches the expected value!");
//        } else {
//            System.out.println("Test Failed: Hash does not match the expected value.");
//            System.out.println("Generated Hash: " + hashedPassword);
//        }
//    }
}

