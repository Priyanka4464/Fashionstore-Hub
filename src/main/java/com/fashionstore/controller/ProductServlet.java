package com.fashionstore.controller;

import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read filter params from URL
        String categoryStr = request.getParameter("categoryId");
        String minStr      = request.getParameter("minPrice");
        String maxStr      = request.getParameter("maxPrice");
        String keyword     = request.getParameter("keyword");

        int    categoryId = (categoryStr != null && !categoryStr.isEmpty()) ? Integer.parseInt(categoryStr) : 0;
        double minPrice   = (minStr != null && !minStr.isEmpty())           ? Double.parseDouble(minStr)    : 0;
        double maxPrice   = (maxStr != null && !maxStr.isEmpty())           ? Double.parseDouble(maxStr)    : 0;

        ProductDAOImpl dao = new ProductDAOImpl();
        List<Product> productList = dao.filterProducts(categoryId, minPrice, maxPrice, keyword);

        // Pass values back to JSP so filters stay selected
        request.setAttribute("products", productList);
        request.setAttribute("selectedCategory", categoryId);
        request.setAttribute("minPrice", minStr);
        request.setAttribute("maxPrice", maxStr);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp")
               .forward(request, response);
    }
}