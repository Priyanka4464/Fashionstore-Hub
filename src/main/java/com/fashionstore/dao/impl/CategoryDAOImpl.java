package com.fashionstore.dao.impl;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private Connection con;

    public CategoryDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean addCategory(Category category) {
        boolean status = false;

        try {
            String sql = "INSERT INTO categories (category_name, description, is_active) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isActive());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM categories";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setDescription(rs.getString("description"));
                c.setActive(rs.getBoolean("is_active"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        Category category = null;

        try {
            String sql = "SELECT * FROM categories WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setActive(rs.getBoolean("is_active"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public boolean updateCategory(Category category) {
        boolean status = false;

        try {
            String sql = "UPDATE categories SET category_name=?, description=?, is_active=? WHERE category_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isActive());
            ps.setInt(4, category.getCategoryId());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        boolean status = false;

        try {
            String sql = "DELETE FROM categories WHERE category_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
