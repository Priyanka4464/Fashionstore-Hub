package com.fashionstore.dao.impl;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    private Connection con;

    public CartItemDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean addCartItem(CartItem item) {
        boolean status = false;

        try {
            String sql = "INSERT INTO cart_items (cart_id, product_id, size_label, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getProductId());
            ps.setString(3, item.getSizeLabel());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getUnitPrice());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {
        List<CartItem> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM cart_items WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setAddedAt(rs.getTimestamp("added_at"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateCartItem(int cartItemId, int quantity) {
        boolean status = false;

        try {
            String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean removeCartItem(int cartItemId) {
        boolean status = false;

        try {
            String sql = "DELETE FROM cart_items WHERE cart_item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartItemId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean clearCart(int cartId) {
        boolean status = false;

        try {
            String sql = "DELETE FROM cart_items WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public CartItem getCartItemByProduct(int cartId, int productId, String sizeLabel) {
        CartItem item = null;

        try {
            String sql = "SELECT * FROM cart_items WHERE cart_id=? AND product_id=? AND size_label=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setString(3, sizeLabel);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setAddedAt(rs.getTimestamp("added_at"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }
}
