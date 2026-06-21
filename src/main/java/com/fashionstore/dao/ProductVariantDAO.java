package com.fashionstore.dao;

import com.fashionstore.model.ProductVariant;
import java.util.List;

public interface ProductVariantDAO {

    // Add variant (size + stock)
    boolean addVariant(ProductVariant variant);

    // Get all variants for a product
    List<ProductVariant> getVariantsByProductId(int productId);

    // Get variant by ID
    ProductVariant getVariantById(int variantId);

    // Update variant (stock, size, etc.)
    boolean updateVariant(ProductVariant variant);

    // Delete variant
    boolean deleteVariant(int variantId);

    // Get available variants (stock > 0)
    List<ProductVariant> getAvailableVariants(int productId);

    // Update stock after order
    boolean updateStock(int variantId, int quantity);
}
