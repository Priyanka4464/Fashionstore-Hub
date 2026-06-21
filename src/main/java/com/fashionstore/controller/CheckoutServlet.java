package com.fashionstore.controller;

import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    // Show checkout page
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
        CartDAOImpl cartDao = new CartDAOImpl();

        List<CartItem> cartItems = cartDao.getCartItems(user.getUserId());

        // Check empty cart
        if (cartItems.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // Calculate totals
        double totalPrice = 0;
        double totalDiscount = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalDiscount += (item.getPrice() * item.getDiscountPercent() / 100) * item.getQuantity();
        }

        double finalPrice = totalPrice - totalDiscount;

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalDiscount", totalDiscount);
        request.setAttribute("finalPrice", finalPrice);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp")
               .forward(request, response);
    }

    // Place order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        // Get form data
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String addressLine = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");
        String paymentMethod = request.getParameter("paymentMethod");

        // Validation
        if (name == null || phone == null || addressLine == null ||
            city == null || state == null || pincode == null ||
            name.isEmpty() || phone.isEmpty() || addressLine.isEmpty() ||
            city.isEmpty() || state.isEmpty() || pincode.isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            doGet(request, response);
            return;
        }

        CartDAOImpl cartDao = new CartDAOImpl();
        List<CartItem> cartItems = cartDao.getCartItems(user.getUserId());

        if (cartItems.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // Calculate totals
        double totalPrice = 0;
        double totalDiscount = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalDiscount += (item.getPrice() * item.getDiscountPercent() / 100) * item.getQuantity();
        }

        double finalPrice = totalPrice - totalDiscount;

        // Create order
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setOrderNumber("ORD" + System.currentTimeMillis());
        order.setTotalAmount(totalPrice);
        order.setDiscount(totalDiscount);
        order.setFinalAmount(finalPrice);
        order.setPaymentMethod(paymentMethod != null ? paymentMethod : "COD");
        order.setOrderStatus("PLACED");
        order.setPaymentStatus("PENDING");
        order.setName(name);
        order.setPhone(phone);
        order.setAddressLine(addressLine);
        order.setCity(city);
        order.setState(state);
        order.setPincode(pincode);

        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem();
            item.setProductId(cartItem.getProductId());
            item.setProductName(cartItem.getProductName());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getPrice());
            item.setDiscountPercent(cartItem.getDiscountPercent());
            item.setSubtotal(cartItem.getSubtotal());
            orderItems.add(item);
        }

        // Save order to DB
        OrderDAOImpl orderDao = new OrderDAOImpl();
        int orderId = orderDao.createOrder(order, orderItems);

        if (orderId > 0) {
            // Clear cart
            cartDao.clearCart(user.getUserId());

            // Redirect to order confirmation
            response.sendRedirect("orderConfirmation?orderId=" + orderId);
        } else {
            request.setAttribute("error", "Failed to place order. Please try again.");
            doGet(request, response);
        }
    }
}