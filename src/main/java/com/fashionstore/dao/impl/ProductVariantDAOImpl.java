package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private Connection con;

    public ProductVariantDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean addVariant(ProductVariant variant) {
        boolean status = false;

        try {
            String sql = "INSERT INTO product_sizes (product_id, size_label, stock_quantity, sku_code, is_available) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, variant.getProductId());
            ps.setString(2, variant.getSizeLabel());
            ps.setInt(3, variant.getStockQuantity());
            ps.setString(4, variant.getSkuCode());
            ps.setBoolean(5, variant.isAvailable());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {
        List<ProductVariant> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM product_sizes WHERE product_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapVariant(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ProductVariant getVariantById(int variantId) {
        ProductVariant variant = null;

        try {
            String sql = "SELECT * FROM product_sizes WHERE product_size_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, variantId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                variant = mapVariant(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variant;
    }

    @Override
    public boolean updateVariant(ProductVariant variant) {
        boolean status = false;

        try {
            String sql = "UPDATE product_sizes SET product_id=?, size_label=?, stock_quantity=?, sku_code=?, is_available=? WHERE product_size_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, variant.getProductId());
            ps.setString(2, variant.getSizeLabel());
            ps.setInt(3, variant.getStockQuantity());
            ps.setString(4, variant.getSkuCode());
            ps.setBoolean(5, variant.isAvailable());
            ps.setInt(6, variant.getProductSizeId());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean deleteVariant(int variantId) {
        boolean status = false;

        try {
            String sql = "DELETE FROM product_sizes WHERE product_size_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, variantId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<ProductVariant> getAvailableVariants(int productId) {
        List<ProductVariant> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM product_sizes WHERE product_id=? AND stock_quantity > 0 AND is_available=true";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapVariant(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateStock(int variantId, int quantity) {
        boolean status = false;

        try {
            String sql = "UPDATE product_sizes SET stock_quantity = stock_quantity - ? WHERE product_size_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, variantId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // Helper method
    private ProductVariant mapVariant(ResultSet rs) throws Exception {
        ProductVariant v = new ProductVariant();

        v.setProductSizeId(rs.getInt("product_size_id"));
        v.setProductId(rs.getInt("product_id"));
        v.setSizeLabel(rs.getString("size_label"));
        v.setStockQuantity(rs.getInt("stock_quantity"));
        v.setSkuCode(rs.getString("sku_code"));
        v.setAvailable(rs.getBoolean("is_available"));

        return v;
    }
}
