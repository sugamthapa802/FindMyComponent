package com.fiveam.findmycomponent.dao;

import com.fiveam.findmycomponent.entity.Product;
import com.fiveam.findmycomponent.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDaoImpl - Implementation of ProductDao interface
 * Handles all database operations for Product entity
 */
public class ProductDaoImpl implements ProductDao {

    // ========== CREATE ==========

    @Override
    public boolean save(Product product) {
        // Validation
        if (!product.isValid()) {
            throw new IllegalArgumentException("Invalid product data: " +
                    (!product.isValidName() ? "Name invalid; " : "") +
                    (!product.isValidPrice() ? "Price must be > 0; " : "") +
                    (!product.isValidStock() ? "Stock must be >= 0; " : ""));
        }

        String sql = "INSERT INTO products (seller_id, category_id, name, brand, description, price, stock_quantity, main_image_url, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, product.getSellerId());
            pstmt.setInt(2, product.getCategoryId());
            pstmt.setString(3, product.getName());
            pstmt.setString(4, product.getBrand());
            pstmt.setString(5, product.getDescription());
            pstmt.setBigDecimal(6, product.getPrice());
            pstmt.setInt(7, product.getStockQuantity());
            pstmt.setString(8, product.getMainImageUrl());
            pstmt.setBoolean(9, product.isActive());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error saving product: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while saving product", e);
        }
        return false;
    }

    // ========== READ ==========

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error finding product by ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching product with id: " + id, e);
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all products: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching all products", e);
        }
        return products;
    }

    @Override
    public List<Product> findAllActive() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM products p " +
                "JOIN users u ON p.seller_id = u.id " +
                "WHERE p.is_active = TRUE AND u.is_active = TRUE " +
                "ORDER BY p.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding active products: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching active products", e);
        }
        return products;
    }

    @Override
    public List<Product> findBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding products by seller: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching products for seller: " + sellerId, e);
        }
        return products;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ? AND is_active = TRUE ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding products by category: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching products for category: " + categoryId, e);
        }
        return products;
    }

    // ========== UPDATE ==========

    @Override
    public boolean update(Product product) {
        // Validation
        if (!product.isValid()) {
            throw new IllegalArgumentException("Invalid product data for update");
        }

        String sql = "UPDATE products SET name = ?, brand = ?, description = ?, price = ?, stock_quantity = ?, " +
                "main_image_url = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setString(3, product.getDescription());
            pstmt.setBigDecimal(4, product.getPrice());
            pstmt.setInt(5, product.getStockQuantity());
            pstmt.setString(6, product.getMainImageUrl());
            pstmt.setInt(7, product.getCategoryId());
            pstmt.setInt(8, product.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating product with id: " + product.getId(), e);
        }
    }

    @Override
    public boolean updateStock(int productId, int quantityChange) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity + ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityChange);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating stock for product: " + productId, e);
        }
    }

    @Override
    public boolean updateStatus(int productId, boolean isActive) {
        String sql = "UPDATE products SET is_active = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isActive);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating product status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while updating status for product: " + productId, e);
        }
    }

    // ========== DELETE (Soft Delete) ==========

    @Override
    public boolean delete(int id) {
        return updateStatus(id, false);
    }

    // ========== CHECK EXISTENCE ==========

    @Override
    public boolean existsByNameForSeller(String name, int sellerId) {
        String sql = "SELECT COUNT(*) FROM products WHERE name = ? AND seller_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, sellerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking product name existence: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while checking product name", e);
        }
        return false;
    }

    // ========== PRIVATE HELPER ==========

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setSellerId(rs.getInt("seller_id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setName(rs.getString("name"));
        product.setBrand(rs.getString("brand"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setMainImageUrl(rs.getString("main_image_url"));
        product.setActive(rs.getBoolean("is_active"));
        product.setCreatedAt(rs.getTimestamp("created_at"));
        product.setUpdatedAt(rs.getTimestamp("updated_at"));
        return product;
    }
}