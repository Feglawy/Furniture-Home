package com.egronx.furniturehome.controller.admin;

import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    ProductService productService;
    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        String result = productService.addProduct(product);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        String result = productService.updateProduct(product, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String result =  productService.deleteProduct(id);
        return ResponseEntity.ok(result);
    }
}
