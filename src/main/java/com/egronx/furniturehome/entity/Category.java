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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    String name;

    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    Set<Product> products = new HashSet<>();

}
