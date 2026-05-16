package com.fiveam.findmycomponent.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order entity - Represents an order placed by a buyer
 * Maps to 'orders' table in database
 */
public class Order {

    // Fields matching database columns
    private int id;
    private String orderNumber;
    private int userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMethod;
    private String shippingAddress;
    private String notes;
    private Date acceptedAt;
    private Date dispatchedAt;
    private Date deliveredAt;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public Order() {}

    // Constructor for creating new order
    public Order(int userId, BigDecimal totalAmount, String shippingAddress) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.orderStatus = "pending";
        this.paymentStatus = "pending";
        this.paymentMethod = "COD";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Date getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Date dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
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

    // Helper methods
    public boolean isPending() {
        return "pending".equals(orderStatus);
    }

    public boolean isAccepted() {
        return "accepted".equals(orderStatus);
    }

    public boolean isDispatched() {
        return "dispatched".equals(orderStatus);
    }

    public boolean isDelivered() {
        return "delivered".equals(orderStatus);
    }

    public boolean isCancelled() {
        return "cancelled".equals(orderStatus);
    }

    public boolean isPaid() {
        return "paid".equals(paymentStatus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}