package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.repository.CategoryRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.validations.CategoryValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidation categoryValidation;
    @Autowired
    ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryValidation categoryValidation) {
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    // List all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // CRUD for admin

    // Create New category + check for duplication or null name
    public String createCategory(Category category) {
        categoryValidation.validateCategoryName(category);

        categoryRepository.save(category);
        return "Category created successfully";

    }

    // Read category
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " is not found"));
    }

    // Update category + check if it exists first
    public String updateCategory(Category category, Long id) {
        categoryValidation.validateCategoryExists(id);

        category.setId(id);
        categoryRepository.save(category);
        return "Category updated successfully";

    }

    // Delete category + check if it exists first
    @Transactional
    public String deleteCategory(Long id) {
        categoryValidation.validateCategoryExists(id);

        productRepository.deleteAllByCategoryId(id);

        categoryRepository.deleteById(id);

        return "Category deleted successfully";
    }

}
