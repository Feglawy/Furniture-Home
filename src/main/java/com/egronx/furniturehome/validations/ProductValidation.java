package com.egronx.furniturehome.validations;

import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.repository.CategoryRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class ProductValidation {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductValidation(ProductRepository repository , CategoryRepository categoryRepository) {
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    public void validateAddProduct(Product product) {
        validateName(product.getName());
        validatePrice(product.getPrice());
        validateDiscount(product.getDiscount());
        validateStock(product.getStock());
        validateCategory(product.getCategory());
        validateDescription(product.getDescription());
    }

    public void validateUpdateProduct(Product product, Long id) {
        validateProductExists(id);
        validateName(product.getName());
        validatePrice(product.getPrice());
        validateDiscount(product.getDiscount());
        validateStock(product.getStock());
        validateCategory(product.getCategory());
        validateDescription(product.getDescription());
    }

    public void validateDeleteProduct(Long id) {
        validateProductExists(id);
    }

    private Boolean validateProductExists(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("The product with id " + id + " does not exist");
        }
        return true;
    }

    private Boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (productRepository.existsByName(name)){
            throw new IllegalArgumentException("Product name already exists");
        }
        return true;
    }

    private Boolean validatePrice(double price) {
        if (price <= 0 ) {
            throw new IllegalArgumentException("Product price cannot be less than 1$");
        }
        return true;
    }

    private Boolean validateDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        if (discount > 100) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }
        return true;
    }

    private Boolean validateStock(Long stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock cannot be null or negative");
        }
        return true;
    }

    private Boolean validateDescription(String description) {
        if (description != null && description.length() > 1000) {
            throw new IllegalArgumentException("Description is too long (max 1000 characters)");
        }
        return true;
    }

    private Boolean validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
        if(! categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category is not exists");
        }

        return true;
    }

}
