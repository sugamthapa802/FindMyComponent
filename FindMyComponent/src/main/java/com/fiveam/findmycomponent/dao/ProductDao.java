package com.fiveam.findmycomponent.dao;

import com.fiveam.findmycomponent.entity.Product;
import java.util.List;

/**
 * ProductDao Interface - Defines database operations for Product entity
 */
public interface ProductDao {

    // ========== CREATE ==========
    boolean save(Product product);

    // ========== READ ==========
    Product findById(int id);
    List<Product> findAll();
    List<Product> findAllActive();
    List<Product> findBySellerId(int sellerId);
    List<Product> findByCategoryId(int categoryId);

    // ========== UPDATE ==========
    boolean update(Product product);
    boolean updateStock(int productId, int quantityChange);
    boolean updateStatus(int productId, boolean isActive);

    // ========== DELETE (Soft Delete) ==========
    boolean delete(int id);

    // ========== CHECK EXISTENCE ==========
    boolean existsByNameForSeller(String name, int sellerId);
}