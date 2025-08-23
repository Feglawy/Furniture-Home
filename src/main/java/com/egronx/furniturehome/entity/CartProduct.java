package com.egronx.furniturehome.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_product" ,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cart_id", "product_id"})
        })
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity" , nullable = false)
    private int quantity = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id" , nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

}
