package com.fiveam.findmycomponent.dao;

import com.fiveam.findmycomponent.entity.Role;
import java.util.List;

/**
 * RoleDAO Interface - Defines database operations for Role entity
 */
public interface RoleDao {

    /**
     * Find role by ID
     * @param id role ID (1=ADMIN, 2=SELLER, 3=BUYER)
     * @return Role object or null if not found
     */
    Role findById(int id);

    /**
     * Find role by name
     * @param name role name ("ADMIN", "SELLER", "BUYER")
     * @return Role object or null if not found
     */
    Role findByName(String name);

    /**
     * Get all roles
     * @return List of all roles (empty list if none)
     */
    List<Role> findAll();

    /**
     * Save a new role (database auto-generates createdAt)
     * @param role Role object to save
     * @return true if successful, false otherwise
     */
    boolean save(Role role);

    /**
     * Delete role by ID
     * @param id role ID to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int id);

    /**
     * Check if role exists by ID
     * @param id role ID to check
     * @return true if exists, false otherwise
     */
    boolean existsById(int id);

    /**
     * Get default role (BUYER - ID = 3)
     * @return Role object for BUYER or null if not found
     */
    Role getDefaultRole();

    /**
     * Get total number of roles
     * @return count of roles
     */
    int getCount();
}
