package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    void deleteAllByCategoryId(Long id);
}
