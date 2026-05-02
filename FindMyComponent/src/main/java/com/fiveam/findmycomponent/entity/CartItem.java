package com.fiveam.findmycomponent.entity;


import java.util.Date;

/**
 * CartItem entity - Represents items in a user's shopping cart
 * Maps to 'cart_items' table in database
 * One cart item = one product + quantity for a specific user
 */
public class CartItem {

    // Fields matching database columns
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private Date addedAt;
    private Date updatedAt;

    // For display purposes (joined from products table - not stored in DB)
    private String productName;
    private String productBrand;
    private double productPrice;
    private String productImageUrl;
    private int productStockQuantity;
    private boolean productIsActive;

    // Default constructor
    public CartItem() {}

    // Constructor for adding item to cart
    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Full constructor
    public CartItem(int id, int userId, int productId, int quantity, Date addedAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Product display fields (from joined query)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public int getProductStockQuantity() {
        return productStockQuantity;
    }

    public void setProductStockQuantity(int productStockQuantity) {
        this.productStockQuantity = productStockQuantity;
    }

    public boolean isProductIsActive() {
        return productIsActive;
    }

    public void setProductIsActive(boolean productIsActive) {
        this.productIsActive = productIsActive;
    }

    // ========== Helper Methods ==========

    /**
     * Calculates total price for this cart item (price * quantity)
     */
    public double getTotalPrice() {
        return productPrice * quantity;
    }

    /**
     * Validates that quantity is positive
     */
    public boolean isValidQuantity() {
        return quantity > 0;
    }

    /**
     * Validates that product is available for purchase
     */
    public boolean isProductAvailable() {
        return productIsActive && productStockQuantity >= quantity;
    }

    /**
     * Increases quantity by 1
     */
    public void incrementQuantity() {
        quantity++;
    }

    /**
     * Decreases quantity by 1 (minimum 1)
     */
    public void decrementQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    /**
     * Updates quantity to specified value
     */
    public boolean updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}