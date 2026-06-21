package com.fashionstore.util;

import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;

import java.util.List;

public class TestDAO {

    public static void main(String[] args) {

        System.out.println("===== DAO TEST START =====");

        // -----------------------------
        // 1. TEST USER LOGIN
        // -----------------------------
        UserDAOImpl userDAO = new UserDAOImpl();

        User user = userDAO.loginUser("priyankabk@gmail.com", "1234");

        if (user != null) {
            System.out.println(" Login Success: " + user.getFullName());
        } else {
            System.out.println(" Login Failed");
        }

        // -----------------------------
        // 2. TEST GET USER BY ID
        // -----------------------------
        User u = userDAO.getUserById(1);
        if (u != null) {
            System.out.println("User Found: " + u.getEmail());
        } else {
            System.out.println("User Not Found");
        }

        // -----------------------------
        // 3. TEST FETCH PRODUCTS
        // -----------------------------
        ProductDAOImpl productDAO = new ProductDAOImpl();

        List<Product> products = productDAO.getAllProducts();

        System.out.println("\n===== PRODUCT LIST =====");

        for (Product p : products) {
            System.out.println("ID: " + p.getProductId());
            System.out.println("Name: " + p.getProductName());
            System.out.println("Discount: " + p.getDiscountPercent());
            System.out.println("-----------------------");
        }

        // -----------------------------
        // 4. TEST FETCH PRODUCTS BY CATEGORY
        // -----------------------------
        List<Product> menProducts = productDAO.getProductsByCategory(1);

        System.out.println("\n===== MEN PRODUCTS =====");

        for (Product p : menProducts) {
            System.out.println(p.getProductName());
        }

        System.out.println("\n===== DAO TEST END =====");
    }
}
