package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.Response.OrderDTO;
import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.entity.OrderStatus;
import com.egronx.furniturehome.entity.OrderStatusChanges;
import com.egronx.furniturehome.repository.OrderProductRepository;
import com.egronx.furniturehome.repository.OrderRepository;
import com.egronx.furniturehome.repository.OrderStatusChangesRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderProductRepository orderProductRepo;
    private final OrderStatusChangesRepository statusRepo;

    public OrderService(OrderRepository orderRepo, OrderProductRepository orderProductRepo, OrderStatusChangesRepository statusRepo) {
        this.orderRepo = orderRepo;
        this.orderProductRepo = orderProductRepo;
        this.statusRepo = statusRepo;
    }

    @Transactional
    public List<OrderDTO> FindAllByCustomerId(Long customerId) {
        return orderRepo.findAllByCustomerId(customerId).stream().map(OrderDTO::parseOrder).toList();
    }

    @Transactional
    public OrderDTO FindById(Long id) {
        Order order = orderRepo.findById(id);
        return OrderDTO.parseOrder(order);
    }

    @Transactional
    public void ChangeOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepo.findById(orderId);
        if  (order != null) {
            order.setStatus(orderStatus);
            orderRepo.save(order);
            OrderStatusChanges orderStatusChanges = OrderStatusChanges.builder()
                    .order(order)
                    .status(orderStatus)
                    .createdAt(LocalDateTime.now())
                    .build();
            statusRepo.save(orderStatusChanges);
        }
    }
}
