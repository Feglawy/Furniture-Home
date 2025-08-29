package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.entity.Order;
import com.egronx.furniturehome.repository.OrderRepository;
import com.egronx.furniturehome.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    OrderService orderService;

//    @GetMapping("/") // get all orders by the logged-in user
//    public List<Order> findAll() {return null;}
//
//    @GetMapping("/id") // get a specfic order by id
//    public Order findById(@RequestParam long id) {return null;}
//
//    @PutMapping("/id/status") // update the status only admin can update the order
//    public Order update(@RequestBody object status) {return null;}
//
//    @PutMapping("/id/cancel") // cancel order mark status as canceled not delete
//    public Order Cancel(@RequestParam long id) {return null;}
}
