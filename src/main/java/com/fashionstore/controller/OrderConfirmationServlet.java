package com.fashionstore.controller;

import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/orderConfirmation")
public class OrderConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);

        OrderDAOImpl dao = new OrderDAOImpl();
        Order order = dao.getOrderById(orderId);
        List<OrderItem> orderItems = dao.getOrderItems(orderId);

        if (order == null) {
            response.sendRedirect("home");
            return;
        }

        request.setAttribute("order", order);
        request.setAttribute("orderItems", orderItems);

        request.getRequestDispatcher("/WEB-INF/views/orderConfirmation.jsp")
               .forward(request, response);
    }
}
