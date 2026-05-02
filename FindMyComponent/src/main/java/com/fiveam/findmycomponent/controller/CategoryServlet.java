package com.fiveam.findmycomponent.controller;

import com.fiveam.findmycomponent.dao.CategoryDao;
import com.fiveam.findmycomponent.dao.CategoryDaoImpl;
import com.fiveam.findmycomponent.entity.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CategoryServlet - Handles all category management operations
 * URL: /admin/categories
 * Only ADMIN should have access to this servlet
 *
 * GET requests:
 * - No action: List all categories
 * - action=add: Show add category form
 * - action=edit&id=X: Show edit category form
 *
 * POST requests:
 * - action=save: Save new or update existing category
 * - action=delete&id=X: Soft delete category
 * - action=activate&id=X: Reactivate category
 */
@WebServlet("/admin/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryDao categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Show add category form
            request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);

        } else if ("edit".equals(action)) {
            // Show edit category form with existing data
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    Category category = categoryDAO.findById(id);
                    if (category != null) {
                        request.setAttribute("category", category);
                        request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    // Invalid ID, redirect to list
                }
            }
            // Category not found or invalid ID, redirect to list
            response.sendRedirect(request.getContextPath() + "/admin/categories");

        } else {
            // Default: List all categories
            List<Category> categories = categoryDAO.findAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("save".equals(action)) {
            // Save or update category
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            // Validation
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Category name is required");
                if (idParam != null && !idParam.isEmpty()) {
                    request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
                }
                return;
            }

            name = name.trim();

            if (idParam != null && !idParam.isEmpty()) {
                // Update existing category
                try {
                    int id = Integer.parseInt(idParam);
                    Category existingCategory = categoryDAO.findById(id);

                    if (existingCategory != null) {
                        // Check if name already exists (excluding this category)
                        Category nameCheck = categoryDAO.findByName(name);
                        if (nameCheck != null && nameCheck.getId() != id) {
                            request.setAttribute("error", "Category name already exists");
                            request.setAttribute("category", existingCategory);
                            request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
                            return;
                        }

                        existingCategory.setName(name);
                        existingCategory.setDescription(description);
                        categoryDAO.update(existingCategory);
                    }
                } catch (NumberFormatException e) {
                    // Invalid ID
                }
            } else {
                // Add new category
                // Check if name already exists
                if (categoryDAO.existsByName(name)) {
                    request.setAttribute("error", "Category name already exists");
                    request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
                    return;
                }

                Category newCategory = new Category(name, description);
                categoryDAO.save(newCategory);
            }

        } else if ("delete".equals(action)) {
            // Soft delete category
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    categoryDAO.delete(id);
                } catch (NumberFormatException e) {
                    // Invalid ID
                }
            }

        } else if ("activate".equals(action)) {
            // Reactivate category
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    categoryDAO.activate(id);
                } catch (NumberFormatException e) {
                    // Invalid ID
                }
            }
        }

        // Redirect back to category list
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}