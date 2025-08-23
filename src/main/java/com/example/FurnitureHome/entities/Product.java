package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "furniture", indexes = {
        @Index(name = "category_id", columnList = "category_id")
})
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discount", nullable = false, precision = 5, scale = 2)
    private BigDecimal discount;

    @ColumnDefault("(`price` - ((`price` * `discount`) / 100))")
    @Column(name = "final_price", precision = 10, scale = 2)
    private BigDecimal finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ColumnDefault("'0'")
    @Column(name = "stock", columnDefinition = "int UNSIGNED not null")
    private Long stock;

}