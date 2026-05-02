package com.fiveam.findmycomponent.dao;
import com.fiveam.findmycomponent.entity.CartItem;
import com.fiveam.findmycomponent.entity.Product;
import com.fiveam.findmycomponent.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CartDaoImpl - Implementation of CartDao interface
 * Handles all shopping cart database operations
 */
public class CartDaoImpl implements CartDao {

    private ProductDao productDao;

    public CartDaoImpl() {
        this.productDao = new ProductDaoImpl();
    }

    // ========== READ ==========

    @Override
    public List<CartItem> getCartByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.*, " +
                "p.name as product_name, " +
                "p.brand as product_brand, " +
                "p.price as product_price, " +
                "p.main_image_url as product_image_url, " +
                "p.stock_quantity as product_stock_quantity, " +
                "p.is_active as product_is_active " +
                "FROM cart_items ci " +
                "JOIN products p ON ci.product_id = p.id " +
                "WHERE ci.user_id = ? " +
                "ORDER BY ci.added_at";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CartItem item = mapResultSetToCartItem(rs);
                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Error getting cart by user ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching cart for user: " + userId, e);
        }
        return cartItems;
    }

    @Override
    public int getCartItemCount(int userId) {
        String sql = "SELECT SUM(quantity) as total FROM cart_items WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting cart item count: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while getting cart count for user: " + userId, e);
        }
        return 0;
    }

    @Override
    public double getCartTotal(int userId) {
        String sql = "SELECT SUM(p.price * ci.quantity) as total " +
                "FROM cart_items ci " +
                "JOIN products p ON ci.product_id = p.id " +
                "WHERE ci.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting cart total: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while getting cart total for user: " + userId, e);
        }
        return 0.0;
    }

    // ========== WRITE ==========

    @Override
    public boolean addToCart(int userId, int productId, int quantity) {
        // Validation
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Check if product exists and is active
        Product product = productDao.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with id: " + productId);
        }
        if (!product.isActive()) {
            throw new IllegalStateException("Product is not available for purchase");
        }

        String sql = "INSERT INTO cart_items (user_id, product_id, quantity) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, quantity);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while adding to cart", e);
        }
    }

    @Override
    public boolean updateQuantity(int userId, int productId, int quantity) {
        // Validation
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, productId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating cart quantity: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating cart quantity", e);
        }
    }

    @Override
    public boolean removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error removing from cart: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while removing from cart", e);
        }
    }

    @Override
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return true; // Clearing empty cart is still successful

        } catch (SQLException e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while clearing cart", e);
        }
    }

    // ========== PRIVATE HELPER ==========

    /**
     * Maps ResultSet to CartItem object (includes joined product fields)
     */
    private CartItem mapResultSetToCartItem(ResultSet rs) throws SQLException {
        CartItem item = new CartItem();

        // Cart item fields
        item.setId(rs.getInt("id"));
        item.setUserId(rs.getInt("user_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setAddedAt(rs.getTimestamp("added_at"));
        item.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Product joined fields
        item.setProductName(rs.getString("product_name"));
        item.setProductBrand(rs.getString("product_brand"));
        item.setProductPrice(rs.getDouble("product_price"));
        item.setProductImageUrl(rs.getString("product_image_url"));
        item.setProductStockQuantity(rs.getInt("product_stock_quantity"));
        item.setProductIsActive(rs.getBoolean("product_is_active"));

        return item;
    }
}