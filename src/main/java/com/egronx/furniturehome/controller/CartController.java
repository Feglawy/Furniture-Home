package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.CartProductDTO;
import com.egronx.furniturehome.entity.CartProduct;
import com.egronx.furniturehome.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @GetMapping
    public List<CartProduct> getCartItems() {
        return cartService.getCartItems();
    }


    @PostMapping
    public void addCartItem(@RequestBody CartProductDTO cartProduct) {
        cartService.addCartItem(cartProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        cartService.removeItem(id);
    }
}
