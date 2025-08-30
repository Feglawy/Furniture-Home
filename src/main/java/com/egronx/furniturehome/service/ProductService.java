package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.validations.ProductValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidation productValidation;

    public ProductService(ProductRepository productRepository, ProductValidation productValidation) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
    }



    // Browse products for customer
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // CRUD

    // Create new product + validation " blank name + price + null category "
    public String addProduct(Product product) {
        productValidation.validateAddProduct(product);

        productRepository.save(product);
        return "Product added successfully";
    }

    // Read specific product
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " is not found"));
    }

    // Update product
    public String updateProduct(Product product, Long id) {
        productValidation.validateUpdateProduct(product, id);

        product.setId(id);
        productRepository.save(product);
        return "Product updated successfully";

    }

    // delete product
    public String deleteProduct(Long id) {
        productValidation.validateDeleteProduct(id);

        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}

