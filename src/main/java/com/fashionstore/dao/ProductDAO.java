package com.fashionstore.dao;

import com.fashionstore.model.Product;
import java.util.List;

public interface ProductDAO {

    // Add new product
    boolean addProduct(Product product);

    // Get all products
    List<Product> getAllProducts();

    // Get product by ID
    Product getProductById(int productId);

    // Update product
    boolean updateProduct(Product product);

    // Delete product
    boolean deleteProduct(int productId);

    // Get products by category
    List<Product> getProductsByCategory(int categoryId);

    // Search products by name
    List<Product> searchProducts(String keyword);

    // Filter products by price range
    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    // Combined filters (category + price + search)
    List<Product> filterProducts(int categoryId, double minPrice, double maxPrice, String keyword);
    
    List<Product> getBestsellerProducts();
}