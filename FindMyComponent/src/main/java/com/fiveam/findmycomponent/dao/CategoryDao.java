package com.fiveam.findmycomponent.dao;

import com.fiveam.findmycomponent.entity.Category;
import java.util.List;

/**
 * CategoryDAO Interface - Defines database operations for Category entity
 * Only ADMIN should have access to create/update/delete operations
 */
public interface CategoryDao {

    // Create
    boolean save(Category category);

    // Read
    Category findById(int id);
    Category findByName(String name);
    List<Category> findAll();
    List<Category> findAllActive();

    // Update
    boolean update(Category category);

    // Delete (Soft delete - set is_active = false)
    boolean delete(int id);
    boolean activate(int id);

    // Check existence
    boolean existsByName(String name);

    // Count
    int getCount();
    int getActiveCount();
}
