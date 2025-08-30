package com.egronx.furniturehome.controller.customer;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryCustomerController {

    CategoryService categoryService;
    public CategoryCustomerController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // list all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
