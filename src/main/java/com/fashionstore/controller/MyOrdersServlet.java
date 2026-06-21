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

@WebServlet("/myOrders")
public class MyOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        OrderDAOImpl orderDao = new OrderDAOImpl();

        // Get all orders for this user
        List<Order> orders = orderDao.getOrdersByUserId(user.getUserId());

        // Load order items for each order
        for (Order order : orders) {
            List<OrderItem> items = orderDao.getOrderItems(order.getOrderId());
            order.setOrderItemsList(items);
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/myOrders.jsp")
               .forward(request, response);
    }
}

