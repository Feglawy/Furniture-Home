package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    // List all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // CRUD for admin

    // Create New category + check for duplication or null name
    public Category createCategory(Category category) {

        if(category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name can not be empty");
        }
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    // Read category
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category with id " + id + " is not found"));
    }

    // Update category + check if it exists first
    public Category updateCategory(Category category) {
        if(!categoryRepository.existsById(category.getId())) {
            throw new RuntimeException("Category with id " + category.getId() + " is not found");
        }
        return categoryRepository.save(category);
    }

    // Delete category + check if it exists first
    public String deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category with id " + id + " is not found");
        }
        categoryRepository.deleteById(id);
        return "Category with id " + id + " was deleted";
    }

}
