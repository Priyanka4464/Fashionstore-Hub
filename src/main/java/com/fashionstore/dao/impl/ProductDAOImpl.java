package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private Connection con;

    public ProductDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean addProduct(Product product) {
        boolean status = false;

        try {
            String sql = "INSERT INTO products (category_id, product_name, description, discount_percent, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getDiscountPercent());
            ps.setString(5, product.getImageUrl());
            ps.setBoolean(6, product.isActive());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM products WHERE is_active = true";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;

        try {
            String sql = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = mapProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean status = false;

        try {
            String sql = "UPDATE products SET category_id=?, product_name=?, description=?, discount_percent=?, image_url=?, is_active=? WHERE product_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getDiscountPercent());
            ps.setString(5, product.getImageUrl());
            ps.setBoolean(6, product.isActive());
            ps.setInt(7, product.getProductId());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean deleteProduct(int productId) {
        boolean status = false;

        try {
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM products WHERE category_id=? AND is_active=true";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM products WHERE product_name LIKE ? AND is_active=true";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        // NOTE: You don't have price column yet → placeholder logic
        return getAllProducts();
    }
    @Override
    public List<Product> filterProducts(int categoryId, double minPrice, double maxPrice, String keyword) {
        List<Product> list = new ArrayList<>();

        try {
            // Build query dynamically
            String sql = "SELECT * FROM products WHERE is_active = true";

            if (categoryId > 0)                    sql += " AND category_id = ?";
            if (minPrice > 0 || maxPrice > 0)      sql += " AND price BETWEEN ? AND ?";
            if (keyword != null && !keyword.isEmpty()) sql += " AND product_name LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);

            int index = 1;

            if (categoryId > 0)
                ps.setInt(index++, categoryId);

            if (minPrice > 0 || maxPrice > 0) {
                ps.setDouble(index++, minPrice);
                ps.setDouble(index++, maxPrice > 0 ? maxPrice : Double.MAX_VALUE);
            }

            if (keyword != null && !keyword.isEmpty())
                ps.setString(index++, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    @Override
    public List<Product> getBestsellerProducts() {
        List<Product> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM products WHERE is_bestseller = true AND is_active = true";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper method (IMPORTANT)
    private Product mapProduct(ResultSet rs) throws Exception {
        Product p = new Product();

        p.setProductId(rs.getInt("product_id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setProductName(rs.getString("product_name"));
        p.setDescription(rs.getString("description"));
        p.setDiscountPercent(rs.getDouble("discount_percent"));
        p.setImageUrl(rs.getString("image_url"));
        p.setActive(rs.getBoolean("is_active"));
        
        p.setPrice(rs.getDouble("price"));
        p.setBestseller(rs.getBoolean("is_bestseller"));

        return p;
    }
}
