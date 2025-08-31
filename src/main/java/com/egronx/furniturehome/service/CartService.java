package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.CartProductDTO;
import com.egronx.furniturehome.entity.Cart;
import com.egronx.furniturehome.entity.CartProduct;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.CartProductRepository;
import com.egronx.furniturehome.repository.CartRepository;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    private final CartProductRepository cartProductRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    CartRepository cartRepository;

    @Autowired
    public CartService(UserRepository userRepository, ProductRepository productRepository , CartRepository cartRepository, CartProductRepository cartProductRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public List<CartProductDTO> getCartItems() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            cart = cartRepository.save(
                    Cart.builder()
                        .user(user)
                        .cartProducts(new HashSet<>())
                        .build());
        }
        List<CartProductDTO> cartProducts;
        cartProducts = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            CartProductDTO dto = new CartProductDTO();
            dto.setId(cartProduct.getId());
            dto.setProductId(cartProduct.getProduct().getId());
            dto.setQuantity(cartProduct.getQuantity());
            cartProducts.add(dto);
        }
        return cartProducts;
    }

    @Transactional
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

    @Transactional
    public void updateCartItem(CartProductDTO cartProductDTO) {
        // TODO
    }

    @Transactional
    public void removeItem(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        Cart cart = user.getCart();
        cart.getCartProducts().removeIf(cartProduct -> Objects.equals(cartProduct.getProduct().getId(), id));
        cartRepository.save(cart);
    }
}
