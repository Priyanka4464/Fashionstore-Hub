package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.AdminDAO;
import com.fashionstore.dao.impl.AdminDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        AdminDAO dao = new AdminDAOImpl();

        if (dao.validateAdmin(username, password)) {

            HttpSession session =
                    request.getSession();

            session.setAttribute("admin", username);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dashboard");

        } else {

        	response.sendRedirect(
        	        request.getContextPath()
        	        + "/admin/login?error=1");
        }
    }
}
