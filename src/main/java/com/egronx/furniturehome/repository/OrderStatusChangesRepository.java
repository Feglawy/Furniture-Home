package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.OrderStatusChanges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusChangesRepository extends JpaRepository<OrderStatusChanges, Long> {
    public List<OrderStatusChanges> findAllByOrderId(Long orderId);
}
