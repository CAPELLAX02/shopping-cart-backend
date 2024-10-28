package com.capellax.shoppingCart.service.category;

import com.capellax.shoppingCart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String name);

    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);

    List<Category> getAllCategories();

    void deleteCategoryByID(Long categoryId);

}
