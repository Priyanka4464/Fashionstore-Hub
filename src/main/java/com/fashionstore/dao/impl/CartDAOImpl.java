package com.fashionstore.dao.impl;

import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl {

    private Connection con;

    public CartDAOImpl() {
        con = DBConnection.getConnection();
    }

    // Get or create cart for user, returns cart_id
    private int getOrCreateCart(int userId) throws SQLException {
        String select = "SELECT cart_id FROM cart WHERE user_id = ?";
        PreparedStatement ps = con.prepareStatement(select);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("cart_id");
        }
        // Create new cart
        String insert = "INSERT INTO cart (user_id) VALUES (?)";
        PreparedStatement ins = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        ins.setInt(1, userId);
        ins.executeUpdate();
        ResultSet keys = ins.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    // Get all cart items for a user
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> list = new ArrayList<>();
        try {
            String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, " +
                         "ci.size_label, ci.quantity, " +
                         "p.product_name, p.price, p.discount_percent, p.image_url " +
                         "FROM cart c " +
                         "JOIN cart_items ci ON c.cart_id = ci.cart_id " +
                         "JOIN products p ON ci.product_id = p.product_id " +
                         "WHERE c.user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscountPercent(rs.getDouble("discount_percent"));
                item.setImageUrl(rs.getString("image_url"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add item to cart
    public void addToCart(int userId, int productId, int quantity, String sizeLabel) {
        try {
            int cartId = getOrCreateCart(userId);

            // Check if same product+size already in cart
            String check = "SELECT cart_item_id, quantity FROM cart_items " +
                           "WHERE cart_id = ? AND product_id = ? AND size_label = ?";
            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setString(3, sizeLabel);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Update quantity
                int newQty = rs.getInt("quantity") + quantity;
                int cartItemId = rs.getInt("cart_item_id");
                updateQuantity(cartItemId, newQty);
            } else {
                // Insert new item
                String sql = "INSERT INTO cart_items (cart_id, product_id, size_label, quantity) " +
                             "VALUES (?, ?, ?, ?)";
                PreparedStatement insert = con.prepareStatement(sql);
                insert.setInt(1, cartId);
                insert.setInt(2, productId);
                insert.setString(3, sizeLabel);
                insert.setInt(4, quantity);
                insert.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update quantity
    public void updateQuantity(int cartItemId, int quantity) {
        try {
            String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove single item
    public void removeItem(int cartItemId) {
        try {
            String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartItemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clear entire cart
    public void clearCart(int userId) {
        try {
            String sql = "DELETE ci FROM cart_items ci " +
                         "JOIN cart c ON ci.cart_id = c.cart_id " +
                         "WHERE c.user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}