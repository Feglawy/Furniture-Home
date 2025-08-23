package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "review", schema = "furniture", indexes = {
        @Index(name = "product_id", columnList = "product_id"),
        @Index(name = "customer_id", columnList = "customer_id")
})
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "comment")
    private String comment;

}