package com.fiveam.findmycomponent.controller;


import com.fiveam.findmycomponent.dao.UserDao;
import com.fiveam.findmycomponent.dao.UserDaoImpl;
import com.fiveam.findmycomponent.entity.User;
import com.fiveam.findmycomponent.entity.Role;
import com.fiveam.findmycomponent.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegisterServlet - Handles user registration
 * URL: /register
 * - GET: Displays registration form
 * - POST: Processes registration form submission
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDao userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to registration page
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String registerAsSeller = request.getParameter("registerAsSeller");

        // Trim whitespace from username and email
        if (username != null) username = username.trim();
        if (email != null) email = email.trim();

        // Validation: Check required fields
        if (username == null || username.isEmpty()) {
            request.setAttribute("error", "Username is required");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Email is required");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Email format validation (basic)
        if (!email.contains("@") || !email.contains(".")) {
            request.setAttribute("error", "Please enter a valid email address");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Password length validation
        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Username length validation
        if (username.length() < 3) {
            request.setAttribute("error", "Username must be at least 3 characters");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if username already exists
            if (userDAO.existsByUsername(username)) {
                request.setAttribute("error", "Username already taken. Please choose another.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Check if email already exists
            if (userDAO.existsByEmail(email)) {
                request.setAttribute("error", "Email already registered. Please login or use another email.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Determine role based on "Register as Seller" checkbox
            int roleId;
            if (registerAsSeller != null && registerAsSeller.equals("on")) {
                roleId = Role.ROLE_SELLER;  // SELLER role (2)
            } else {
                roleId = Role.ROLE_BUYER;   // BUYER role (3)
            }

            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Create user object
            User user = new User(username, email, hashedPassword);
            user.setRoleId(roleId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setActive(true);
            user.setEmailVerified(false);

            // Save to database
            boolean saved = userDAO.save(user);

            if (saved) {
                // Set success message and forward to login page
                request.setAttribute("success", "Registration successful! Please login to continue.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}