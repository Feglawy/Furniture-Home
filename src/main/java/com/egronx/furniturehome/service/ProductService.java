package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.ProductDTO;
import com.egronx.furniturehome.entity.Category;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.entity.ProductImage;
import com.egronx.furniturehome.repository.CategoryRepository;
import com.egronx.furniturehome.repository.ProductImageRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.validations.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductValidation productValidation;

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductValidation productValidation, ProductImageRepository productImageRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .finalPrice(product.getFinalPrice())
                .stock(product.getStock())
                .category(product.getCategory().getName())
                .images(product.getImages().stream().map(ProductImage::getImageUrl).collect(Collectors.toList()))
                .build();
    }

    private Product mapToEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .discount(productDTO.getDiscount())
                .finalPrice(productDTO.getFinalPrice())
                .stock(productDTO.getStock())
                .category(
                        categoryRepository.findByName(productDTO.getCategory()).orElse(
                                Category.builder().name(productDTO.getCategory()).build()
                        )
                ).images(
                        productDTO.getImages() != null
                                ? productDTO.getImages().stream()
                                .map(url -> ProductImage.builder().imageUrl(url).build())
                                .collect(Collectors.toSet())
                                : new HashSet<>()
                )
                .build();
    }

    // Browse products for customer
    public List<ProductDTO> getProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {productDTOS.add(mapToDTO(product));});
        return productDTOS;
    }

    // CRUD
    // Create new product + validation " blank name + price + null category "
    public String addProduct(ProductDTO productDto) {
        Product product = mapToEntity(productDto);
        productValidation.validateAddProduct(product);

        productRepository.save(product);
        return "Product added successfully";
    }

    // Read specific product
    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " is not found"));

        return mapToDTO(product);
    }

    // Update product
    public String updateProduct(ProductDTO productDto, Long id) {
        Product product = mapToEntity(productDto);
        productValidation.validateUpdateProduct(product, id);

        product.setId(id);
        productRepository.save(product);
        return "Product updated successfully";

    }

    // delete product
    public String deleteProduct(Long id) {
        productValidation.validateDeleteProduct(id);

        productImageRepository.deleteAllByProductId(id);

        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}

