package com.fashionstore.dao;

import com.fashionstore.model.CartItem;
import java.util.List;

public interface CartDAO {
    boolean addToCart(int userId, int productId, int quantity);
    List<CartItem> getCartItems(int userId);
    boolean updateQuantity(int cartId, int quantity);
    boolean removeItem(int cartId);
    boolean clearCart(int userId);
    int getCartCount(int userId);
}
