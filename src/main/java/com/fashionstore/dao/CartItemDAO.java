package com.fashionstore.dao;

import com.fashionstore.model.CartItem;
import java.util.List;

public interface CartItemDAO {

    // Add item to cart
    boolean addCartItem(CartItem item);

    // Get all items in a cart
    List<CartItem> getCartItems(int cartId);

    // Update quantity
    boolean updateCartItem(int cartItemId, int quantity);

    // Remove single item
    boolean removeCartItem(int cartItemId);

    // Clear entire cart
    boolean clearCart(int cartId);

    // Check if product already exists in cart
    CartItem getCartItemByProduct(int cartId, int productId, String sizeLabel);
}
