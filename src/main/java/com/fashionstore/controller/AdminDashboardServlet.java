package com.fashionstore.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null ||
           session.getAttribute("admin") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/login");

            return;
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/dashboard.jsp")
                .forward(request, response);
    }
}
