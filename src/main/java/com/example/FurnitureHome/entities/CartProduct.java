package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "cart_product", schema = "furniture", indexes = {
        @Index(name = "product_id", columnList = "product_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "cart_id", columnNames = {"cart_id", "product_id"})
})
public class CartProduct {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ColumnDefault("'1'")
    @Column(name = "quantity", columnDefinition = "int UNSIGNED not null")
    private Long quantity;

}