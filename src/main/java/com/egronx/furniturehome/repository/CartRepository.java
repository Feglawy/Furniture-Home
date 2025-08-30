package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Cart;
import com.egronx.furniturehome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long customerId);
}
