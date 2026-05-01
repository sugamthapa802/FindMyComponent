package com.fiveam.findmycomponent.entity;

import java.util.Date;

/**
 * Category entity - Represents product categories
 * Maps to 'categories' table in database
 * Only ADMIN can manage categories
 */
public class Category {

    // Fields matching database columns
    private int id;
    private String name;
    private String description;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public Category() {}

    // Constructor for creating new category
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.isActive = true;
    }

    // Constructor with all fields
    public Category(int id, String name, String description, boolean isActive, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}