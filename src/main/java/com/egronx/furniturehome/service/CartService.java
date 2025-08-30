package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.CartProductDTO;
import com.egronx.furniturehome.entity.Cart;
import com.egronx.furniturehome.entity.CartProduct;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.CartRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    UserRepository userRepository;
    ProductRepository productRepository;
    CartRepository cartRepository;

    @Autowired
    public CartService(UserRepository userRepository, ProductRepository productRepository , CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<CartProduct> getCartItems() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        Cart cart = user.getCart();
        List<CartProduct> cartProducts;
        cartProducts = new ArrayList<>(cart.getCartProducts());
        return cartProducts;
    }

    public void addCartItem(CartProductDTO cartProductDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        Cart cart = user.getCart();
        Product product = productRepository.findById(cartProductDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartProductDTO.getQuantity());
        cartProduct.setCart(cart);
        cart.addCartProduct(cartProduct);
        cartRepository.save(cart);
    }

    public void removeItem(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        Cart cart = user.getCart();
        cart.getCartProducts().removeIf(cartProduct -> Objects.equals(cartProduct.getProduct().getId(), id));
        cartRepository.save(cart);
    }
}
