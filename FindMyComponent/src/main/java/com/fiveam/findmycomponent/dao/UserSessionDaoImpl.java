package com.fiveam.findmycomponent.dao;
import com.fiveam.findmycomponent.entity.UserSession;
import com.fiveam.findmycomponent.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserSessionDAOImpl - Implementation of UserSessionDAO interface
 * Handles all database operations for UserSession entity
 */
public class UserSessionDaoImpl implements UserSessionDao {

    @Override
    public boolean save(UserSession session) {
        String sql = "INSERT INTO user_sessions (user_id, session_token, expires_at) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, session.getUserId());
            pstmt.setString(2, session.getSessionToken());
            pstmt.setTimestamp(3, new Timestamp(session.getExpiresAt().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error saving user session: " + e.getMessage());
        }
        return false;
    }

    @Override
    public UserSession findByToken(String sessionToken) {
        String sql = "SELECT * FROM user_sessions WHERE session_token = ? AND expires_at > NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sessionToken);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUserSession(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error finding session by token: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserSession> findByUserId(int userId) {
        List<UserSession> sessions = new ArrayList<>();
        String sql = "SELECT * FROM user_sessions WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sessions.add(mapResultSetToUserSession(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error finding sessions by user ID: " + e.getMessage());
        }
        return sessions;
    }

    @Override
    public boolean deleteByToken(String sessionToken) {
        String sql = "DELETE FROM user_sessions WHERE session_token = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sessionToken);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting session by token: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByUserId(int userId) {
        String sql = "DELETE FROM user_sessions WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting sessions by user ID: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int deleteExpiredSessions() {
        String sql = "DELETE FROM user_sessions WHERE expires_at < NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int affectedRows = pstmt.executeUpdate();
            return affectedRows;

        } catch (SQLException e) {
            System.out.println("Error deleting expired sessions: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean existsByToken(String sessionToken) {
        String sql = "SELECT COUNT(*) FROM user_sessions WHERE session_token = ? AND expires_at > NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sessionToken);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking session existence: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int getActiveSessionCount(int userId) {
        String sql = "SELECT COUNT(*) FROM user_sessions WHERE user_id = ? AND expires_at > NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error getting active session count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Private helper method to map ResultSet to UserSession object
     */
    private UserSession mapResultSetToUserSession(ResultSet rs) throws SQLException {
        UserSession session = new UserSession();
        session.setId(rs.getInt("id"));
        session.setUserId(rs.getInt("user_id"));
        session.setSessionToken(rs.getString("session_token"));
        session.setExpiresAt(rs.getTimestamp("expires_at"));
        session.setCreatedAt(rs.getTimestamp("created_at"));
        return session;
    }
}