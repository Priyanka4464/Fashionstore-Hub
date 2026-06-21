package com.fashionstore.dao;

import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import java.util.List;

public interface OrderDAO {
    int createOrder(Order order, List<OrderItem> orderItems);
    Order getOrderById(int orderId);
    List<Order> getOrdersByUserId(int userId);
    boolean updateOrderStatus(int orderId, String status);
    List<OrderItem> getOrderItems(int orderId);
}
