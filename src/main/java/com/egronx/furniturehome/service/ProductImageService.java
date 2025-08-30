package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.ProductImage;
import com.egronx.furniturehome.repository.ProductImageRepository;
import com.egronx.furniturehome.validations.ProductImageValidation;
import com.egronx.furniturehome.validations.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageValidation productImageValidation;

    public ProductImageService(ProductImageRepository productImageRepository, ProductImageValidation productImageValidation) {
        this.productImageRepository = productImageRepository;
        this.productImageValidation = productImageValidation;
    }

    public List<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }

    public ProductImage viewImage(Long id) {
        return productImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " is not found"));
    }

    public String addProductImage(ProductImage productImage) {
        productImageValidation.validateImgURL(productImage.getImageUrl());
        productImageValidation.validateProduct(productImage.getProduct());

        productImageRepository.save(productImage);
        return "Product image added";
    }

    public String updateProductImage(ProductImage productImage, Long id) {
        productImageValidation.validateImgExists(id);
        productImageValidation.validateImgURL(productImage.getImageUrl());
        productImageValidation.validateProduct(productImage.getProduct());

        productImage.setId(id);
        productImageRepository.save(productImage);
        return "Product image updated";
    }
    public String deleteProductImage(Long id) {
        productImageValidation.validateImgExists(id);

        productImageRepository.deleteById(id);
        return "Product image deleted";
    }
}
