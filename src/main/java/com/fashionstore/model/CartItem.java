package com.fashionstore.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int productId;
    private String productName;
    private double price;
    private double discountPercent;
    private int quantity;
    private String imageUrl;
    private String sizeLabel;
    private java.sql.Timestamp addedAt;

    public double getDiscountedPrice() {
        return price - (price * discountPercent / 100);
    }

    public double getSubtotal() {
        return getDiscountedPrice() * quantity;
    }

    public CartItem() {}

    // Getters & Setters
    public java.sql.Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(java.sql.Timestamp addedAt) { this.addedAt = addedAt; }

    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSizeLabel() { return sizeLabel; }
    public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }
}