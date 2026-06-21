package com.fashionstore.controller;

import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Create DAO object
        ProductDAOImpl productDAO = new ProductDAOImpl();

        // Step 2: Fetch all products from DB
        List<Product> productList = productDAO.getAllProducts();
     // Limit to first 6 products only for home page
     if (productList.size() > 5) {
         productList = productList.subList(0, 5);
     }

        // Step 3: Send data to JSP
        request.setAttribute("products", productList);

        // Step 4: Forward to home.jsp
     // ✅ Correct
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
        
     // Get cart count from session
        HttpSession session = request.getSession();
        Integer cartCount = (Integer) session.getAttribute("cartCount");
        if (cartCount == null) cartCount = 0;
        request.setAttribute("cartCount", cartCount);
    }
}
