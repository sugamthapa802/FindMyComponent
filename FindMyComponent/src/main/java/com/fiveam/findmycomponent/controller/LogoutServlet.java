package com.fiveam.findmycomponent.controller;
import com.fiveam.findmycomponent.dao.UserSessionDao;
import com.fiveam.findmycomponent.dao.UserSessionDaoImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * LogoutServlet - Handles user logout
 * URL: /logout
 * - Invalidates session
 * - Removes remember-me cookie and database token
 * - Redirects to login page
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private UserSessionDao sessionDAO;

    @Override
    public void init() throws ServletException {
        sessionDAO = new UserSessionDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Invalidate the HTTP session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Step 2: Remove remember-me cookie and database token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("remember_token".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    // Delete token from database
                    sessionDAO.deleteByToken(token);

                    // Delete the cookie by setting max age to 0
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);

                    break;
                }
            }
        }

        // Step 3: Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}