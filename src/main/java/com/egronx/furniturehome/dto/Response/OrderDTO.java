package com.egronx.furniturehome.dto.Response;

import com.egronx.furniturehome.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private Long customerId; // instead of whole User entity
    private double totalPrice;
    private String status; // Enum as String
    private LocalDateTime createdAt;

    private List<OrderProductDTO> orderProducts;
    private List<OrderStatusChangeDTO> statusChanges;

    public static OrderDTO parseOrder(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .orderProducts(order.getOrderProducts().stream().map(op -> OrderProductDTO.builder()
                        .productId(op.getProduct().getId())
                        .quantity(op.getQuantity())
                        .unitPrice(op.getUnitPrice())
                        .build()).collect(Collectors.toList()))
                .statusChanges(order.getStatusChanges().stream().map(changes -> OrderStatusChangeDTO.builder()
                        .status(changes.getStatus().name())
                        .changedAt(changes.getCreatedAt())
                        .build()).collect(Collectors.toList())
                )
                .build();
    }
}
