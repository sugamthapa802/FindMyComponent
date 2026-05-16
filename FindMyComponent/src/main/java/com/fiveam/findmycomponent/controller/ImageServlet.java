package com.fiveam.findmycomponent.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Serves uploaded product images from the external uploads folder.
 *
 * Maps to /uploads/products/* so that product images are accessible via URL.
 * Images are stored in ~/findmycomponent-uploads/products/
 *
 * URL pattern: /uploads/products/{filename}.png
 */
@WebServlet("/uploads/products/*")
public class ImageServlet extends HttpServlet {

    private static final String UPLOAD_BASE_DIR = System.getProperty("user.home")
            + File.separator + "findmycomponent-uploads"
            + File.separator + "products";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String filename = request.getPathInfo();
        if (filename == null || filename.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Remove leading slash
        filename = filename.substring(1);

        // Security: Prevent directory traversal attacks
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        File file = new File(UPLOAD_BASE_DIR, filename);

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Security: ensure the file is inside the uploads folder
        String canonicalUploadPath = new File(UPLOAD_BASE_DIR).getCanonicalPath();
        String canonicalFilePath = file.getCanonicalPath();

        if (!canonicalFilePath.startsWith(canonicalUploadPath)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String contentType = Files.probeContentType(file.toPath());
        if (contentType != null) {
            response.setContentType(contentType);
        } else {
            response.setContentType("image/png");
        }

        response.setContentLengthLong(file.length());

        // Cache for 1 week
        response.setHeader("Cache-Control", "max-age=604800");

        try (OutputStream out = response.getOutputStream()) {
            Files.copy(file.toPath(), out);
        }
    }
}