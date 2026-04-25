package com.fiveam.findmycomponent.entity;


import java.util.Date;

/**
 * Role entity - Represents user roles (ADMIN, SELLER, BUYER)
 * Maps to 'roles' table in database
 */
public class Role {

    // Role ID Constants (match database values)
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_SELLER = 2;
    public static final int ROLE_BUYER = 3;

    // Role Name Constants
    public static final String ADMIN = "ADMIN";
    public static final String SELLER = "SELLER";
    public static final String BUYER = "BUYER";

    // Fields matching database columns
    private int id;
    private String name;
    private String description;
    private Date createdAt;

    // Default constructor
    public Role() {}

    // Constructor with ID and name
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor for creating new roles
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Full constructor
    public Role(int id, String name, String description, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Helper methods
    public boolean isAdmin() {
        return this.id == ROLE_ADMIN;
    }

    public boolean isSeller() {
        return this.id == ROLE_SELLER;
    }

    public boolean isBuyer() {
        return this.id == ROLE_BUYER;
    }
}