package com.egronx.furniturehome.controller.customer;

import com.egronx.furniturehome.entity.ProductImage;
import com.egronx.furniturehome.service.ProductImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productimages")
public class ProductImageCustomerController {

    ProductImageService productImageService;
    public ProductImageCustomerController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/{id}")
    public ProductImage showProductImage(@PathVariable Long id) {
        return productImageService.viewImage(id);
    }
}
