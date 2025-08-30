package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.Response.OrderDTO;
import com.egronx.furniturehome.dto.Response.OrderProductDTO;
import com.egronx.furniturehome.dto.Response.OrderStatusChangeDTO;
import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.security.MyUserDetails;
import com.egronx.furniturehome.service.CheckoutService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("cart/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<?> Checkout(@AuthenticationPrincipal MyUserDetails userDetails) {
       try {
           Order order = this.checkoutService.Checkout(userDetails.getId());
           return ResponseEntity.ok().body(OrderDTO.parseOrder(order));
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
