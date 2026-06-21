package com.fashionstore.controller;

import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Show register page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp")
               .forward(request, response);
    }

    // Handle form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name    = request.getParameter("name");
        String email   = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        String phone   = request.getParameter("phone");
        String gender  = request.getParameter("gender");
        String address = request.getParameter("address");

        // Validation
        if (name == null || email == null || password == null ||
            name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp")
                   .forward(request, response);
            return;
        }

        if (!password.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp")
                   .forward(request, response);
            return;
        }

        UserDAOImpl dao = new UserDAOImpl();

        if (dao.isEmailExists(email)) {
            request.setAttribute("error", "Email already registered. Please login.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp")
                   .forward(request, response);
            return;
        }

        // Save user with gender and address
        User user = new User(name, email, password, phone, gender, address);
        boolean success = dao.registerUser(user);

        if (success) {
            response.sendRedirect("login?registered=true");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp")
                   .forward(request, response);
        }
    }
}