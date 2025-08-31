package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(Long id);

    @Query("""
    SELECT distinct o FROM Order o
    LEFT join fetch o.orderProducts
    LEFT JOIN fetch o.statusChanges
    WHERE o.customer.id = :customerId
    """)
    List<Order> findAllByCustomerId(@Param("customerId") Long customerId);
}
