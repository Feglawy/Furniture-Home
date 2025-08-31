package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.Request.StatusChangeRequest;
import com.egronx.furniturehome.dto.Response.OrderDTO;
import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.security.MyUserDetails;
import com.egronx.furniturehome.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/") // get all orders by the logged-in user
    public ResponseEntity<?> findAll(@AuthenticationPrincipal MyUserDetails userDetails) {
        try {
            List<OrderDTO>result = orderService.FindAllByCustomerId(userDetails.getId());
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}") // get a specific order by id
    public ResponseEntity<?> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        OrderDTO order = orderService.FindById(id);
        if (Objects.equals(order.getCustomerId(), userDetails.getId())) {
            return ResponseEntity.ok(order);
        }else {
            return ResponseEntity.badRequest().body("access denied or Order id not found");
        }
    }

    @PutMapping("/{id}/status") // update the status only admin can update the order
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody StatusChangeRequest status,
                                    @AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id) {
        orderService.ChangeOrderStatus(id, status.getStatus());
        return ResponseEntity.ok().build();
    }

}
