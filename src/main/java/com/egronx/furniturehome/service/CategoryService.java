package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.repository.CategoryRepository;
import com.egronx.furniturehome.validations.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    CategoryValidation categoryValidation = new CategoryValidation(categoryRepository);


    // List all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // CRUD for admin

    // Create New category + check for duplication or null name
    public String createCategory(Category category) {

        try {
            if (categoryValidation.validateCategoryName(category)) {
                categoryRepository.save(category);
                return "Category created";
            }
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
        return "Unexpected error";
    }

    // Read category
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category with id " + id + " is not found"));
    }

    // Update category + check if it exists first
    public String updateCategory(Category category) {
        try {
            if (categoryValidation.validateCategoryExist(category)){
                categoryRepository.save(category);
                return "Category updated successfully";
            }
        } catch (IllegalArgumentException ex){
            return ex.getMessage();
        }
        return "Unexpected error";
    }

    // Delete category + check if it exists first
    public String deleteCategory(Long id) {
        try {
            if (categoryValidation.validateCategoryIDExists(id)){
                categoryRepository.deleteById(id);
                return "Category deleted successfully";
            }
        } catch (IllegalArgumentException ex){
            return ex.getMessage();
        }
        return "Unexpected error";
    }

}
