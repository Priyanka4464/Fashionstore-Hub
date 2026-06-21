package com.fashionstore.dao;

import com.fashionstore.model.Category;
import java.util.List;

public interface CategoryDAO {

    // Add new category
    boolean addCategory(Category category);

    // Get all categories
    List<Category> getAllCategories();

    // Get category by ID
    Category getCategoryById(int categoryId);

    // Update category
    boolean updateCategory(Category category);

    // Delete category
    boolean deleteCategory(int categoryId);
}
