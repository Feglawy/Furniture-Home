package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.entity.OrderStatus;
import com.egronx.furniturehome.repository.OrderProductRepository;
import com.egronx.furniturehome.repository.OrderRepository;
import com.egronx.furniturehome.repository.OrderStatusChangesRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

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

    public List<Order> FindAllByCustomerId(Long customerId) {
        return this.orderRepo.findAllByCustomerId(customerId);
    }

    public Order FindById(Long id) {
        return orderRepo.findById(id);
    }

    public void ChangeOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepo.findById(orderId);
        if  (order != null) {
            order.setStatus(orderStatus);
            orderRepo.save(order);
        }
    }

    public void CancelOrder(Long OrderId) {
        this.ChangeOrderStatus(OrderId, OrderStatus.CANCELLED);
    }

    public void CreateOrder(Order order) {
        orderRepo.save(order);
    }

    public void DeleteOrder(Long OrderId) {
        orderRepo.delete(orderRepo.findById(OrderId));
    }
}
