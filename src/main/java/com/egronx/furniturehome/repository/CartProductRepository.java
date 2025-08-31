package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.CartProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    @Modifying
    @Transactional
    @Query("""
    delete from CartProduct cp
        where cp.cart.user.id = :customerId""")
    void emptyTheCart(@Param("customerId") Long customerId);
}
