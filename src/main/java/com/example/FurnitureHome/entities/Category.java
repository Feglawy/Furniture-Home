package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "furniture", uniqueConstraints = {
        @UniqueConstraint(name = "name", columnNames = {"name"})
})
public class Category {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}