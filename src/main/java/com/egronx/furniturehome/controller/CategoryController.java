package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> listAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category showCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        String result = categoryService.createCategory(category);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        String result = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        String result = categoryService.deleteCategory(id);
        return ResponseEntity.ok(result);
    }

}
