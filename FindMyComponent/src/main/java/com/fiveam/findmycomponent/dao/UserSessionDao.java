package com.fiveam.findmycomponent.dao;


import com.fiveam.findmycomponent.entity.UserSession;
import java.util.List;

/**
 * UserSessionDAO Interface - Defines database operations for UserSession entity
 * Handles "Remember Me" session tokens
 */
public interface UserSessionDao {

    /**
     * Save a new session token
     * @param session UserSession object to save
     * @return true if successful, false otherwise
     */
    boolean save(UserSession session);

    /**
     * Find session by token (only returns non-expired sessions)
     * @param sessionToken the token to look up
     * @return UserSession object or null if not found or expired
     */
    UserSession findByToken(String sessionToken);

    /**
     * Find all sessions for a specific user
     * @param userId the user ID
     * @return List of UserSession objects (empty list if none)
     */
    List<UserSession> findByUserId(int userId);

    /**
     * Delete session by token
     * @param sessionToken the token to delete
     * @return true if successful, false otherwise
     */
    boolean deleteByToken(String sessionToken);

    /**
     * Delete all sessions for a specific user
     * @param userId the user ID
     * @return true if successful, false otherwise
     */
    boolean deleteByUserId(int userId);

    /**
     * Delete all expired sessions
     * @return number of rows deleted, or -1 if error
     */
    int deleteExpiredSessions();

    /**
     * Check if a token exists and is not expired
     * @param sessionToken the token to check
     * @return true if exists and valid, false otherwise
     */
    boolean existsByToken(String sessionToken);

    /**
     * Get count of active (non-expired) sessions for a user
     * @param userId the user ID
     * @return count of active sessions
     */
    int getActiveSessionCount(int userId);
}