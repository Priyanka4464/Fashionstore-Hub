package com.fashionstore.controller;

import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bestSellers")
public class BestSellerServlet extends HttpServlet {   // ← fixed name

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAOImpl dao = new ProductDAOImpl();
        List<Product> bestSellers = dao.getBestsellerProducts();

        if (bestSellers == null || bestSellers.isEmpty()) {
            bestSellers = dao.getAllProducts();
        }

        request.setAttribute("products", bestSellers);
        request.setAttribute("pageTitle", "Best Sellers");
        request.getRequestDispatcher("/WEB-INF/views/products.jsp")
               .forward(request, response);
    }
}
