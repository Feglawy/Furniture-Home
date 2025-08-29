package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(Long id);
    List<Order> findAllByCustomerId(Long customerId);
}
