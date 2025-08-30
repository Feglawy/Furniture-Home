package com.egronx.furniturehome.validations;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidation {
    CategoryRepository categoryRepository;

    public CategoryValidation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Boolean validateCategoryName(Category category) {
        if(category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name can not be empty");
        }
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        return true;
    }

    public Boolean validateCategoryExists(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with id " + id + " is not found");
        }
        return true;
    }
}
