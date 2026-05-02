package com.fiveam.findmycomponent.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Product entity - Represents PC hardware products listed by sellers
 * Maps to 'products' table in database
 */
public class Product {

    // Fields matching database columns
    private int id;
    private int sellerId;
    private int categoryId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String mainImageUrl;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public Product() {}

    // Constructor for creating new product
    public Product(String name, BigDecimal price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isActive = true;
    }

    // Full constructor
    public Product(int id, int sellerId, int categoryId, String name, String brand,
                   String description, BigDecimal price, int stockQuantity,
                   String mainImageUrl, boolean isActive, Date createdAt, Date updatedAt) {
        this.id = id;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.mainImageUrl = mainImageUrl;
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

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
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

    // ========== Helper Methods ==========

    /**
     * Validates that price is greater than zero
     */
    public boolean isValidPrice() {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Validates that stock quantity is not negative
     */
    public boolean isValidStock() {
        return stockQuantity >= 0;
    }

    /**
     * Validates that name is not empty and within length limit
     */
    public boolean isValidName() {
        return name != null && !name.trim().isEmpty() && name.length() <= 200;
    }

    /**
     * Validates all product fields
     */
    public boolean isValid() {
        return isValidName() && isValidPrice() && isValidStock() && sellerId > 0 && categoryId > 0;
    }

    /**
     * Gets total value of current stock (price * quantity)
     */
    public BigDecimal getStockValue() {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(stockQuantity));
    }

    /**
     * Reduces stock by given quantity (for when order is placed)
     */
    public boolean reduceStock(int quantity) {
        if (quantity <= 0 || quantity > stockQuantity) {
            return false;
        }
        stockQuantity -= quantity;
        return true;
    }

    /**
     * Increases stock by given quantity (for restocking)
     */
    public void increaseStock(int quantity) {
        if (quantity > 0) {
            stockQuantity += quantity;
        }
    }

    /**
     * Checks if product is in stock
     */
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    /**
     * Checks if product is available for purchase (active AND in stock)
     */
    public boolean isAvailable() {
        return isActive && isInStock();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", isActive=" + isActive +
                '}';
    }
}