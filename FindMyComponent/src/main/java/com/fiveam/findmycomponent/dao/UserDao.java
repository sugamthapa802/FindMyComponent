package com.fiveam.findmycomponent.dao;


import com.fiveam.findmycomponent.entity.User;
import java.util.List;

/**
 * UserDAO Interface - Defines database operations for User entity
 */
public interface UserDao {

    // Create / Save
    boolean save(User user);

    // Read / Find
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);

    // Update
    boolean updateLastLogin(int userId);
    boolean updateRole(int userId, int newRoleId);

    // Delete
    boolean delete(int id);

    // Check existence
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Authentication
//    User authenticate(String username, String password);

    // Count
    int getCount();
}