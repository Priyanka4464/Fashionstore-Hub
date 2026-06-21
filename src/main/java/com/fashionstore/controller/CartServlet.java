package com.fashionstore.controller;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    // Show cart page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        CartDAOImpl dao = new CartDAOImpl();
        List<CartItem> cartItems = dao.getCartItems(user.getUserId());

        // ✅ Count total quantity
        int totalQty = 0;
        for (CartItem item : cartItems) {  // ← fixed: was "items", now "cartItems"
            totalQty += item.getQuantity();
        }
        session.setAttribute("cartCount", totalQty);

        // Calculate totals
        double totalPrice    = 0;
        double totalDiscount = 0;
        for (CartItem item : cartItems) {
            totalPrice    += item.getPrice() * item.getQuantity();
            totalDiscount += (item.getPrice() * item.getDiscountPercent() / 100) * item.getQuantity();
        }
        double finalPrice = totalPrice - totalDiscount;

        request.setAttribute("cartItems",     cartItems);
        request.setAttribute("totalPrice",    totalPrice);
        request.setAttribute("totalDiscount", totalDiscount);
        request.setAttribute("finalPrice",    finalPrice);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
               .forward(request, response);
    }

    // Handle add/update/remove actions
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        CartDAOImpl dao = new CartDAOImpl();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int productId    = Integer.parseInt(request.getParameter("productId"));
            int quantity     = Integer.parseInt(request.getParameter("quantity"));
            String sizeLabel = request.getParameter("sizeLabel");
            dao.addToCart(user.getUserId(), productId, quantity, sizeLabel);

            // ✅ Count total quantity
            List<CartItem> items = dao.getCartItems(user.getUserId());
            int totalQty = 0;
            for (CartItem item : items) { totalQty += item.getQuantity(); }
            session.setAttribute("cartCount", totalQty);

            response.sendRedirect("cart");

        } else if ("update".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity   = Integer.parseInt(request.getParameter("quantity"));
            if (quantity > 0) {
                dao.updateQuantity(cartItemId, quantity);
            }

            // ✅ Count total quantity
            List<CartItem> items = dao.getCartItems(user.getUserId());
            int totalQty = 0;
            for (CartItem item : items) { totalQty += item.getQuantity(); }
            session.setAttribute("cartCount", totalQty);

            response.sendRedirect("cart");

        } else if ("remove".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            dao.removeItem(cartItemId);

            // ✅ Count total quantity
            List<CartItem> items = dao.getCartItems(user.getUserId());
            int totalQty = 0;
            for (CartItem item : items) { totalQty += item.getQuantity(); }
            session.setAttribute("cartCount", totalQty);

            response.sendRedirect("cart");

        } else if ("clear".equals(action)) {
            dao.clearCart(user.getUserId());

            // ✅ Reset to 0
            session.setAttribute("cartCount", 0);

            response.sendRedirect("cart");

        } else {
            response.sendRedirect("cart");
        }
    }
}