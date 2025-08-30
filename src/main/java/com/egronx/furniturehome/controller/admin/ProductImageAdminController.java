package com.egronx.furniturehome.controller.admin;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.entity.ProductImage;
import com.egronx.furniturehome.service.ProductImageService;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/productimages")
public class ProductImageAdminController {

    ProductImageService productImageService;
    public ProductImageAdminController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public List<ProductImage> getProductImages() {
        return productImageService.getAllImages();
    }

    @PostMapping
    public ResponseEntity<String> addProductImage(@RequestBody ProductImage productImage) {
        String result = productImageService.addProductImage(productImage);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    public ProductImage showProductImage(@PathVariable Long id) {
        return productImageService.viewImage(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductImage(@PathVariable Long id, @RequestBody ProductImage productImage) {
        String result = productImageService.updateProductImage(productImage, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long id) {
        String result = productImageService.deleteProductImage(id);
        return ResponseEntity.ok(result);
    }
}
