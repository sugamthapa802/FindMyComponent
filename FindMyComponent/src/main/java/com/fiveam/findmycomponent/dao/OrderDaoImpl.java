package com.fiveam.findmycomponent.dao;
import com.fiveam.findmycomponent.entity.Order;
import com.fiveam.findmycomponent.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDaoImpl - Implementation of OrderDao interface
 * Handles all database operations for Order entity
 */
public class OrderDaoImpl implements OrderDao {

    // ========== CREATE ==========

    @Override
    public boolean save(Order order) {
        String sql = "INSERT INTO orders (order_number, user_id, total_amount, order_status, payment_status, payment_method, shipping_address, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, order.getOrderNumber());
            pstmt.setInt(2, order.getUserId());
            pstmt.setBigDecimal(3, order.getTotalAmount());
            pstmt.setString(4, order.getOrderStatus());
            pstmt.setString(5, order.getPaymentStatus());
            pstmt.setString(6, order.getPaymentMethod());
            pstmt.setString(7, order.getShippingAddress());
            pstmt.setString(8, order.getNotes());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error saving order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while saving order", e);
        }
        return false;
    }

    // ========== READ ==========

    @Override
    public Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error finding order by ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching order with id: " + id, e);
        }
        return null;
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        String sql = "SELECT * FROM orders WHERE order_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error finding order by number: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching order with number: " + orderNumber, e);
        }
        return null;
    }

    @Override
    public List<Order> findByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding orders by user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching orders for user: " + userId, e);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all orders: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByStatus(String orderStatus) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE order_status = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderStatus);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding orders by status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching orders with status: " + orderStatus, e);
        }
        return orders;
    }

    @Override
    public List<Order> findByPaymentStatus(String paymentStatus) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE payment_status = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paymentStatus);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding orders by payment status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching orders with payment status: " + paymentStatus, e);
        }
        return orders;
    }

    // ========== UPDATE ==========

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE orders SET order_number = ?, total_amount = ?, order_status = ?, payment_status = ?, payment_method = ?, shipping_address = ?, notes = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, order.getOrderNumber());
            pstmt.setBigDecimal(2, order.getTotalAmount());
            pstmt.setString(3, order.getOrderStatus());
            pstmt.setString(4, order.getPaymentStatus());
            pstmt.setString(5, order.getPaymentMethod());
            pstmt.setString(6, order.getShippingAddress());
            pstmt.setString(7, order.getNotes());
            pstmt.setInt(8, order.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating order with id: " + order.getId(), e);
        }
    }

    @Override
    public boolean updateStatus(int orderId, String orderStatus) {
        String sql = "UPDATE orders SET order_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderStatus);
            pstmt.setInt(2, orderId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating status for order: " + orderId, e);
        }
    }

    @Override
    public boolean updatePaymentStatus(int orderId, String paymentStatus) {
        String sql = "UPDATE orders SET payment_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paymentStatus);
            pstmt.setInt(2, orderId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating payment status for order: " + orderId, e);
        }
    }

    @Override
    public boolean updateTotalAmount(int orderId, double totalAmount) {
        String sql = "UPDATE orders SET total_amount = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, java.math.BigDecimal.valueOf(totalAmount));
            pstmt.setInt(2, orderId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating total amount: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating total amount for order: " + orderId, e);
        }
    }

    @Override
    public boolean markAsAccepted(int orderId) {
        String sql = "UPDATE orders SET order_status = 'accepted', accepted_at = NOW() WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error marking order as accepted: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while marking order as accepted: " + orderId, e);
        }
    }

    @Override
    public boolean markAsDispatched(int orderId) {
        String sql = "UPDATE orders SET order_status = 'dispatched', dispatched_at = NOW() WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error marking order as dispatched: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while marking order as dispatched: " + orderId, e);
        }
    }

    @Override
    public boolean markAsDelivered(int orderId) {
        String sql = "UPDATE orders SET order_status = 'delivered', delivered_at = NOW(), payment_status = 'paid' WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error marking order as delivered: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while marking order as delivered: " + orderId, e);
        }
    }

    @Override
    public boolean markAsCancelled(int orderId) {
        String sql = "UPDATE orders SET order_status = 'cancelled' WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error cancelling order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while cancelling order: " + orderId, e);
        }
    }

    // ========== COUNT ==========

    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM orders";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error getting order count: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while getting order count", e);
        }
        return 0;
    }

    @Override
    public int getCountByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error getting order count by user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while getting order count for user: " + userId, e);
        }
        return 0;
    }

    @Override
    public int getCountByStatus(String orderStatus) {
        String sql = "SELECT COUNT(*) FROM orders WHERE order_status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderStatus);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error getting order count by status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while getting order count for status: " + orderStatus, e);
        }
        return 0;
    }

    // ========== PRIVATE HELPER ==========

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setPaymentStatus(rs.getString("payment_status"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setNotes(rs.getString("notes"));
        order.setAcceptedAt(rs.getTimestamp("accepted_at"));
        order.setDispatchedAt(rs.getTimestamp("dispatched_at"));
        order.setDeliveredAt(rs.getTimestamp("delivered_at"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));
        return order;
    }
}