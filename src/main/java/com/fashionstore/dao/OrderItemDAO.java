package com.fashionstore.dao;

import com.fashionstore.model.OrderItem;
import java.util.List;

public interface OrderItemDAO {

    // Add item to order
    boolean addOrderItem(OrderItem item);

    // Get all items for an order
    List<OrderItem> getOrderItems(int orderId);

    // Delete items of an order (optional)
    boolean deleteOrderItems(int orderId);
}
