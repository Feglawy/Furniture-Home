package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.*;
import com.egronx.furniturehome.repository.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@Service
public class CheckoutService {
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    private final CartRepository cartRepo;
    private final CartProductRepository cartProductRepo;
    private final OrderRepository orderRepo;
    private final OrderProductRepository orderProductRepo;

    public CheckoutService(UserRepository userRepo, ProductRepository productRepo, CartRepository cartRepo, CartProductRepository cartProduct, OrderRepository orderRepo, OrderProductRepository orderProductRepo) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.cartProductRepo = cartProduct;
        this.orderRepo = orderRepo;
        this.orderProductRepo = orderProductRepo;
    }

    @Transactional
    public Order Checkout(Long customerId) {
        // get the users cart
        Cart cart = cartRepo.findByUserId(customerId).orElseThrow(
                () -> new RuntimeException("Cart not found")
        );

        // get the products and their quantities inside the cart
        if (cart.getCartProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // create new order
        Order order = Order.builder()
                .customer(userRepo.findById(customerId).orElseThrow(
                        () -> new RuntimeException("Customer not found")
                ))
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepo.save(order);

        long totalPrice = 0L;

        // convert cart product into order product
        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();

            // check if the demanded quantities is available
            if (product.getStock() < cartProduct.getQuantity()) {
                throw new RuntimeException("Not enough stock for product " + product.getName());
            }
            // update the stock in product
            product.setStock(product.getStock() - cartProduct.getQuantity());
            productRepo.save(product);

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartProduct.getQuantity())
                    .unitPrice(product.getFinalPrice())
                    .build();

            orderProductRepo.save(orderProduct);
            totalPrice += (long) product.getFinalPrice();
        }
        order.setTotalPrice(totalPrice);
        orderRepo.save(order);

        // clear the products in cart
        cartProductRepo.deleteAll(cart.getCartProducts());
        return order;
    }
}
