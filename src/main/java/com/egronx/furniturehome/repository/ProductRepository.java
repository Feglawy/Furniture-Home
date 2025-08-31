package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);


    @Query("SELECT p from Product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") @NotNull Long id);
    void deleteAllByCategoryId(Long id);
}
