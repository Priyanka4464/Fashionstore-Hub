package com.fashionstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get current session and invalidate it
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // ← clears all session data
        }

        // Redirect to login page
        response.sendRedirect("login");
    }
}
