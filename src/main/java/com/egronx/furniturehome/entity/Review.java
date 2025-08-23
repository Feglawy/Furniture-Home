package com.egronx.furniturehome.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review")
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    String comment;

    @Column(name = "rating" , columnDefinition = "INT CHECK (rating BETWEEN 1 AND 5)")
    int rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id" , nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id" , nullable = false)
    Product product;

}
