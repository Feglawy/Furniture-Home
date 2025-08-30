package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Optional<Product> findById(Long id);
    void deleteAllByCategoryId(Long id);
}
