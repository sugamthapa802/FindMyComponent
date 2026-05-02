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
 *
 * Admin can ONLY:
 * - View all users
 * - Edit: first_name, last_name, phone, is_active
 * - CANNOT create new users
 * - CANNOT delete users (use is_active instead)
 * - CANNOT change username, email, role_id
 */
@WebServlet("/admin/users")
public class UserManagementServlet extends HttpServlet {

    private UserDao userDao;
    private RoleDao roleDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
        roleDao = new RoleDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Show edit form for existing user
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    User user = userDao.findById(id);
                    if (user != null) {
                        request.setAttribute("editUser", user);

                        // Check if editing self
                        HttpSession session = request.getSession(false);
                        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;
                        boolean isEditingSelf = (loggedInUser != null && user.getId() == loggedInUser.getId());
                        request.setAttribute("isEditingSelf", isEditingSelf);

                        // Get role name for display (read-only)
                        Role userRole = roleDao.findById(user.getRoleId());
                        request.setAttribute("roleName", userRole != null ? userRole.getName() : "UNKNOWN");

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
            List<User> users = userDao.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loggedInUser == null || loggedInUser.getRoleId() != Role.ROLE_ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            // Update existing user - ONLY first_name, last_name, phone, is_active
            String idParam = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String isActiveParam = request.getParameter("isActive");

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            }

            try {
                int id = Integer.parseInt(idParam);
                User user = userDao.findById(id);

                if (user != null) {
                    // Update basic info
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhone(phone);

                    // Check if editing self
                    boolean isEditingSelf = (loggedInUser != null && user.getId() == loggedInUser.getId());

                    if (isEditingSelf) {
                        // Self edit: cannot deactivate self
                        user.setActive(true);
                    } else {
                        // Editing others: allow status change only
                        user.setActive("true".equals(isActiveParam));
                    }

                    // IMPORTANT: role_id, username, email are NOT updated
                    userDao.update(user);
                }

            } catch (NumberFormatException e) {
                // Invalid ID format
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}