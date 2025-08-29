package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.OrderStatusChanges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusChangesRepository extends JpaRepository<OrderStatusChanges, Integer> {
}
