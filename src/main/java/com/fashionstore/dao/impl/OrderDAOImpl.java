package com.fashionstore.dao.impl;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private Connection con;

    public OrderDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public int createOrder(Order order, List<OrderItem> orderItems) {
        try {
            // Start transaction
            con.setAutoCommit(false);

            // Insert order
            String orderSql = "INSERT INTO orders (user_id, order_number, total_amount, discount, " +
                    "final_amount, payment_method, payment_status, order_status, name, phone, address_line, city, state, pincode) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement orderPs = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, order.getUserId());
            orderPs.setString(2, order.getOrderNumber());
            orderPs.setDouble(3, order.getTotalAmount());
            orderPs.setDouble(4, order.getDiscount());
            orderPs.setDouble(5, order.getFinalAmount());
            orderPs.setString(6, order.getPaymentMethod());
            orderPs.setString(7, order.getPaymentStatus());
            orderPs.setString(8, order.getOrderStatus());
            orderPs.setString(9, order.getName());
            orderPs.setString(10, order.getPhone());
            orderPs.setString(11, order.getAddressLine());
            orderPs.setString(12, order.getCity());
            orderPs.setString(13, order.getState());
            orderPs.setString(14, order.getPincode());

            orderPs.executeUpdate();

            // Get generated order ID
            ResultSet rs = orderPs.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Insert order items
            String itemSql = "INSERT INTO order_items (order_id, product_id, product_name, " +
                           "quantity, price, discount_percent, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement itemPs = con.prepareStatement(itemSql);
            for (OrderItem item : orderItems) {
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, item.getProductId());
                itemPs.setString(3, item.getProductName());
                itemPs.setInt(4, item.getQuantity());
                itemPs.setDouble(5, item.getPrice());
                itemPs.setDouble(6, item.getDiscountPercent());
                itemPs.setDouble(7, item.getSubtotal());
                itemPs.addBatch();
            }
            itemPs.executeBatch();

            // Commit transaction
            con.commit();
            con.setAutoCommit(true);

            return orderId;

        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order = null;
        try {
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = mapOrder(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(mapOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        try {
            String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM order_items WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscountPercent(rs.getDouble("discount_percent"));
                item.setSubtotal(rs.getDouble("subtotal"));
                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // Helper method to map ResultSet to Order
    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setDiscount(rs.getDouble("discount"));
        order.setFinalAmount(rs.getDouble("final_amount"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setPaymentStatus(rs.getString("payment_status"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setName(rs.getString("name"));
        order.setPhone(rs.getString("phone"));
        order.setAddressLine(rs.getString("address_line"));
        order.setCity(rs.getString("city"));
        order.setState(rs.getString("state"));
        order.setPincode(rs.getString("pincode"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        //order.setUpdatedAt(rs.getTimestamp("updated_at"));
        try { order.setUpdatedAt(rs.getTimestamp("updated_at")); } catch(Exception e) {}
        return order;
    }
}
