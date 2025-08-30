package com.egronx.furniturehome.validations;

import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.repository.ProductImageRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductImageValidation {

    ProductImageRepository productImageRepository;
    ProductRepository productRepository;


    public ProductImageValidation(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    public Boolean validateImgExists(Long id) {
        if (!productImageRepository.existsById(id)) {
            throw new IllegalArgumentException("The image with id " + id + " does not exist");
        }
        return true;
    }

    public Boolean validateImgURL(String imgURL) {
        if (imgURL == null || imgURL.isEmpty()) {
            throw new IllegalArgumentException("Invalid image URL");
        }
        return true;
    }


    public Boolean validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (!productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product is not exist");
        }
        return true;
    }

}
