package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.ProductDTO;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO product) {
        String result = productService.addProduct(product);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO product) {
        String result = productService.updateProduct(product, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String result =  productService.deleteProduct(id);
        return ResponseEntity.ok(result);
    }
}
