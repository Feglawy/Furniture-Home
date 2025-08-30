package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    void deleteAllByCategoryId(Long id);
}
