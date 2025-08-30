package com.egronx.furniturehome.controller.admin;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    CategoryService categoryService;
    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> listAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        String result = categoryService.createCategory(category);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public Category showCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        String result = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        String result = categoryService.deleteCategory(id);
        return ResponseEntity.ok(result);
    }

}
