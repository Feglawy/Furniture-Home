package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.Response.OrderDTO;
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
    public OrderDTO Checkout(Long customerId) {
        // get the user's cart
        Cart cart = cartRepo.findByUserId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // create new order
        Order order = new Order();
        order.setCustomer(userRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found")));
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        // collections are already initialized in entity
        orderRepo.save(order);

        long totalPrice = 0L;

        // convert cart products into order products
        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();

            if (product.getStock() < cartProduct.getQuantity()) {
                throw new RuntimeException("Not enough stock for product " + product.getName());
            }

            // update stock
            product.setStock(product.getStock() - cartProduct.getQuantity());
            productRepo.save(product);

            // create order product
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setUnitPrice(product.getFinalPrice());

            // add to order's collection
            order.getOrderProducts().add(orderProduct);
            orderProductRepo.save(orderProduct);
            totalPrice += (long) (product.getFinalPrice() * cartProduct.getQuantity());
        }

        // update total price
        order.setTotalPrice(totalPrice);
        orderRepo.save(order); // cascades orderProducts

        // clear the cart
        cartProductRepo.emptyTheCart(customerId);

        return OrderDTO.parseOrder(order);
    }

}
