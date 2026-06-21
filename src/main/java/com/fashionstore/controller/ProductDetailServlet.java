package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/productDetails")
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Get product ID from URL
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("products");
                return;
            }

            int productId = Integer.parseInt(idParam);

            // 2. Fetch product from DB
            Product product = productDAO.getProductById(productId);

            if (product == null) {
                request.setAttribute("error", "Product not found");
                request.getRequestDispatcher("WEB-INF/views/error.jsp")
                       .forward(request, response);
                return;
            }

            // 3. Send data to JSP
            request.setAttribute("product", product);

            // 4. Forward to product-details page
            request.getRequestDispatcher("WEB-INF/views/product_details.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", "Something went wrong");
            request.getRequestDispatcher("WEB-INF/views/error.jsp")
                   .forward(request, response);
        }
    }
}
