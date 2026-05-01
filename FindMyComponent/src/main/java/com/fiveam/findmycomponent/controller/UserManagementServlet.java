package com.fiveam.findmycomponent.controller;

import com.fiveam.findmycomponent.dao.RoleDao;
import com.fiveam.findmycomponent.dao.RoleDaoImpl;
import com.fiveam.findmycomponent.dao.UserDao;
import com.fiveam.findmycomponent.dao.UserDaoImpl;
import com.fiveam.findmycomponent.entity.Role;
import com.fiveam.findmycomponent.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * UserManagementServlet - Handles user management for ADMIN
 * URL: /admin/users
 * Only ADMIN can access this servlet
 *
 * GET requests:
 * - No action: List all users
 * - action=edit&id=X: Show edit form for user
 *
 * POST requests:
 * - action=update: Update user details (with self-protection)
 */
@WebServlet("/admin/users")
public class UserManagementServlet extends HttpServlet {

    private UserDao userDAO;
    private RoleDao roleDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDaoImpl();
        roleDAO = new RoleDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Temporary ADMIN check (will be replaced by filter later)
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loggedInUser == null || loggedInUser.getRoleId() != Role.ROLE_ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied. Admin only.");
            return;
        }

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Show edit form for user
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    User user = userDAO.findById(id);
                    if (user != null) {
                        request.setAttribute("editUser", user);

                        // Check if editing self
                        boolean isEditingSelf = (user.getId() == loggedInUser.getId());
                        request.setAttribute("isEditingSelf", isEditingSelf);

                        // Get all roles for dropdown
                        List<Role> roles = roleDAO.findAll();
                        request.setAttribute("roles", roles);

                        request.getRequestDispatcher("/WEB-INF/admin/user-form.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    // Invalid ID, redirect to list
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin/users");

        } else {
            // Default: List all users
            List<User> users = userDAO.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Temporary ADMIN check (will be replaced by filter later)
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loggedInUser == null || loggedInUser.getRoleId() != Role.ROLE_ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied. Admin only.");
            return;
        }

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            // Get all parameters
            String idParam = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String roleIdParam = request.getParameter("roleId");
            String isActiveParam = request.getParameter("isActive");

            // Validation
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            }

            try {
                int id = Integer.parseInt(idParam);
                User user = userDAO.findById(id);

                if (user != null) {
                    // Always update basic info
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhone(phone);

                    // Check if editing self
                    boolean isEditingSelf = (user.getId() == loggedInUser.getId());

                    if (isEditingSelf) {
                        // Self edit: prevent role change and deactivation
                        user.setRoleId(Role.ROLE_ADMIN);  // Force ADMIN role
                        user.setActive(true);              // Force active status
                    } else {
                        // Editing others: allow role and status change
                        if (roleIdParam != null && !roleIdParam.isEmpty()) {
                            int roleId = Integer.parseInt(roleIdParam);
                            user.setRoleId(roleId);
                        }

                        if (isActiveParam != null) {
                            user.setActive(true);
                        } else {
                            user.setActive(false);
                        }
                    }

                    // Perform update
                    boolean updated = userDAO.update(user);

                    if (!updated) {
                        request.setAttribute("error", "Failed to update user");
                    }
                }

            } catch (NumberFormatException e) {
                // Invalid ID format
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}