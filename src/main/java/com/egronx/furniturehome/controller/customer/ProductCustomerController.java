package com.egronx.furniturehome.controller.customer;

import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductCustomerController {
    ProductService productService;
    public ProductCustomerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }
}
