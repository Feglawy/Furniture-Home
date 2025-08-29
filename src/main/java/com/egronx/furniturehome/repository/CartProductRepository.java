package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.CartProduct;
import org.springframework.data.repository.CrudRepository;

public interface CartProductRepository extends CrudRepository<CartProduct,Long> {
}
