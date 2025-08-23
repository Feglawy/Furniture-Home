package com.egronx.furniturehome.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" , nullable = false)
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "price" , nullable = false)
    double price;
    @Column(name = "discount" , nullable = false)
    double discount = 0;
    @Column(name = "final_price" , nullable = false , updatable = false , insertable = false)
    double finalPrice;
    @Column(name = "stock" , nullable = false)
    Long stock = 0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<CartProduct> cartProducts = new HashSet<>();

}
