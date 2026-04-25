package com.fiveam.findmycomponent.entity;

import java.util.Date;

/**
 * UserSession entity - Stores "Remember Me" session tokens for persistent login
 * Maps to 'user_sessions' table in database
 */
public class UserSession {

    // Fields matching database columns
    private int id;
    private int userId;
    private String sessionToken;
    private Date expiresAt;
    private Date createdAt;

    // Default constructor
    public UserSession() {}

    // Constructor for creating new sessions
    public UserSession(int userId, String sessionToken, Date expiresAt) {
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Helper method to check if token has expired
    public boolean isExpired() {
        if (expiresAt == null) {
            return true;
        }
        return new Date().after(expiresAt);
    }
}
