package com.fiveam.findmycomponent.entity;


import java.util.Date;

/**
 * User entity - Represents system users (Admin, Seller, Buyer)
 * Maps to 'users' table in database
 */
public class User {

    // Fields matching database columns
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private int roleId;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean isActive;
    private boolean isEmailVerified;
    private Date lastLogin;

    // Default constructor
    public User() {}

    // Constructor for registration
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = Role.ROLE_BUYER;  // Default to BUYER (3)
        this.isActive = true;
        this.isEmailVerified = false;
    }

    // Constructor with role
    public User(String username, String email, String passwordHash, int roleId) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.isActive = true;
        this.isEmailVerified = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    // Helper Methods
    public boolean isAdmin() {
        return this.roleId == Role.ROLE_ADMIN;
    }

    public boolean isSeller() {
        return this.roleId == Role.ROLE_SELLER;
    }

    public boolean isBuyer() {
        return this.roleId == Role.ROLE_BUYER;
    }

    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return username;
    }
}