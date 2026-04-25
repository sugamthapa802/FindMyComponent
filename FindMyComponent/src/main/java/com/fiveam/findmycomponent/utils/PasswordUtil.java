package com.fiveam.findmycomponent.utils;
import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil - Handles password hashing and verification using BCrypt
 * BCrypt automatically handles salt generation and storage
 */
public class PasswordUtil {

    // BCrypt workload factor (2^10 rounds = good security without performance hit)
    private static final int WORKLOAD_FACTOR = 10;

    /**
     * Hashes a plain text password using BCrypt
     * @param plainPassword the plain text password
     * @return BCrypt hash (includes salt and workload factor)
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            return null;
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORKLOAD_FACTOR));
    }

    /**
     * Verifies a plain password against a stored BCrypt hash
     * @param plainPassword the plain text password to check
     * @param hashedPassword the stored BCrypt hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}