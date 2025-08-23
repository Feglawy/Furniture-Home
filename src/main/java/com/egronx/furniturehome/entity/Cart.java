package com.egronx.furniturehome.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" , nullable = false , unique = true)
    private User user;


    @OneToMany(mappedBy = "cart" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();
}
