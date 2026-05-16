package com.fiveam.findmycomponent.dao;

import com.fiveam.findmycomponent.entity.Order;
import java.util.List;

/**
 * OrderDao Interface - Defines database operations for Order entity
 */
public interface OrderDao {

    // ========== CREATE ==========
    boolean save(Order order);

    // ========== READ ==========
    Order findById(int id);
    Order findByOrderNumber(String orderNumber);
    List<Order> findByUserId(int userId);
    List<Order> findAll();
    List<Order> findByStatus(String orderStatus);
    List<Order> findByPaymentStatus(String paymentStatus);

    // ========== UPDATE ==========
    boolean update(Order order);
    boolean updateStatus(int orderId, String orderStatus);
    boolean updatePaymentStatus(int orderId, String paymentStatus);
    boolean updateTotalAmount(int orderId, double totalAmount);

    // Update with timestamps
    boolean markAsAccepted(int orderId);
    boolean markAsDispatched(int orderId);
    boolean markAsDelivered(int orderId);
    boolean markAsCancelled(int orderId);

    // ========== COUNT ==========
    int getCount();
    int getCountByUserId(int userId);
    int getCountByStatus(String orderStatus);
}