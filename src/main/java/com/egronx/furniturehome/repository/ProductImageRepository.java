package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByImageUrlAndProduct_Id(String imageUrl, Long productId);
    void deleteAllByProductId(Long id);
}
