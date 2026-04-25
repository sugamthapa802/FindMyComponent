package com.fiveam.findmycomponent.dao;


import com.fiveam.findmycomponent.entity.Role;
import com.fiveam.findmycomponent.utils.DatabaseConnection;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

/**
 * RoleDAOImpl - Implementation of RoleDAO interface
 * Handles all database operations for Role entity
 */
public class RoleDaoImpl implements RoleDao {

    @Override
    public Role findById(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRole(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error finding role by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Role findByName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRole(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error finding role by name: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error finding all roles: " + e.getMessage());
        }
        return roles;
    }

    @Override
    public boolean save(Role role) {
        String sql = "INSERT INTO roles (name, description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, role.getName());
            pstmt.setString(2, role.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    role.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error saving role: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting role: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM roles WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking role existence: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Role getDefaultRole() {
        return findById(Role.ROLE_BUYER);
    }

    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM roles";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error getting role count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Private helper method to map ResultSet to Role object
     */
    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        role.setCreatedAt(rs.getTimestamp("created_at"));
        return role;
    }
}